package com.aice.dao.secretary;

import com.aice.dao.DaoDtUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecretaryDao extends DaoDtUser {
    private Long fkCompany;
    private Long pkCompanyStaff;
    private Long fkCompanyStaffAi;
    private String fdStaffResponseStatusCode;
    private String responseStatusCode;
    private String autoResponseYn;
    private String fdCompanyName;
    private String fdStaffName;
    private String fdStaffId;
    private String solutionType;
    private String enableYn;

    private Long seqMember;
    private String contactAiYn;
    private String loggingYn;
    private String resStatus;
    private String dispMode;
    private String appMode;
    private String callFwdYn;
}
