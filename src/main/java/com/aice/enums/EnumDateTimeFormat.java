package com.aice.enums;

public enum EnumDateTimeFormat{
    Ymd("yyyy-MM-dd")
    ,YmdHms("yyyy-MM-dd HH:mm:ss")
    ,YmdHmsTrim("yyyyMMddHHmmss")
    ,YmdHmsMi("yyyy-MM-dd HH:mm:ss.SSS")
    ,YmdHmsMiTrim("yyyyMMddHHmmssSSS")
    ;
    private String dtFormat;
    public String getDtFormat() {
        return dtFormat;
    }
    public void setDtFormat(String dtFormat) {
        this.dtFormat = dtFormat;
    }
    EnumDateTimeFormat(String dtFormat){
        this.dtFormat = dtFormat;
    }
}
