package com.comdata.backend.comdatapointage.service.interfaces;

import com.comdata.backend.comdatapointage.dto.PageDto;
import com.comdata.backend.comdatapointage.dto.TypeDto;
import com.comdata.backend.comdatapointage.request.TypeRequest;
import org.springframework.data.domain.Page;

public interface ITypePauseService {

    TypeDto consulterType(Integer id) throws Exception;
    PageDto<TypeDto> consulterTypes(String mc, int page, int size);
    TypeDto addType(TypeRequest request);
    TypeDto editType(Integer id, TypeRequest request) throws Exception;
    void deleteType(Integer id) throws Exception;
}
