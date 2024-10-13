package com.aice.dao.bizMsg;

import com.aice.dao.DaoJson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BizmCtgrItem extends DaoJson {
    private String parentCode;
    private String code;
    private String name;
}
