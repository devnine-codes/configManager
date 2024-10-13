package com.aice.dao.extension;

import com.aice.dao.DaoDtUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaoExtension extends DaoExtensionOb {
    private Long pkConfDialExtAihandy;
    private Long fkCompany;
    private Long seqMember;
    //private Long fkCompanyStaff;
    private Long fkCompanyStaffAi;
    private String dnis;
    private String extNum;
    private String dialNum;
    private String fullDnis;
    private String dnisType;
    private String vgwAuthPw;
    private String useYn;

    private String callFwdYn;
    private String mobileMainYn;
}
