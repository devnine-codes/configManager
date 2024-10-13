package com.aice.dao.external;

import com.aice.dao.DaoDtUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaoExtApiMeta extends DaoDtUser {
    private Long pkConfExtApiMeta;
    private Long fkConfExtApi;
    private String metaKey;
    private String metaVal;
    private String useYn;
}
