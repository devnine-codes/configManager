package com.aice.dao.businesstime;

import com.aice.dao.DaoDtUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaoDayOff extends DaoDtUser{
	private Long pkAiConfDayOff;
	private Long fkCompany;
	private Long fkCompanyStaffAi;
	private String dayOffFrom;
	private String dayOffTo;
	private String timeType;
	private String dispName;
	private String msgIntro;
	private String msgClose;
	private String useYn;

	private String oldDayOffFrom;
	private String solYear;
}
