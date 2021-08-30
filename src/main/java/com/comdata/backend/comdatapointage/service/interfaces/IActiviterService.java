package com.comdata.backend.comdatapointage.service.interfaces;

import com.comdata.backend.comdatapointage.dto.ActiviterDto;
import com.comdata.backend.comdatapointage.dto.PageDto;
import com.comdata.backend.comdatapointage.request.ActiviterRequest;

public interface IActiviterService {

    ActiviterDto consulterActiviter(Integer id);
    PageDto<ActiviterDto> consulterActivites(String mc, int page, int size);

    ActiviterDto addActiviter(ActiviterRequest request);
    ActiviterRequest editActiviter(Integer id, ActiviterRequest request);

    void enableActiviter(Integer id);
    void disableActiviter(Integer id);

}
