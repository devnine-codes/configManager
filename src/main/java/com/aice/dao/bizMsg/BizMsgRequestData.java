package com.aice.dao.bizMsg;

import com.aice.dao.DaoJson;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BizMsgRequestData extends DaoJson {
    private String userId; //사용자ID
    private String plusFriend; //카카오톡 채널 아이디(예: @스윗트래커)
    private String phoneNumber; //인증번호 요청시 사용한 관리자 핸드폰 번호
    private String token; //인증토큰 번호
    private String senderKey; //발신프로필키
    private String templateCode; //템플릿 코드
    private String templateName; //템플릿명
    private String templateMessageType; //템플릿 메시지 유형(BA: 기본형(default))
    private String templateEmphasizeType; //템플릿 강조 유형(NONE: 선택안함(default))
    private String templateContent; //템플릿 내용(최대 1,000자)
    private String categoryCode; //템플릿 카테고리 코드
    private String senderKeyType;
    private String templateExtra;
    private String templateTitle;
    private String templateSubtitle;
    private String templateImageUrl;
    private String templateHeader;
    private String templateItemHighlight;
    private String templateItem;
    private String securityFlag;
    private String buttonName;
//    private String quickReplies;
    private String templateRepresentLink;
    private String templateAd;
    private String modifiedAt;
    private String pcFlag;
    private String inspectReqDt;
    private String createdAt;
    private String buttonType;
    private String inspectDt;
//    private String buttons;
    private String active;
    private String buttonUrl;
    private String templateImageName;
    private String dormant;
    private String inspectionStatus;
    private String templatePreviewMessage;
    private String status;

    private String newSenderKey;
    private String newTemplateCode;
    private String newTemplateName;
    private String newTemplateMessageType;
    private String newTemplateEmphasizeType;
    private String newTemplateContent;

    private String userkey;
    private String callback;
    private String comments;
    private String method;
}





















