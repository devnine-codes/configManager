package com.aice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DtoAiInfoRequest {
    private List<String> aiRole;
    private String fromDate;
    private String toDate;
}
