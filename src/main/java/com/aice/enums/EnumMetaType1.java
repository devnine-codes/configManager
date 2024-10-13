package com.aice.enums;

import java.util.Arrays;

public enum EnumMetaType1 {
    CallRec("CTGR1001","통화녹음")
    ,CallRecScript("CTGR1002","통화녹취록")
    ,Receptionist("CTGR1003","Receptionist")
    ,HR("CTGR1004","HR (Human Resource)")
    ,Marketing("CTGR1005","Marketing")
    ,IR("CTGR1006","IR")
    ,Finance("CTGR1007","재무")
    ,Sales("CTGR1008","영업")
    ,Delivery("CTGR1009","배송관리")
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
    EnumMetaType1(String value, String desc){
        this.value = value;
        this.desc = desc;
    }
    public static EnumMetaType1 findByValue(String code) {
        return Arrays.stream(EnumMetaType1.values())
                .filter(enumObj -> enumObj.getValue().equals(code) == true)
                .findFirst()
                .orElse(Receptionist);
    }
}
















