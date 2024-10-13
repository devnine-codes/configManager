package com.aice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.aice.conf.ConfFeign;
import com.aice.dao.msggw.ReqMsgMain;

@FeignClient(
    name="api-message-gw"
    ,url="${message-gw.hostname}"
    ,decode404=true
    ,configuration={ConfFeign.class}
)
public interface ServiceApiMessageGw {
    @PostMapping(
        value="/aice/msggw/messages/v1/send/bulk/sms"
        ,consumes=MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> sendBulkSms(@RequestBody ReqMsgMain reqMsgMain);

    @PostMapping(
        value="/aice/msggw/messages/v1/send/bulk/kakao"
        ,consumes=MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> sendBulkKakao(@RequestBody ReqMsgMain reqMsgMain);
}
