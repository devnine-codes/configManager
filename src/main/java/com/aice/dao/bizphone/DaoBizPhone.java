package com.aice.dao.bizphone;

import com.aice.dao.DaoDtUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaoBizPhone extends DaoDtUser {
    private Long pkCompanyPhone;
    private String solutionType;
    private String userType;
    private Long fkCompany;
    private String bizPhoneName;
    private String bizPhoneNum;
    private String dnis;
    private String regStatus;
    private String docStatus;
    private String docMainStatus;
    private String callForwardStatus;
    private String defaultYn;
    private String inboundYn;
    private String outboundYn;
    private String phoneYn;
    private String smsYn;
    private String enableYn;
    private String useYn;
    private Integer useOrder;
    private String filePathMainNum;
    private String filePathJoinCert;
    private String filePathCompanyCert;
    private String filePathAttorney;
    private String filePathTradeCert;
    private String filePathEmployCert;
    private String bizCateStep1;
    private String bizCateStep2;
    private String bizCateStep3;

    private String metaType2;
    private String metaType4;
    private String companyName;
    private String ploonetYn;
    private String mainPhoneNum;
    private Long fkCompanyStaff;
    private String staffName;
}