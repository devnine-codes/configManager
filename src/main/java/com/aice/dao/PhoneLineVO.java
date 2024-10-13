package com.aice.dao;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneLineVO extends DaoDtUser implements Serializable{
    private String vgwId;
    private String number;
    private String type;
    private String inUse;
    private String name;
    private String referenceId;
}