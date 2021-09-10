package com.comdata.backend.comdatapointage.service.implementations;

import com.comdata.backend.comdatapointage.dao.ActiviterRepository;
import com.comdata.backend.comdatapointage.dao.ParametrageRepository;
import com.comdata.backend.comdatapointage.dto.ActiviterDto;
import com.comdata.backend.comdatapointage.dto.PageDto;
import com.comdata.backend.comdatapointage.entity.Activiter;
import com.comdata.backend.comdatapointage.entity.Collaborateur;
import com.comdata.backend.comdatapointage.entity.Parametrage;
import com.comdata.backend.comdatapointage.entity.Superviseur;
import com.comdata.backend.comdatapointage.request.ActiviterRequest;
import com.comdata.backend.comdatapointage.service.DtoParser;
import com.comdata.backend.comdatapointage.service.interfaces.IActiviterService;
import com.comdata.backend.comdatapointage.service.interfaces.IGetterIdService;
import com.comdata.backend.comdatapointage.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class ActiviterServiceDb implements IActiviterService {

    @Autowired private DtoParser dtoParser;
    @Autowired private IGetterIdService getterIdService;
    @Autowired private IUserService userService;
    @Autowired private ActiviterRepository activiterRepository;
    @Autowired private ParametrageRepository parametrageRepository;



    @Override
    public ActiviterDto consulterActiviter(Integer id) throws Exception {
        Activiter activiter = getterIdService.getActiviter(id);
        return dtoParser.toActiviterDto(activiter);
    }

    @Override
    public PageDto<ActiviterDto> consulterActivites(String mc, int page, int size) {
        Page<Activiter> pageActiviter = activiterRepository.findByNomContains(mc, PageRequest.of(page, size));
        PageDto<ActiviterDto> pageDto = new PageDto<>();
        pageDto.setPage(page);
        pageDto.setSize(size);
        pageDto.setTotalPage(pageActiviter.getTotalPages());
        for(Activiter activiter : pageActiviter.getContent()) {
            pageDto.getContent().add(dtoParser.toActiviterDto(activiter));
        }
        return pageDto;
    }

    @Override
    public ActiviterDto addActiviter(ActiviterRequest request) {
        Parametrage parametrage = new Parametrage();
        parametrage = parametrageRepository.save(parametrage);
        parametrage.setTFermetureSessionMs(request.getTfermetureSessionMs());
        parametrage.setTInactiviteMs(request.getTinactiviteMs());
        parametrage = parametrageRepository.save(parametrage);

        Activiter activiter = new Activiter();
        activiter.setNom(request.getName());
        activiter.setActive(true);
        activiter.setDate_creation(new Date());
        activiter = activiterRepository.save(activiter);
        activiter.setParametrage(parametrage);

        activiter = activiterRepository.save(activiter);
        return dtoParser.toActiviterDto(activiter);
    }

    @Override
    public ActiviterDto editActiviter(Integer id, ActiviterRequest request) throws Exception {
        Activiter activiter = getterIdService.getActiviter(id);
        Parametrage parametrage = activiter.getParametrage();
        parametrage.setTFermetureSessionMs(request.getTfermetureSessionMs());
        parametrage.setTInactiviteMs(request.getTinactiviteMs());
        parametrage = parametrageRepository.save(parametrage);

        activiter.setNom(request.getName());
        activiter.setActive(true);
        activiter.setDate_creation(new Date());
        activiter = activiterRepository.save(activiter);
        activiter.setParametrage(parametrage);

        activiter = activiterRepository.save(activiter);

        return dtoParser.toActiviterDto(activiter);
    }

    @Override
    @Transactional
    public void enableActiviter(Integer id) throws Exception {
        Activiter activiter = getterIdService.getActiviter(id);
        activiter.setActive(true);

        for (Collaborateur collaborateur : activiter.getCollaborateurs()) {
            userService.enableUser(collaborateur.getMatricule());
        }
        for (Superviseur e : activiter.getSuperviseurs()) {
            userService.enableUser(e.getMatricule());
        }
        activiterRepository.save(activiter);
    }

    @Override
    @Transactional
    public void disableActiviter(Integer id) throws Exception {
        Activiter activiter = getterIdService.getActiviter(id);
        activiter.setActive(false);
        for (Collaborateur e : activiter.getCollaborateurs()) {
            userService.disableUser(e.getMatricule());
        }
        for (Superviseur e : activiter.getSuperviseurs()) {
            userService.disableUser(e.getMatricule());
        }
        activiterRepository.save(activiter);
    }
}
