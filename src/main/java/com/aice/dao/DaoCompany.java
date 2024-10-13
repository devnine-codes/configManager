package com.aice.dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaoCompany extends DaoDtUser {
    private Long pkCompany;
    private String fdCompanyId;
    private String fdCompanyName;
    private String solutionType;

}
