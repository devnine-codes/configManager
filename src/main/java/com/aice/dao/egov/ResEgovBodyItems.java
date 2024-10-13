package com.aice.dao.egov;

import java.util.List;

import com.aice.dao.DaoJson;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResEgovBodyItems extends DaoJson {
    private List<ResEgovBodyItemsItem> item;
}
