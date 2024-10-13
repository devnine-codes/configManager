package com.aice.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaoCompanyStaff extends DaoDtUser {
    private Long pkCompanyStaff;
    private String fdStaffStatusCode;
    private String solutionType;
    private Long fkCompany;
    private String fdStaffAiUid;
    private String fdCompanyName;
    private String fdStaffName;
    private String interjYn;

    private String fdStaffAiYn;
    private String fdCompanyMasterYn;

    private Integer quickStartStatus;
    private Integer quickStartBotStatus;
    private String quickStartFrom;
    private String quickStartTo;
    private String botDisplayYn;

    private Long aiStaffFrom;
    private Long aiStaffTo;

    private String fdDefaultAi;

    private String fromDate;
    private String toDate;
}
