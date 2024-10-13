package com.aice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aice.dao.bulksend.DaoBulkSendPlan;
import com.aice.service.ServiceBulkSend;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("{apiVer}/bulk/send")
public class ControllerBulkSend {
    @Autowired ServiceBulkSend serviceBulkSend;
    // tbl_bulk_send_plan
    // tbl_bulk_send_member
    // 대량 발송 전화/문자
    // plan 을 만들고
    // plan 에 보낼 member 들을 등록
    @PostMapping("plan/create")
    public ResponseEntity<?> planCreate(
        @PathVariable("apiVer") String apiVer
        ,@RequestBody DaoBulkSendPlan req
    ){
        return ResponseEntity.ok(serviceBulkSend.planCreate(req));
    }

    @GetMapping("plan/list")
    public ResponseEntity<?> planList(
        @PathVariable("apiVer") String apiVer
        ,@PageableDefault(size=10,sort="send_dt_from",direction=Sort.Direction.DESC) Pageable pageable
        ,@RequestParam(value="fkCompany") Long fkCompany
        ,@RequestParam(value="sendDtFrom") String sendDtFrom
        ,@RequestParam(value="sendDtTo") String sendDtTo
        ,@RequestParam(value="channelType",required=false) String channelType
        ,@RequestParam(value="sendStatus",required=false) String sendStatus
        ,@RequestParam(value="fkStaff",required=false) Long fkStaff
    ){
        return ResponseEntity.ok(
            serviceBulkSend.planList(
                pageable
                ,fkCompany
                ,sendDtFrom
                ,sendDtTo
                ,channelType
                ,sendStatus
                ,fkStaff
            )
        );
    }
}




















