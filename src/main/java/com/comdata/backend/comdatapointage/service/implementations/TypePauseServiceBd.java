package com.comdata.backend.comdatapointage.service.implementations;

import com.comdata.backend.comdatapointage.dao.TypePauseRepository;
import com.comdata.backend.comdatapointage.dto.PageDto;
import com.comdata.backend.comdatapointage.dto.TypeDto;
import com.comdata.backend.comdatapointage.entity.Activiter;
import com.comdata.backend.comdatapointage.entity.TypePause;
import com.comdata.backend.comdatapointage.request.TypeRequest;
import com.comdata.backend.comdatapointage.service.DtoParser;
import com.comdata.backend.comdatapointage.service.interfaces.IGetterIdService;
import com.comdata.backend.comdatapointage.service.interfaces.ITypePauseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TypePauseServiceBd implements ITypePauseService {

    @Autowired private IGetterIdService getterIdService;
    @Autowired private TypePauseRepository typePauseRepository;
    @Autowired private DtoParser dtoParser;


    @Override
    public TypeDto consulterType(Integer id) throws Exception {
        return dtoParser.toTypeDto(getterIdService.getTypePause(id));
    }

    @Override
    public PageDto<TypeDto> consulterTypes(String mc, int page, int size) {
        Page<TypePause> pageType = typePauseRepository.findByLibelleContains(mc, PageRequest.of(page, size));

        PageDto<TypeDto> pageDto = new PageDto<>();
        pageDto.setPage(page);
        pageDto.setSize(size);
        pageDto.setTotalPage(pageType.getTotalPages());
        for(TypePause type : pageType.getContent()) {
            pageDto.getContent().add(dtoParser.toTypeDto(type));
        }
        return pageDto;
    }

    @Override
    public TypeDto addType(TypeRequest request) {
        TypePause type = new TypePause();
        type.setLibelle(request.getLibelle());
        type = typePauseRepository.save(type);
        return dtoParser.toTypeDto(type);
    }

    @Override
    public TypeDto editType(Integer id, TypeRequest request) throws Exception {
        TypePause type = getterIdService.getTypePause(id);
        type.setLibelle(request.getLibelle());
        type = typePauseRepository.save(type);
        return dtoParser.toTypeDto(type);
    }

    @Override
    public void deleteType(Integer id) throws Exception {
        TypePause type = getterIdService.getTypePause(id);
        typePauseRepository.delete(type);
    }
}
