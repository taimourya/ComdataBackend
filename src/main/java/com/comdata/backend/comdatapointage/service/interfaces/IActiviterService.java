package com.comdata.backend.comdatapointage.service.interfaces;

import com.comdata.backend.comdatapointage.dto.ActiviterDto;
import com.comdata.backend.comdatapointage.dto.PageDto;
import com.comdata.backend.comdatapointage.request.ActiviterRequest;

public interface IActiviterService {

    ActiviterDto consulterActiviter(Integer id) throws Exception;
    PageDto<ActiviterDto> consulterActivites(String mc, int page, int size);

    ActiviterDto addActiviter(ActiviterRequest request);
    ActiviterDto editActiviter(Integer id, ActiviterRequest request) throws Exception;

    void enableActiviter(Integer id) throws Exception;
    void disableActiviter(Integer id) throws Exception;

}
