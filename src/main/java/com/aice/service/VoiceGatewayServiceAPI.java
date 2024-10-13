package com.aice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.aice.conf.ConfFeign;
import com.aice.dao.vgw.ReqVgwMain;
import com.aice.dao.vgw.ResVgwMain;

@FeignClient(
    name="voice-gw-api"
    ,url="${voice-gw.hostname}"
    ,decode404=true
    ,configuration={ConfFeign.class}
)
public interface VoiceGatewayServiceAPI {
    /* voice gateway api - numberplans */
    @PostMapping(
        value="voicegw/numberplans"
        ,consumes=MediaType.APPLICATION_JSON_VALUE
        ,produces=MediaType.APPLICATION_JSON_VALUE
    )
    ResVgwMain requestNumberPlan(@RequestBody String req);

    /* voice gateway api - numbers */
    @PostMapping(
        value="voicegw/numbers/{extension}"
        ,consumes=MediaType.APPLICATION_JSON_VALUE
        ,produces=MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ResVgwMain> requestNumber(@PathVariable String extension, @RequestBody String req);

    @PostMapping(
        value="acs/{extension}"
        ,consumes=MediaType.APPLICATION_JSON_VALUE
        ,produces=MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ResVgwMain> acs(@PathVariable String extension, @RequestBody ReqVgwMain req);
}















