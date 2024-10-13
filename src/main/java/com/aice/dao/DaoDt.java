package com.aice.dao;

import io.swagger.v3.oas.annotations.media.Schema;

public class DaoDt extends DaoJson {
    @Schema(hidden = true)
    private String fdRegdate;
    @Schema(hidden = true)
    private String fdModdate;
    public String getFdRegdate() {
        return fdRegdate;
    }
    public void setFdRegdate(String fdRegdate) {
        this.fdRegdate = fdRegdate;
    }
    public String getFdModdate() {
        return fdModdate;
    }
    public void setFdModdate(String fdModdate) {
        this.fdModdate = fdModdate;
    }
}
