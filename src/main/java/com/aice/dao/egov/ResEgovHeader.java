package com.aice.dao.egov;

import com.aice.dao.DaoJson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResEgovHeader extends DaoJson {
    private String resultCode;
    private String resultMsg;
}
