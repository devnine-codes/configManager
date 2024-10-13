package com.aice.dao.vgw;

import com.aice.dao.DaoDtUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResVgwNumberPlans extends DaoDtUser {
    private String command;
    private String number;
    private String type;
    private String inUse;
    private String name;
    private String vgwId;
    private String serviceType;
}
