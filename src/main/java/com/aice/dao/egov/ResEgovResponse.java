package com.aice.dao.egov;

import com.aice.dao.DaoJson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResEgovResponse extends DaoJson {
    private ResEgovHeader header;
    private ResEgovBody body;
}
