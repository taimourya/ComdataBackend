package com.comdata.backend.comdatapointage.threads;

import com.comdata.backend.comdatapointage.entity.*;
import com.comdata.backend.comdatapointage.service.interfaces.IGetterIdService;
import com.comdata.backend.comdatapointage.service.interfaces.ISessionService;
import javassist.NotFoundException;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;


public class SessionCollaborateurThread extends Thread{

    enum STATE {
        ACTIF, INACTIF, PAUSE, CLOSED;
    }

    private IGetterIdService getterIdService;
    private ISessionService sessionService;

    static public List<SessionCollaborateurThread> sessions = new ArrayList<>();

    private WebSocketSession session;
    private Collaborateur collaborateur;
    private Parametrage parametrage;
    private int counterActif = 0;
    private int counterInactif = 0;
    private int counterPause = 0;
    private STATE currentState;
    private Temps currentTemps;

    public boolean isActif() {
        return currentState == STATE.ACTIF;
    }

    public boolean isInactif() {
        return currentState == STATE.INACTIF;
    }

    public boolean isPause() {
        return currentState == STATE.PAUSE;
    }

    public Collaborateur getCollaborateur() {
        return collaborateur;
    }

    public SessionCollaborateurThread(String matricule, WebSocketSession session,
                                      IGetterIdService getterIdService, ISessionService sessionService) {
        try {
            System.out.println("start session of " + matricule);
            this.collaborateur = (Collaborateur) getterIdService.getUser(matricule);
            this.parametrage = collaborateur.getActiviter().getParametrage();
            if(containsCollaborateur(this.collaborateur))
                this.currentState = STATE.CLOSED;
            else {
                this.currentState = STATE.ACTIF;
                this.currentTemps = sessionService.startSession(collaborateur.getMatricule());
            }
            this.session = session;
            this.getterIdService = getterIdService;
            this.sessionService = sessionService;
            SessionCollaborateurThread.sessions.add(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void startActiviter() throws Exception {
        if(currentState == STATE.PAUSE)
            return;
        if(currentState == STATE.INACTIF)
            endInactiviter();
        currentState = STATE.ACTIF;
        currentTemps = sessionService.startActiviter(collaborateur.getMatricule());
    }
    private void endActiviter() throws Exception {
        if(currentState == STATE.ACTIF && currentTemps instanceof Actif) {
            sessionService.endActiviter(currentTemps.getId());
            currentTemps = null;
        }
    }

    public void startPause(int type_id) throws Exception {
        if(currentState == STATE.ACTIF)
            endActiviter();
        if(currentState == STATE.INACTIF)
            endInactiviter();
        currentState = STATE.PAUSE;
        currentTemps = sessionService.startPause(collaborateur.getMatricule(), type_id);
    }
    public void endPause() throws Exception {
        if(currentState == STATE.PAUSE && currentTemps instanceof Pause) {
            sessionService.endPause(currentTemps.getId());
            counterPause = 0;
            currentState = STATE.ACTIF;
            startActiviter();
        }
    }

    public void startInactiviter() throws Exception {
        if(currentState == STATE.ACTIF)
            endActiviter();
        if (currentState == STATE.PAUSE)
            return;
        currentTemps = sessionService.startInactiviter(collaborateur.getMatricule());
        currentState = STATE.INACTIF;
    }

    public void endInactiviter() throws Exception {
        if(currentState == STATE.INACTIF && currentTemps instanceof Inactif) {
            sessionService.endInactiviter(currentTemps.getId());
            counterInactif = 0;
            currentState = STATE.ACTIF;
            startActiviter();
        }
    }

    public void endSession() throws Exception {
        if(currentState == STATE.PAUSE)
            sessionService.endPause(currentTemps.getId());
        else if(currentState == STATE.INACTIF)
            sessionService.endInactiviter(currentTemps.getId());
        else if(currentState == STATE.ACTIF)
            sessionService.endActiviter(currentTemps.getId());
        this.currentState = STATE.CLOSED;
    }

    public static SessionCollaborateurThread findSession(WebSocketSession session) throws NotFoundException {
        for (SessionCollaborateurThread sessionThread : SessionCollaborateurThread.sessions) {
            if(session.getId().equals(sessionThread.session.getId()))
                return sessionThread;
        }
        throw new NotFoundException("session not found");
    }

    public static boolean containsCollaborateur(Collaborateur c) throws NotFoundException {
        for (SessionCollaborateurThread sessionThread : SessionCollaborateurThread.sessions) {
            if(c.getMatricule().equals(sessionThread.collaborateur.getMatricule()))
                return true;
        }
        return false;
    }

    public void startInactiviterAfterCpt(int cpt) throws Exception {

        if(cpt == 0) {
            if(currentState == STATE.INACTIF) {
                this.endInactiviter();
            }
        }
        else if(cpt * 1000 >= parametrage.getTInactiviteMs()) {
            if(currentState != STATE.INACTIF)
                this.startInactiviter();
        }
    }

    @Override
    public void run() {

        boolean fin = false;
        while (!fin) {
            /*System.out.println("----------------------------");
            System.out.println("current : " + currentState);
            if(currentTemps != null) {
                System.out.println("pauseIna : " + currentTemps.getId() + " and type is : " + currentTemps.getClass().getSimpleName());
            }
            System.out.println("----------------------------");*/
            try {
                Thread.sleep(1000);

                switch (currentState) {
                    case ACTIF:
                        counterActif += 1000;
                        //rep = count : actif,pause,inactif
                        session.sendMessage(new TextMessage("count : "+counterActif/1000+","+counterPause/1000+","+counterInactif/1000));
                        break;
                    case PAUSE:
                        counterPause += 1000;
                        session.sendMessage(new TextMessage("count : "+counterActif/1000+","+counterPause/1000+","+counterInactif/1000));
                        break;
                    case INACTIF:
                        counterInactif += 1000;
                        session.sendMessage(new TextMessage("count : "+counterActif/1000+","+counterPause/1000+","+counterInactif/1000));
                        if(counterInactif < parametrage.getTFermetureSessionMs()) {
                            break;
                        }
                        session.sendMessage(new TextMessage("vous avez ete inactif votre session va se fermer"));
                        sessionService.endInactiviter(currentTemps.getId());
                        currentState = STATE.CLOSED;
                        //no break for continue
                    case CLOSED:
                        if(session.isOpen()) {
                            session.sendMessage(new TextMessage("fermeture session"));
                            session.close();
                        }
                        SessionCollaborateurThread.sessions.remove(this);
                        fin = true;
                        break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


}
