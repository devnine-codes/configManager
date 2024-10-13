package com.aice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AiInfoResponse {

    private int totalCnt;
    private List<DtoAiInfo> list;

    public AiInfoResponse(List<DtoAiInfo> list, int totalCnt) {
        this.totalCnt = totalCnt;
        this.list = list;
    }
}
