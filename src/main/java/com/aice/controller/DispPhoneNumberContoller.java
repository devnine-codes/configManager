package com.aice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aice.dao.bizphone.DaoBizPhone;
import com.aice.service.ServiceBizPhone;

import lombok.extern.slf4j.Slf4j;

/**
 * 회선관리,대표번호관리
 */
@Slf4j
@RestController
@RequestMapping("/disp/phone/number")
public class DispPhoneNumberContoller {
    @Autowired ServiceBizPhone serviceBizPhone;

    @PostMapping("/create")
    public ResponseEntity<?> insertBizPhoneNumber(
        @RequestBody DaoBizPhone req
    ){
        return serviceBizPhone.insertBizPhoneNumber(req);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateBizPhoneNumber(
        @RequestBody DaoBizPhone req
    ){
        return serviceBizPhone.updateBizPhoneNumber(req);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBizPhoneNumber(
        @RequestParam(required=true) Long fkCompany
        ,@RequestParam(required=true) String bizPhoneNum
        ,@RequestParam(required=true) String dnis
    ){
        return serviceBizPhone.deleteBizPhoneNumber(fkCompany,bizPhoneNum,dnis);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listBizPhoneNumber(
        @RequestParam(required=true) Long fkCompany
        ,@RequestParam(required=false) String bizPhoneNum
        ,@RequestParam(required=false) String dnis
        ,@RequestParam(required=false) String inboundYn
        ,@RequestParam(required=false) String outboundYn
        ,@RequestParam(required=false) String phoneYn
        ,@RequestParam(required=false) String smsYn
        ,@RequestParam(required=false) String enableYn
    ){
        return serviceBizPhone.listBizPhoneNumber(
            fkCompany
            ,bizPhoneNum
            ,dnis
            ,inboundYn
            ,outboundYn
            ,phoneYn
            ,smsYn
            ,enableYn
        );
    }

    @GetMapping("/detail")
    public ResponseEntity<?> detailBizPhoneNumber(
        @RequestParam(required=true) Long fkCompany
        ,@RequestParam(required=true) String bizPhoneNum
    ){
        return serviceBizPhone.detailBizPhoneNumber(fkCompany,bizPhoneNum);
    }

    @PostMapping("/docs/upload")
    public ResponseEntity<?> docsUpload(
        @RequestParam(required=true,name="file") MultipartFile file
        ,@RequestParam(required=true) Long fkCompany
        ,@RequestParam(required=true) String bizPhoneNum
        ,@RequestParam(required=true) String metaType2
        ,@RequestParam(required=true) String metaType4
    ){
        return serviceBizPhone.docsUpload(file,fkCompany,bizPhoneNum,metaType2,metaType4);
    }
}






















