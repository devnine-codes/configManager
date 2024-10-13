package com.aice.dao.dnis;

import com.aice.dao.DaoDtUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaoCompanyDnisCamelCase extends DaoDtUser {
    private Long pkCompanyDnis;
    private String solutionType;
    private String userType;
    private Long fkCompany;
    private Long fkCompanyStaff;
    private Long fkCompanyStaffAi;
    private String fdDnis;
    private String fullDnis;
    private String dnisType;
    private String useCategory;
    private String fwdNum;
    private String vgwId;
    private String defaultYn;
    private String interjYn;
    private String inboundYn;
    private String outboundYn;
    private String vgwAuthPw;
    private String fdBoundIo;
    private String fdUseYn;

    private String numberType;
    private String bizPhoneNum;
}
