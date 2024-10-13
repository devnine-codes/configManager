package com.aice.dao.ment;

import com.aice.dao.DaoDtUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaoMent extends DaoDtUser {
    private Long fkCompany;
    private Long fkCompanyStaffAi;

    private String msgBefore;
    private String msgBody;
    private String msgBody2;
    private String msgBody3;
    private String msgBody4;
    private String msgBody5;
    private String msgBody6;
    private String msgAfter;
    private String msgOff;
    private String defaultYn;
    private String warnYn;
    private String useYn;
}
