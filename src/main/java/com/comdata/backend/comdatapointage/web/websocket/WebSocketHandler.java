package com.comdata.backend.comdatapointage.web.websocket;

import com.comdata.backend.comdatapointage.service.JwtTokenService;
import com.comdata.backend.comdatapointage.service.interfaces.IGetterIdService;
import com.comdata.backend.comdatapointage.service.interfaces.ISessionService;
import com.comdata.backend.comdatapointage.threads.SessionCollaborateurThread;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;

@Component
public class WebSocketHandler extends AbstractWebSocketHandler {

    @Autowired private JwtTokenService jwtTokenService;
    @Autowired private IGetterIdService getterIdService;
    @Autowired private ISessionService sessionService;


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String[] splited = message.getPayload().split(":");
        if(splited.length < 2) {
            session.sendMessage(new TextMessage("operation impossible"));
            return;
        }
        String operation = splited[0].trim();
        System.out.println(operation);
        if(operation.equals("start")) {
            startSession(session, splited[1].trim());
        }
        else if(operation.equals("state")) {
            String route = splited[1].trim();
            if(route.startsWith("start/pause")) {
                String[] params = route.split("\\?");
                if(params.length >= 2) {
                    if(params[1].startsWith("id")) {
                        Integer id = Integer.parseInt(params[1].split("=")[1].trim());
                        SessionCollaborateurThread s = SessionCollaborateurThread.findSession(session);
                        s.startPause(id);
                    }
                }
            }
            else if(route.startsWith("end/pause")) {
                SessionCollaborateurThread s = SessionCollaborateurThread.findSession(session);
                s.endPause();
            }
            else if(route.startsWith("start/inactif")) {
                SessionCollaborateurThread s = SessionCollaborateurThread.findSession(session);
                s.startInactiviter();
            }
            else if(route.startsWith("end/inactif")) {
                SessionCollaborateurThread s = SessionCollaborateurThread.findSession(session);
                s.endInactiviter();
            }
            else if(route.startsWith("end/session")) {
                SessionCollaborateurThread s = SessionCollaborateurThread.findSession(session);
                s.endSession();
            }
        }
    }

    private void startSession(WebSocketSession session, String token) throws IOException {
        System.out.println(token);
        if(!jwtTokenService.isTokenExpired(token) && jwtTokenService.checkTheRole(token, "COLLABORATEUR")) {
            //start session with matricule (the matricule jwt)
            //send message "success"
            SessionCollaborateurThread sessionThread =
                    new SessionCollaborateurThread(
                            jwtTokenService.getMatriculeFromToken(token),
                            session,
                            getterIdService,
                            sessionService
                    );
            sessionThread.start();
            session.sendMessage(new TextMessage("success jwt"));
        }
        else {
            session.sendMessage(new TextMessage("failed jwt"));
        }
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage("demarage session"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        SessionCollaborateurThread s = SessionCollaborateurThread.findSession(session);
        s.endSession();
    }
}
