package com.aice.dao;

import com.aice.dao.bizMsg.BizMsgRequestData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DaoMsgTemp extends DaoDtUser {
    private Long pkMsgTemp;
    private Long fkCompany;
    private String tempCodeVendor;
    private String actCode;
    private String msgType;
    private String msgTitle;
    private String msgBody;
    private String memo;
    private String useYn;
    private List<BizMsgRequestData> bizReqItems;
}