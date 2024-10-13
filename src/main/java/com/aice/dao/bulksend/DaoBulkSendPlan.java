package com.aice.dao.bulksend;

import java.util.List;

import com.aice.dao.DaoDtUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaoBulkSendPlan extends DaoDtUser {
    private String pkBulkSendPlan;
    private Long fkCompany;
    private Long fkStaffAi;
    private String numberFrom;
    private String fullDnis;
    private String dnis;
    private String reserveYn;
    private String reserveDt;
    private String channelType;
    private String msgTitle;
    private String msgBody;
    private String pathAttach;
    private Integer cntUser;
    private String agreeAdYn;
    private Long fkStaff;
    private String chkLevel;
    private String memo;
    private String sendStatus;
    private String sendDtFrom;
    private String sendDtTo;
    private List<DaoBulkSendMember> listMember;
}
