package com.aice.filter;

import com.aice.dao.DaoJson;

public class VoHttpLog extends DaoJson {
    private VoHttpLogItem req;
    private VoHttpLogItem res;
    public VoHttpLogItem getReq() {
        return req;
    }
    public void setReq(VoHttpLogItem req) {
        this.req=req;
    }
    public VoHttpLogItem getRes() {
        return res;
    }
    public void setRes(VoHttpLogItem res) {
        this.res=res;
    }
}
