package com.aice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aice.dao.DaoConfigManager;
import com.aice.dao.ResponseApi;
import com.aice.service.SvcAiConfNoti;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/{apiVer}/noti")
public class ControllerNoti {
    @Autowired SvcAiConfNoti svcAiConfNoti;

    @GetMapping("list")
    public ResponseEntity<?> aiConfNotiChktpList(
        @PathVariable("apiVer") String apiVer
        ,@PageableDefault(size=10,sort="pk_ai_conf_noti",direction=Sort.Direction.ASC) Pageable pageable
        ,@RequestParam(required=false,value="seqCompany") Long seqCompany
        ,@RequestParam(required=false,value="seqStaffAi") Long seqStaffAi
        ,@RequestParam(required=false,value="checkType") String checkType
    ){
        return ResponseEntity.ok(svcAiConfNoti.findAllPaging(pageable,seqCompany,seqStaffAi,checkType));
    }

    @GetMapping("detail/seq/{seqAiConfNoti}")
    public ResponseEntity<?> aiConfNotiChktpDetail(
        @PathVariable("apiVer") String apiVer
        ,@PathVariable("seqAiConfNoti") Long seqAiConfNoti
    ){
        return ResponseEntity.ok(svcAiConfNoti.findOne(seqAiConfNoti));
    }

    @PostMapping("chktp/{checkType}")
    public ResponseEntity<?> aiConfNotiChktpCreate(
        @PathVariable("apiVer") String apiVer
        ,@PathVariable("checkType") String checkType
        ,@RequestBody DaoConfigManager req
    ){
        // fkCompany,fkCompanyStaffAi 의 sms,kakao 설정
        // 채널종류,CUSTOMER,STAFF,TKMANAGER
        // CUSTOMER : 고객에게 알림이 발생했을때 보낼것인지
        // STAFF : 직원에게 알림이 발생했을때 보낼것인지
        // TKMANAGER : 티켓매니저에서 알림이 발생했을때 보낼것인지
        log.info("ai.conf.noti.chktp.create checkType:{}",checkType);
        req.setCheckType(checkType);
        if(ObjectUtils.isEmpty(req.getSeqCompany()) == true) {
            return ResponseEntity.ok(ResponseApi.error("ai.conf.noti.chktp.create.err.empty.seqCompany",null));
        }
        if(ObjectUtils.isEmpty(req.getCheckType()) == true) {
            return ResponseEntity.ok(ResponseApi.error("ai.conf.noti.chktp.create.err.empty.checkType",null));
        }
        return ResponseEntity.ok(svcAiConfNoti.upsert(req));
    }

    @PatchMapping("chktp/{checkType}")
    public ResponseEntity<?> aiConfNotiChktpUpdate(
        @PathVariable("apiVer") String apiVer
        ,@PathVariable("checkType") String checkType
        ,@RequestBody DaoConfigManager req
    ){
        req.setCheckType(checkType);
        if(ObjectUtils.isEmpty(req.getSeqCompany()) == true) {
            return ResponseEntity.ok(ResponseApi.error("ai.conf.noti.chktp.update.err.empty.seqCompany",null));
        }
        if(ObjectUtils.isEmpty(req.getCheckType()) == true) {
            return ResponseEntity.ok(ResponseApi.error("ai.conf.noti.chktp.update.err.empty.checkType",null));
        }
        return ResponseEntity.ok(svcAiConfNoti.upsert(req));
    }

    @DeleteMapping("chktp/{checkType}")
    public ResponseEntity<?> aiConfNotiChktpDelete(
        @PathVariable("apiVer") String apiVer
        ,@PathVariable("checkType") String checkType
        ,@RequestBody DaoConfigManager req
    ){
        req.setCheckType(checkType);
        if(ObjectUtils.isEmpty(req.getSeqCompany()) == true) {
            return ResponseEntity.ok(ResponseApi.error("ai.conf.noti.chktp.delete.err.empty.seqCompany",null));
        }
        if(ObjectUtils.isEmpty(req.getCheckType()) == true) {
            return ResponseEntity.ok(ResponseApi.error("ai.conf.noti.chktp.delete.err.empty.checkType",null));
        }
        req.setUseYn("N");
        return ResponseEntity.ok(svcAiConfNoti.upsert(req));
    }

    @PostMapping("all")
    public ResponseEntity<?> aiConfNotiAllCreate(
        @PathVariable("apiVer") String apiVer
        ,@PathVariable("checkType") String checkType
        ,@RequestBody DaoConfigManager req
    ){
        if(ObjectUtils.isEmpty(req.getSeqCompany()) == true) {
            return ResponseEntity.ok(ResponseApi.error("ai.conf.noti.all.upsert.err.empty.seqCompany",null));
        }
        return ResponseEntity.ok(svcAiConfNoti.insertAll(req));
    }
}



























