package com.aice.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EnumMessageType{
     KAKAO("B1001","AT","A1506","알림톡")
    ,KAKAO_IMG("B1002","AI","A1506","이미지 알림톡")
    ,KAKAO_FRIEND("B1003","FT","A1506","친구톡")
    ,KAKAO_FRIEND_IMG("B1004","FI","A1506","이미지 친구톡")
    ,KAKAO_FRIEND_IMG_WIDE("B1005","FW","A1506","와이드 이미지 친구톡")
    ,SMS("B1006","SMS","A1503","SMS")
    ,LMS("B1007","LMS","A1504","LMS")
    ,MMS("B1008","MMS","A1505","MMS")
    ,E_MAIL("B1009","E-mail","A1507","E-mail")
    ,CALL_WITH_TALKBOT("B1011","CWTB","A1501","call with talkbot")
    ,CALL_TRANSFER("B1012","TCST","A1501","trans-call with staff")
    ,STUDIO_VIDEO("B1013","STUDIO_VIDEO","A1501","make video")
    ,WEB_CHAT("B1014","WEB_CHAT","A1508","chat")
    ,APP_PUSH("B1015","APP_PUSH","A1501","app push")
    ,EMAIL_POP3("B1016","Email Pop3","A1507","Email Pop3")
    ,EMAIL_IMAP("B1017","Email Imap","A1507","Email Imap")
    ;

    @JsonValue
    @Getter
    private final String value;

    @Getter
    private final String code;

    @Getter
    private final String codeFront;

    @Getter
    private final String description;

    public static EnumMessageType findByValue(String code) {
        return Arrays.stream(EnumMessageType.values())
                .filter(enumObj -> enumObj.getValue().equals(code) == true)
                .findFirst()
                .orElse(SMS);
    }
}