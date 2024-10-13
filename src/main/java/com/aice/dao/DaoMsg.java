package com.aice.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaoMsg extends DaoDtUser{
    private Long pkAiConfMsg;
    private Long fkCompany;
    private Long fkCompanyStaffAi;
    private String useType;
    private String confHost;
    private String confProfile;
    private String confId;
    private String confPw;
    private String sendYn;
    private String useYn;
}