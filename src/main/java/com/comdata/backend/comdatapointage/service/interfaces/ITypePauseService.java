package com.comdata.backend.comdatapointage.service.interfaces;

import com.comdata.backend.comdatapointage.dto.TypeDto;
import com.comdata.backend.comdatapointage.request.TypeRequest;
import org.springframework.data.domain.Page;

public interface ITypePauseService {

    TypeDto consulterType(Integer id);
    Page<TypeDto> consulterTypes(String mc, int page, int size);
    TypeDto addType(TypeRequest request);
    TypeDto editType(Integer id, TypeRequest request);
    void deleteType(Integer id);
}
