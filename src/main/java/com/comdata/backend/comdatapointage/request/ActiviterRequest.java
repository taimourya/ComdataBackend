package com.comdata.backend.comdatapointage.request;

import lombok.Data;

@Data
public class ActiviterRequest {

    private String name;
    private int tFermetureSessionMs;
    private int tInactiviteMs;

}
