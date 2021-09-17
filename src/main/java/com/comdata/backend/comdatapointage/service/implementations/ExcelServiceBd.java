package com.comdata.backend.comdatapointage.service.implementations;

import com.comdata.backend.comdatapointage.dao.*;
import com.comdata.backend.comdatapointage.dto.ActiviterDto;
import com.comdata.backend.comdatapointage.entity.*;
import com.comdata.backend.comdatapointage.request.ActiviterRequest;
import com.comdata.backend.comdatapointage.request.UserRequest;
import com.comdata.backend.comdatapointage.service.FileStorageService;
import com.comdata.backend.comdatapointage.service.interfaces.IActiviterService;
import com.comdata.backend.comdatapointage.service.interfaces.IExcelService;
import com.comdata.backend.comdatapointage.service.interfaces.IGetterIdService;
import com.comdata.backend.comdatapointage.service.interfaces.IUserService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

@Service
public class ExcelServiceBd implements IExcelService {

    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired private IGetterIdService getterIdService;
    @Autowired private ActiviterRepository activiterRepository;
    @Autowired private ParametrageRepository parametrageRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private TypePauseRepository typePauseRepository;
    @Autowired private TempsRepository tempsRepository;
    @Autowired private FileStorageService fileStorageService;

    @Override
    public String exportActivites() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("activites");
        List<Activiter> activites = activiterRepository.findAll();

        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("nom");
        row.createCell(2).setCellValue("date creation");
        row.createCell(3).setCellValue("active");
        row.createCell(4).setCellValue("tinactivité");
        row.createCell(5).setCellValue("tfermeture");
        int rowCount = 0;
        for(Activiter a : activites) {
            row = sheet.createRow(++rowCount);
            row.createCell(0).setCellValue(a.getId());
            row.createCell(1).setCellValue(a.getNom());
            row.createCell(2).setCellValue(a.getDate_creation().toString());
            row.createCell(3).setCellValue(a.isActive());
            row.createCell(4).setCellValue(a.getParametrage().getTInactiviteMs());
            row.createCell(5).setCellValue(a.getParametrage().getTFermetureSessionMs());
        }
        try {
            String fileName = fileStorageService.createEmptyFile("activites.xlsx");
            FileOutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "activites.xlsx";
    }

    @Override
    public String exportUsers() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("users");
        List<User> users = userRepository.findAll();

        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("matricule");
        row.createCell(1).setCellValue("firstname");
        row.createCell(2).setCellValue("lastname");
        row.createCell(3).setCellValue("email");
        row.createCell(4).setCellValue("cin");
        row.createCell(5).setCellValue("phone");
        row.createCell(6).setCellValue("date naissance");
        row.createCell(7).setCellValue("date creation");
        row.createCell(8).setCellValue("activer");
        row.createCell(9).setCellValue("role");
        row.createCell(10).setCellValue("activite");
        int rowCount = 0;
        for(User a : users) {
            row = sheet.createRow(++rowCount);

            row.createCell(0).setCellValue(a.getMatricule());
            row.createCell(1).setCellValue(a.getFirstname());
            row.createCell(2).setCellValue(a.getLastname());
            row.createCell(3).setCellValue(a.getEmail());
            row.createCell(4).setCellValue(a.getCin());
            row.createCell(5).setCellValue(a.getPhone());
            row.createCell(6).setCellValue(a.getDate_naissance().toString());
            row.createCell(7).setCellValue(a.getDate_creation().toString());
            row.createCell(8).setCellValue(a.isActive());
            if(a instanceof Superviseur) {
                row.createCell(9).setCellValue("superviseur");
                row.createCell(10).setCellValue(((Superviseur) a).getActiviter().getNom());
            }
            else if(a instanceof Collaborateur) {
                row.createCell(9).setCellValue("collaborateur");
                row.createCell(10).setCellValue(((Collaborateur) a).getActiviter().getNom());
            }
            else {
                row.createCell(9).setCellValue("admin");
            }
        }
        FileOutputStream outputStream = null;
        try {
            String fileName = fileStorageService.createEmptyFile("users.xlsx");
            outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "users.xlsx";
    }

    @Override
    public String exportTypes() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("types");
        List<TypePause> types = typePauseRepository.findAll();

        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("libelle");
        int rowCount = 0;
        for(TypePause a : types) {
            row = sheet.createRow(++rowCount);
            row.createCell(0).setCellValue(a.getId());
            row.createCell(1).setCellValue(a.getLibelle());
        }
        FileOutputStream outputStream = null;
        try {
            String fileName = fileStorageService.createEmptyFile("types.xlsx");
            outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "types.xlsx";
    }

    @Override
    public String exportTemps() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("temps");
        List<Temps> temps = tempsRepository.findAll();

        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("collaborateur matricule");
        row.createCell(2).setCellValue("collaborateur fullname");
        row.createCell(3).setCellValue("date");
        row.createCell(5).setCellValue("heure debut");
        row.createCell(5).setCellValue("heure fin");
        row.createCell(6).setCellValue("type");
        row.createCell(7).setCellValue("type pause");
        int rowCount = 0;
        for(Temps a : temps) {
            row = sheet.createRow(++rowCount);
            row.createCell(0).setCellValue(a.getId());
            if(a instanceof Pause) {
                row.createCell(1).setCellValue(((Pause) a).getCollaborateur().getMatricule());
                row.createCell(2).setCellValue(
                        ((Pause) a).getCollaborateur().getFirstname() + " " + ((Pause) a).getCollaborateur().getLastname()
                );
            }
            else if(a instanceof Actif) {
                row.createCell(1).setCellValue(((Actif) a).getCollaborateur().getMatricule());
                row.createCell(2).setCellValue(
                        ((Actif) a).getCollaborateur().getFirstname() + " " + ((Actif) a).getCollaborateur().getLastname()
                );
            }
            else if(a instanceof Inactif) {
                row.createCell(1).setCellValue(((Inactif) a).getCollaborateur().getMatricule());
                row.createCell(2).setCellValue(
                        ((Inactif) a).getCollaborateur().getFirstname() + " " + ((Inactif) a).getCollaborateur().getLastname()
                );
            }

            row.createCell(3).setCellValue(a.getDate().toString());
            row.createCell(4).setCellValue(a.getHeur_debut().toString());
            row.createCell(5).setCellValue(a.getHeur_fin().toString());
            if(a instanceof Pause) {

                row.createCell(6).setCellValue("pause");
                row.createCell(7).setCellValue(((Pause) a).getTypePause().getLibelle());
            }
            else if(a instanceof Actif) {

                row.createCell(6).setCellValue("actif");
            }
            else if(a instanceof Inactif) {

                row.createCell(6).setCellValue("inactif");
            }


        }
        FileOutputStream outputStream = null;
        try {
            String fileName = fileStorageService.createEmptyFile("temps.xlsx");
            outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "temps.xlsx";
    }

    @Override
    public void importActivites(MultipartFile file) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for(int i=1; i<=worksheet.getPhysicalNumberOfRows() ;i++) {
            XSSFRow row = worksheet.getRow(i);
            if(row != null) {
                Parametrage parametrage = new Parametrage(
                        null, (int)row.getCell(5).getNumericCellValue(), (int)row.getCell(4).getNumericCellValue(), null
                );
                parametrage = parametrageRepository.save(parametrage);

                Activiter activiter = new Activiter(
                        (int)row.getCell(0).getNumericCellValue(),
                        row.getCell(1).getStringCellValue(),
                        null,
                        row.getCell(3).getBooleanCellValue(),
                        parametrage, null, null
                );
                try {
                    activiter.setDate_creation(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(row.getCell(2).getStringCellValue()));
                } catch (ParseException e) {
                    activiter.setDate_creation(new Date());
                }
                activiterRepository.save(activiter);
            }
        }
    }

    @Override
    public void importUsers(MultipartFile file) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for(int i=1; i<=worksheet.getPhysicalNumberOfRows() ;i++) {
            XSSFRow row = worksheet.getRow(i);
            if(row != null) {
                User user = getterIdService.getUser(row.getCell(0).getStringCellValue());
                if(user == null) {
                    if(row.getCell(9).getStringCellValue().equals("admin")) {
                        user = new Admin();
                    }
                    else if(row.getCell(9).getStringCellValue().equals("superviseur")) {
                        user = new Superviseur();
                    }
                    else if(row.getCell(9).getStringCellValue().equals("collaborateur")) {
                        user = new Collaborateur();
                    }
                    user.setMatricule(row.getCell(0).getStringCellValue());
                    user.setPasswd(bCryptPasswordEncoder.encode("123"));
                    user.setImage("default_image.png");
                }

                user.setFirstname(row.getCell(1).getStringCellValue());
                user.setLastname(row.getCell(2).getStringCellValue());
                user.setEmail(row.getCell(3).getStringCellValue());
                user.setCin(row.getCell(4).getStringCellValue());
                user.setPhone(row.getCell(5).getStringCellValue());
                user.setDate_naissance(new SimpleDateFormat("yyyy-MM-dd").parse(row.getCell(6).getStringCellValue()));
                try {
                    user.setDate_creation(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(row.getCell(7).getStringCellValue()));
                }catch (ParseException e) {
                    user.setDate_creation(new Date());
                }
                user.setActive(row.getCell(8).getBooleanCellValue());
                if(user instanceof Collaborateur || user instanceof Superviseur) {
                    Activiter a = getterIdService.getActiviter(row.getCell(10).getStringCellValue());
                    if(a == null) {
                        Parametrage parametrage = new Parametrage(
                                null, 10000, 10000, null
                        );
                        parametrage = parametrageRepository.save(parametrage);

                        a = new Activiter(
                                null,
                                row.getCell(10).getStringCellValue(),
                                new Date(),
                                true,
                                parametrage, null, null
                        );

                        a = activiterRepository.save(a);
                    }
                    if(user instanceof  Collaborateur)
                        ((Collaborateur) user).setActiviter(a);
                    else
                        ((Superviseur) user).setActiviter(a);
                }

                userRepository.save(user);

            }
        }
    }

    @Override
    public void importTypes(MultipartFile file) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for(int i=1; i<=worksheet.getPhysicalNumberOfRows() ;i++) {
            XSSFRow row = worksheet.getRow(i);
            if(row != null) {
                TypePause typePause = new TypePause(
                        (int)row.getCell(0).getNumericCellValue(),
                        row.getCell(1).getStringCellValue(), null
                );
                typePauseRepository.save(typePause);
            }
        }
    }

    @Override
    public void importTemps(MultipartFile file) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for(int i=1; i<=worksheet.getPhysicalNumberOfRows() ;i++) {
            XSSFRow row = worksheet.getRow(i);
            if(row != null) {
                Collaborateur collaborateur = (Collaborateur) getterIdService.getUser(row.getCell(1).getStringCellValue());
                if(row.getCell(6).getStringCellValue().equals("actif")) {
                    Actif actif = new Actif(
                            (int)row.getCell(0).getNumericCellValue(),
                            new SimpleDateFormat("yyyy-MM-dd").parse(row.getCell(3).getStringCellValue()),
                            LocalTime.parse(row.getCell(4).getStringCellValue()),
                            LocalTime.parse(row.getCell(5).getStringCellValue()),
                            collaborateur
                    );
                    tempsRepository.save(actif);
                }
                else if(row.getCell(6).getStringCellValue().equals("inactif")) {
                    Inactif inactif = new Inactif(
                            (int)row.getCell(0).getNumericCellValue(),
                            new SimpleDateFormat("yyyy-MM-dd").parse(row.getCell(3).getStringCellValue()),
                            LocalTime.parse(row.getCell(4).getStringCellValue()),
                            LocalTime.parse(row.getCell(5).getStringCellValue()),
                            collaborateur
                    );
                    tempsRepository.save(inactif);
                }
                else if(row.getCell(6).getStringCellValue().equals("pause")) {
                    TypePause typePause = typePauseRepository.findByLibelle(row.getCell(7).getStringCellValue());
                    if(typePause == null) {
                        typePause = new TypePause(null, row.getCell(7).getStringCellValue(), null);
                        typePause = typePauseRepository.save(typePause);
                    }
                    Pause pause = new Pause(
                            (int)row.getCell(0).getNumericCellValue(),
                            new SimpleDateFormat("yyyy-MM-dd").parse(row.getCell(3).getStringCellValue()),
                            LocalTime.parse(row.getCell(4).getStringCellValue()),
                            LocalTime.parse(row.getCell(5).getStringCellValue()),
                            typePause,
                            collaborateur
                    );
                    tempsRepository.save(pause);
                }
            }
        }
    }



}
