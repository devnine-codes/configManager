package com.aice.dao.vgw;


import java.util.List;

import com.aice.dao.DaoJson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResVgwMain extends DaoJson {
    private String command;
    private String response;
    private String message;
    private String err;
    private String number;
    private List<ResVgwNumberPlans> numberPlans;
    private ResVgwNumbers numbers;

    private String extension;
    private String dnis;
}
