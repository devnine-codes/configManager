package com.aice.dao.bulksend;

import com.aice.dao.DaoDtUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaoBulkSendMember extends DaoDtUser {
    private String fkBulkSendPlan;
    private String numberTo;
    private String fkCustomer;
    private String channelType;
    private String sendLogId;
    private String sendStatus;
    private String sendDtFrom;
    private String sendDtTo;
    private String cntRetry;
}
