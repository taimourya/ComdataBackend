package com.comdata.backend.comdatapointage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ColSupActiviterDto extends UserDto {
    private Integer activiterId;
    private String activiterName;


}
