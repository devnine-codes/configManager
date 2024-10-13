package com.aice.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaoCompanyCustomer extends DaoDtUser {
    private Long pkCompanyCustomer;
    private Long fkCompany;
    private String fdCustomerName;
    private String fdActiveState;
    private String fdCompanyName;
    private String fdCompanyDept;
    private String fdCompanyPosition;
    private String fdCustomerMobile;
    private String fdCustomerPhone;
    private String fdCustomerEmail;
    private String fdCustomerKakaoUid;
    private String fdCustomerChatUid;
    private String contactChannelFrom;
    private String contactDateFrom;
    private String contactAiYn;
}
