package com.aice.dao.external;

import com.aice.dao.DaoDtUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaoExtApi extends DaoExtApiMeta {
    private Long pkConfExtApi;
    private Long fkCompany;
    private String apiType;
    private String dispName;
    private String useYn;
}
