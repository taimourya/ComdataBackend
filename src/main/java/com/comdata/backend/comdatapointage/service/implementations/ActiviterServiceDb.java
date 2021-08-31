package com.comdata.backend.comdatapointage.service.implementations;

import com.comdata.backend.comdatapointage.dao.ActiviterRepository;
import com.comdata.backend.comdatapointage.dao.ParametrageRepository;
import com.comdata.backend.comdatapointage.dto.ActiviterDto;
import com.comdata.backend.comdatapointage.dto.PageDto;
import com.comdata.backend.comdatapointage.entity.Activiter;
import com.comdata.backend.comdatapointage.entity.Parametrage;
import com.comdata.backend.comdatapointage.request.ActiviterRequest;
import com.comdata.backend.comdatapointage.service.DtoParser;
import com.comdata.backend.comdatapointage.service.interfaces.IActiviterService;
import com.comdata.backend.comdatapointage.service.interfaces.IGetterIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ActiviterServiceDb implements IActiviterService {

    @Autowired private IGetterIdService getterIdService;
    @Autowired private DtoParser dtoParser;
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
        parametrage.setTFermetureSessionMs(request.getTFermetureSessionMs());
        parametrage.setTInactiviteMs(request.getTInactiviteMs());
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
        parametrage.setTFermetureSessionMs(request.getTFermetureSessionMs());
        parametrage.setTInactiviteMs(request.getTInactiviteMs());
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
    public void enableActiviter(Integer id) throws Exception {
        Activiter activiter = getterIdService.getActiviter(id);
        activiter.setActive(true);
        activiterRepository.save(activiter);
    }

    @Override
    public void disableActiviter(Integer id) throws Exception {
        Activiter activiter = getterIdService.getActiviter(id);
        activiter.setActive(false);
        activiterRepository.save(activiter);
    }
}
