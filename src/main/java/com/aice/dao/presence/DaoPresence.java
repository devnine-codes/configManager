package com.aice.dao.presence;

import com.aice.dao.DaoDtUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaoPresence extends DaoDtUser {
    private Long seqCompany;
    private Long seqStaffAi;
    private Long seqMember;
    private String enableYn;
    private String resStatus;
    private String dispMode;
    private String solutionType;
    private String callFwdYn;
    private String activationYn;
}