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
@RequestMapping("/{apiVer}/work")
@Deprecated
public class ControllerWork {
    @PostMapping("create")
    public ResponseEntity<?> companyListCreate(
        @PathParam("apiVer") String apiVer
    ){
        // fkCompany,fkCompanyStaffAi 의 직무 설정
        // ai_conf_work.png, ojt_design.jpg 참조
        // ojt 의 step1, step2 까지만 저장
        // tree 구조로 저장
        // step1 = ai 와 1:1
        //         ai별로 하나만 선택 가능하고 수정 불가
        // step2 = ai 와 1:N
        //         step1 의 자식 노드
        //         on/off 형태
        //         on/off 상태에 상관없이 무조건 row 생성
        return ResponseEntity.ok("list");
    }

    @GetMapping("list")
    public ResponseEntity<?> companyListRead(
        @PathParam("apiVer") String apiVer
        ,@RequestParam("fkCompany") Long fkCompany
        ,@RequestParam(required=false,value="fkCompanyStaffAi") Long fkCompanyStaffAi
        ,@RequestParam(required=false,value="aiWorkCd") String aiWorkCd
        ,@RequestParam(required=false,value="pAiWorkCd") String pAiWorkCd
    ){
        // fkCompany,fkCompanyStaffAi 의 직무 설정
        return ResponseEntity.ok("list");
    }

    @PutMapping("{pkAiConfWork}")
    public ResponseEntity<?> companyListUpdate(
        @PathParam("apiVer") String apiVer
        ,@PathParam("pkAiConfWork") Long pkAiConfWork
    ){
        return ResponseEntity.ok("list");
    }

    @DeleteMapping("{pkAiConfWork}")
    public ResponseEntity<?> companyListDelete(
        @PathParam("apiVer") String apiVer
        ,@PathParam("pkAiConfWork") Long pkAiConfWork
    ){
        return ResponseEntity.ok("list");
    }
}



























