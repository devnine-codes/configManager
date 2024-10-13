package com.aice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aice.conf.ConfFeign;

@FeignClient(
    name="api-data-go-kr"
    ,url="${data-go-kr.hostname}"
    ,decode404=true
    ,configuration={ConfFeign.class}
)
public interface ServiceApiDataGoKr {
    // 국경일
    @GetMapping(
        value="B090041/openapi/service/SpcdeInfoService/getHoliDeInfo"
        ,consumes=MediaType.APPLICATION_JSON_VALUE
        ,produces=MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> getHoliDeInfo(
        @RequestParam(value="serviceKey") String serviceKey
        ,@RequestParam(value="pageNo") String pageNo
        ,@RequestParam(value="numOfRows") String numOfRows
        ,@RequestParam(value="solYear") String solYear
        ,@RequestParam(value="solMonth",required=false) String solMonth
    );

    // 공휴일
    @GetMapping(
        value="B090041/openapi/service/SpcdeInfoService/getRestDeInfo"
        ,consumes=MediaType.APPLICATION_JSON_VALUE
        ,produces=MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> getRestDeInfo(
        @RequestParam(value="serviceKey") String serviceKey
        ,@RequestParam(value="pageNo") String pageNo
        ,@RequestParam(value="numOfRows") String numOfRows
        ,@RequestParam(value="solYear") String solYear
        ,@RequestParam(value="solMonth",required=false) String solMonth
    );

    // 기념일
    @GetMapping(
        value="B090041/openapi/service/SpcdeInfoService/getAnniversaryInfo"
        ,consumes=MediaType.APPLICATION_JSON_VALUE
        ,produces=MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> getAnniversaryInfo(
        @RequestParam(value="serviceKey") String serviceKey
        ,@RequestParam(value="pageNo") String pageNo
        ,@RequestParam(value="numOfRows") String numOfRows
        ,@RequestParam(value="solYear") String solYear
        ,@RequestParam(value="solMonth",required=false) String solMonth
    );

    // 24절기
    @GetMapping(
        value="B090041/openapi/service/SpcdeInfoService/get24DivisionsInfo"
        ,consumes=MediaType.APPLICATION_JSON_VALUE
        ,produces=MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> get24DivisionsInfo(
        @RequestParam(value="serviceKey") String serviceKey
        ,@RequestParam(value="pageNo") String pageNo
        ,@RequestParam(value="numOfRows") String numOfRows
        ,@RequestParam(value="solYear") String solYear
        ,@RequestParam(value="solMonth",required=false) String solMonth
    );

    // 잡절
    @GetMapping(
        value="B090041/openapi/service/SpcdeInfoService/getSundryDayInfo"
        ,consumes=MediaType.APPLICATION_JSON_VALUE
        ,produces=MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> getSundryDayInfo(
        @RequestParam(value="serviceKey") String serviceKey
        ,@RequestParam(value="pageNo") String pageNo
        ,@RequestParam(value="numOfRows") String numOfRows
        ,@RequestParam(value="solYear") String solYear
        ,@RequestParam(value="solMonth",required=false) String solMonth
    );
}

























