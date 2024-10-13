package com.aice.dao.egov;

import com.aice.dao.DaoJson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResEgovBodyItemsItem extends DaoJson {
    private Integer seq;
    // dateKind
    // 01:국경일(어린이 날, 광복절, 개천절)
    // 02:기념일(의병의 날, 정보보호의 날, 4·19 혁명 기념일)
    // 03:24절기(청명, 경칩, 하지)
    // 04:잡절(단오, 한식)
    private String dateKind;
    private String dateName;
    private String isHoliday;// 공공기관 휴일여부 Y/N
    private String locdate;// yyyyMMdd
}
