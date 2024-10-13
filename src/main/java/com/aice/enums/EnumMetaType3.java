package com.aice.enums;

import java.util.Arrays;

public enum EnumMetaType3 {
    META_AUDIO("META_AUDIO","음성파일")
    ,META_DOC("META_DOC","문서파일")
    ,META_IMAGE("META_IMAGE","이미지파일")
    ,META_LINK("META_LINK","http link")
    ,META_TEXT("META_TEXT","텍스트")
    ,META_VIDEO("META_VIDEO","영상파일")
    ,META_VOICE("META_VOICE","voice-gw 전용")
    ,META_ZIP("META_ZIP","압축파일")
    ;
    private String value;
    private String desc;
    public String getValue(){
        return value;
    }
    public void setValue(String value){
        this.value = value;
    }
    public String getDesc(){
        return desc;
    }
    public void setDesc(String desc){
        this.desc = desc;
    }
    EnumMetaType3(String value, String desc){
        this.value = value;
        this.desc = desc;
    }
    public static EnumMetaType3 findByValue(String code) {
        return Arrays.stream(EnumMetaType3.values())
                .filter(enumObj -> enumObj.getValue().equals(code) == true)
                .findFirst()
                .orElse(META_DOC);
    }
}

















