package com.aice.controller;

import javax.websocket.server.PathParam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/{apiVer}/msg")
@Deprecated
public class ControllerMsg {
    @PostMapping("create")
    public ResponseEntity<?> companyListCreate(
        @PathParam("apiVer") String apiVer
    ){
        // fkCompany,fkCompanyStaffAi 의 sms,kakao 설정
        return ResponseEntity.ok("list");
    }

    @GetMapping("list")
    public ResponseEntity<?> companyListRead(
        @PathParam("apiVer") String apiVer
        ,@RequestParam("fkCompany") Long fkCompany
        ,@RequestParam(required=false,value="fkCompanyStaffAi") Long fkCompanyStaffAi
    ){
        // fkCompany,fkCompanyStaffAi 의 sms,kakao 설정
        return ResponseEntity.ok("list");
    }

    @PutMapping("{pkAiConfMsg}")
    public ResponseEntity<?> companyListUpdate(
        @PathParam("apiVer") String apiVer
        ,@PathParam("pkAiConfMsg") Long pkAiConfMsg
    ){
        return ResponseEntity.ok("list");
    }

    @DeleteMapping("{pkAiConfMsg}")
    public ResponseEntity<?> companyListDelete(
        @PathParam("apiVer") String apiVer
        ,@PathParam("pkAiConfMsg") Long pkAiConfMsg
    ){
        return ResponseEntity.ok("list");
    }
}



























