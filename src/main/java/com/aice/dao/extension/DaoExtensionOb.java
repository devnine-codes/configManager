package com.aice.dao.extension;

import com.aice.dao.DaoDtUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaoExtensionOb extends DaoDtUser {
    private Long seqConfDialExtObAihandy;
    private String solutionType;
    private Long seqCompany;
    private Long seqStaff;
    private Long seqMember;
    private String obType;
    private String agreeYn;
    private String obAgree;
    private String phoneYn;
    private String smsYn;
    private String useYn;
    private String originDnis;

    private String obNumber;
    private String outboundNumber;
}