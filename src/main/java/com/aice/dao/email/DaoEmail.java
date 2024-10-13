package com.aice.dao.email;

import com.aice.dao.DaoDtUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaoEmail extends DaoDtUser {
    private Long pkAiConfEmail;
    private Long fkCompany;
    private Long fkCompanyStaffAi;
    private String useType;
    private String useTypeName;
    private String confName;
    private String confHost;
    private Integer confPort;
    private String confId;
    private String confPw;
    private String confDispName;
    private String useAuthYn;
    private String useTlsYn;
    private String useSslYn;
    private String confKey;
    private String confVal;
    private String useYn;
}
