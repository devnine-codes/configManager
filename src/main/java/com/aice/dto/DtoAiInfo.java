package com.aice.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoAiInfo {

    /* tbl_company_staff */
    private Long aiStaffSeq;
    private Long companySeq;
    private String fdStaffStatusCode;
    private String fdStaffName;
    private String fdStaffAiUid;
    private String newAi;
    private String fdDefaultAi;
    private Integer fdStaffPersona;
    private Integer quickStartStatus;
    private Integer quickStartBotStatus;
    private String quickStartFrom;
    private String quickStartTo;
    private String botDisplayYn;

    private String companyId;
    private String companyName;

    /* tbl_code */
    private String fdName;

    /* tbl_ai_conf_work */
    private String frontStatus;

    /* tbl_ai_policy_work */
    private String staffWorkCodeName;

    /* tbl_company_dnis */
    private String dnisNum;

    /* tbl_ai_conf_avatar_img */
    private String dispName;
    private String aiPolicyAvatarImg;
    private String botStatus;
    private String botId;
    private String botRole;

}
