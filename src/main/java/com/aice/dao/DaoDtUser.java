package com.aice.dao;

import io.swagger.v3.oas.annotations.media.Schema;

public class DaoDtUser extends DaoDt {
    @Schema(hidden = true)
    private Long fkWriter;
    @Schema(hidden = true)
    private Long fkModifier;
    public Long getFkWriter() {
        return fkWriter;
    }
    public void setFkWriter(Long fkWriter) {
        this.fkWriter = fkWriter;
    }
    public Long getFkModifier() {
        return fkModifier;
    }
    public void setFkModifier(Long fkModifier) {
        this.fkModifier = fkModifier;
    }
}
