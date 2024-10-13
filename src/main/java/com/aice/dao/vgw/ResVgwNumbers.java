package com.aice.dao.vgw;

import com.aice.dao.DaoDtUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResVgwNumbers extends DaoDtUser {
    private String command;
    private String number;
    private String numberType;
    private String forwardedNumber;
    private String password;
    private String callerId;
    private Long maxCallCountIn;
    private Long maxCallCountOut;
    private String name;
    private Long noanswerTimeout;
    private String cbHost;
    private String cbPort;
    private String sttServer;
    private String ttsServer;
    private String ttsMasterKey;
    private Long ttsSid;
    private String language;
    private String referenceId;
    private String vgwId;
}
