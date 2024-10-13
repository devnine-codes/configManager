package com.aice.dao.businesstime;

import com.aice.dao.DaoDtUser;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DaoDayOn extends DaoDtUser{
	private Long pkAiConfDayOn;
	private Long fkCompany;
	private Long fkCompanyStaffAi;
	private Integer weekNum;
	private Integer timeFromHh;
	private Integer timeFromMin;
	private Integer timeToHh;
	private Integer timeToMin;
	private String workType;
	private String timeType;
	private String msgIntro;
	private String msgClose;
	private String enableYn;
	private String useYn;

	private String fdCompanyName;
	private String fdStaffName;
	private String fdStaffAiYn;

	private String searchDate;
	private String searchTime;
}
