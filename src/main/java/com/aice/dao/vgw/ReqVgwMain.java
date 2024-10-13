package com.aice.dao.vgw;

import com.aice.dao.DaoJson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqVgwMain extends DaoJson {
    private String command;
    private String jobCode;
    private String jobName;
    private Integer callInterval;
    private Integer retryCount;
    private Integer retryInterval;
    private String dnis;
    private Integer maxCallCount;
    private String callerId;
    private String numbers;
    private String svcType;
    private Long companySeq;
    private Long aiStaffSeq;
    private String startTime;
    private String forwardedNumber;

}






















