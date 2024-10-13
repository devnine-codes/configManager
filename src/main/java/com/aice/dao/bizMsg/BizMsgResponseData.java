package com.aice.dao.bizMsg;

import java.util.List;
import java.util.Map;

import com.aice.dao.DaoJson;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BizMsgResponseData extends DaoJson {
    private String code; //처리 성공/실패 여부 (success: 성공, fail: 실패)
    private String data; //발신프로필 키
    private String message; //오류 메시지
    private Map<String,List<BizmCtgrItem>> dataCtgr;
}
