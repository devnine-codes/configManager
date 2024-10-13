package com.aice.dao.egov;

import com.aice.dao.DaoJson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResEgovBody extends DaoJson {
    private ResEgovBodyItems items;
    private Integer numOfRows;
    private Integer pageNo;
    private Integer totalCount;
}
