package com.aice.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaoAiWork extends DaoDtUser {
    private Long pkAiConfWork;
    private Long fkCompany;
    private Long fkCompanyStaffAi;
    private String aiWorkCd;
    private String pAiWorkCd;
    private String frontStatus;
    private String botStatus;
    private String enableYn;
    private String openCompanyYn;
    private String openCompanyAddrYn;
    private String openCompanyLevelYn;
    private String openEmailYn;
    private String openBirthYn;
    private String useYn;

    private Long pkAiConfWorkTask;
    private Long pkAiConfIntro;
    private Long pkAiConfDayOn;

    private Long aiStaffSeq;

    //2024.05.07 Add
    private String contactAiYn; //톡봇연동여부
    private String loggingYn; //대화기록여부
}
