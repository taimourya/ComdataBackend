package com.comdata.backend.comdatapointage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PageDto<T> {

    private int size;
    private int page;
    private int totalPage;
    private List<T> content = new ArrayList<>();

}
