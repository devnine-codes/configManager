package com.aice.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaoConfigManager extends DaoDtUser {
    private Long seqCompany;
    private Long seqStaffAi;
    private String checkType;
    private String smsYn;
    private String kakaoYn;
    private String pushYn;
    private String msgBody;
    private String useYn;
    private Long seqAiConfNoti;

    private Long pkCompany;
    private String solutionType;
    private String userType;
    private String fdCompanyId;
    private String fdCompanyStatusCode;
    private String fdCompanyName;

    private Long pkCompanyDnis;
    private Long fkCompany;
    private Long fkCompanyStaff;
    private Long fkCompanyStaffAi;
    private String fdDnis;
    private String fullDnis;
    private String vgwId;
    private String interjYn;
    private String inboundYn;
    private String outboundYn;
    private String vgwAuthPw;
    private String fdBoundIo;
    private String fdUseYn;
    private String bizPhoneNum;
    private String numberType;

    private String pkFileLog;
    private String meta1;
    private String meta2;
    private String meta3;
    private String metaValue;
    private String fileName;
    private String fileSize;
    private String callId;
    private String fkIssueTicket;
    private String filePath;

    private Long pkCompanyStaff;
    private String fdStaffId;
    private String fdStaffName;
    private String fdStaffMobile;
    private String fdStaffPhone;
    private String fdStaffEmail;
}
