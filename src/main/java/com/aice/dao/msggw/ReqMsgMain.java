package com.aice.dao.msggw;

import java.util.List;

import com.aice.dao.DaoJson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqMsgMain extends DaoJson {
    private Long fkCompany;
    private Long fkCompanyStaffAi;
    private String solutionType;

    // OJT 알림 문구 채널 설정
    // INSTANT:바로전송
    // CUSTOMER : 고객 접수시 알림받을 채널
    // STAFF : 고객 접수시 담당자가 받을 채널
    // TKMANAGER : 마감 직전 담당자가 받을 채널
    private String notiCheckType;

    // B1001:알림톡 (sendBizMessage)
    // B1002:이미지알림톡
    // B1003:친구톡
    // B1004:이미지친구톡
    // B1005:와이드이미지친구톡
    // B1006:SMS (sendMessage)
    // B1007:LMS
    // B1008:MMS
    // B1009:EMail (sendEmail)
    private String msgType;
    private String actCode;
    private String templateCode;
    private String channelType;
    private String tbBrokerId;
    private String fkCallId;
    private Long fkIssueTicket;
    private String refId;
    private String reserveDt;
    private String idFrom;
    private String idTo;
    private String title;
    private String body;
    private String payYn = "Y";
    private List<String> idsTo;
    private String svcType;
    private String jobCode;
}






















