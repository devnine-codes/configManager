package com.aice.enums;

import java.util.Arrays;

public enum EnumMetaType2 {
    UnDefined("CTGR2000","파일 메타 타입 상세")
    ,CallRec("CTGR2001","통화녹음")
    ,CallRecScript("CTGR2002","통화녹취록")
    ,Intro_Depart("CTGR2003","담당부서 안내")
    ,Connect_Manager("CTGR2004","담당자 및 부서 연결")
    ,Reception("CTGR2005","고객 요청 접수 및 전달")
    ,CTGR2006("CTGR2006","연락처 안내")
    ,CTGR2007("CTGR2007","오시는 길 및 주차 안내")
    ,CTGR2008("CTGR2008","회사 첫인사 설정")
    ,CTGR2009("CTGR2009","긴급멘트 안내")
    ,CTGR2010("CTGR2010","회사 소개")
    ,CTGR2011("CTGR2011","부서 정보 안내")
    ,CTGR2012("CTGR2012","홈페이지 주소 안내")
    ,CTGR2013("CTGR2013","창립일 안내")
    ,CTGR2014("CTGR2014","채용 안내")
    ,CTGR2015("CTGR2015","복리후생제도 안내")
    ,CTGR2016("CTGR2016","면접 안내")
    ,CTGR2017("CTGR2017","휴가 안내")
    ,CTGR2018("CTGR2018","근태 안내")
    ,CTGR2019("CTGR2019","상장일 안내")
    ,CTGR2020("CTGR2020","공시제도 안내")
    ,CTGR2021("CTGR2021","대표번호신청서")
    ,CTGR2022("CTGR2022","발신번호신청서")
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
    EnumMetaType2(String value, String desc){
        this.value = value;
        this.desc = desc;
    }
    public static EnumMetaType2 findByValue(String code) {
        return Arrays.stream(EnumMetaType2.values())
                .filter(enumObj -> enumObj.getValue().equals(code) == true)
                .findFirst()
                .orElse(UnDefined);
    }
}
















