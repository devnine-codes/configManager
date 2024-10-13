package com.aice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aice.dao.bizMsg.BizMsgRequestData;
import com.aice.service.ServiceApiBizMsgAdmin;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/{apiVer}/kakao/adm")
public class CtrlKakaoAdm {
    @Autowired ServiceApiBizMsgAdmin serviceApiBizMsgAdmin;

    // 발신 프로필 추가시 인증번호 요청
    // /v2/sender/token
    @GetMapping(
        value="sender/token"
    )
    Map<String,Object> senderToken(
        @RequestParam(value="plusFriend") String plusFriend //@스윗트래커
        ,@RequestParam(value="phoneNumber") String phoneNumber //고객사 관리자 핸드폰 번호
    ) {
        return serviceApiBizMsgAdmin.senderToken(plusFriend,phoneNumber);
    }

    // 발신프로필 카테고리 전체 조회
    @GetMapping(
        value="sender/category/all"
    )
    Map<String,Object> senderCategoryAll(){
        return serviceApiBizMsgAdmin.senderCategoryAll();
    }

    // 발신프로필 전체 조회
    @GetMapping(
        value="sender/list"
    )
    Map<String,Object> senderList(
        @RequestParam(value="userId") String userId //비즈엠 계정
    ){
        return serviceApiBizMsgAdmin.senderList(userId);
    }

    // 발신 프로필 추가 (V3)
    @PostMapping(
        value="sender/create"
    )
    Map<String,Object> senderCreate(
        @RequestBody BizMsgRequestData req
    ){
        return serviceApiBizMsgAdmin.senderCreate(
                req.getUserId()
                ,req.getPlusFriend()
                ,req.getPhoneNumber()
                ,req.getToken()
                ,req.getCategoryCode()
            );
    }

    // 템플릿 목록 조회
    @GetMapping(
        value="template/list"
    )
    Map<String,Object> templateList(
        @RequestParam(value="userId") String userId
        ,@RequestParam(value="senderKey") String senderKey
    ){
        return serviceApiBizMsgAdmin.templateList(userId,senderKey);
    }

    // 템플릿 카테고리 전체조회
    @GetMapping(
        value="template/category/all"
    )
    Map<String,Object> templateCategoryAll(){
        return serviceApiBizMsgAdmin.templateCategoryAll();
    }

    @PostMapping(
        value="template/create"
    )
    List<Map<String,Object>> templateCreate(
        @RequestParam(value="userId") String userId
        ,@RequestBody List<BizMsgRequestData> req
    ){
        return serviceApiBizMsgAdmin.templateCreate(userId,req);
    }

    @PostMapping(
        value="template/update"
    )
    Map<String,Object> templateUpdate(
        @RequestParam(value="userId") String userId
        ,@RequestBody BizMsgRequestData req
    ){
        return serviceApiBizMsgAdmin.templateUpdate(userId,req);
    }

    @PostMapping(
        value="template/delete"
    )
    List<Map<String,Object>> templateDelete(
        @RequestParam(value="userId") String userId
        ,@RequestBody List<BizMsgRequestData> req
    ){
        return serviceApiBizMsgAdmin.templateDelete(userId,req);
    }

    @PostMapping(
        value="template/request"
    )
    List<Map<String,Object>> templateRequest(
        @RequestParam(value="userId") String userId
        ,@RequestBody List<BizMsgRequestData> req
    ){
        return serviceApiBizMsgAdmin.templateRequest(userId,req);
    }

    @PostMapping(
        value="template/cancel_request"
    )
    List<Map<String,Object>> templateCancelRequest(
        @RequestParam(value="userId") String userId
        ,@RequestBody List<BizMsgRequestData> req
    ){
        return serviceApiBizMsgAdmin.templateCancelRequest(userId,req);
    }

    @PostMapping(
        value="template/cancel_approval"
    )
    List<Map<String,Object>> templateCancelApproval(
        @RequestParam(value="userId") String userId
        ,@RequestBody List<BizMsgRequestData> req
    ){
        return serviceApiBizMsgAdmin.templateCancelApproval(userId,req);
    }

    /**
     * 발신번호 관리
     */

    @GetMapping(
        value="callback/list"
    )
    Map<String,Object> callbackList(
        @RequestParam(value="userId") String userId
        ,@RequestParam(value="userkey") String userkey
        ,@RequestParam(value="page") String page
        ,@RequestParam(value="rowCount",required=false) String rowCount
    ){
        return serviceApiBizMsgAdmin.callbackList(userId,userkey,page,rowCount);
    }

    @PostMapping(
        value="callback/create"
        ,consumes=MediaType.MULTIPART_FORM_DATA_VALUE
    )
    Map<String,Object> callbackCreate(
        @RequestPart(value="userId") String userId
        ,@RequestPart(value="userkey") String userkey
        ,@RequestPart(required=true) MultipartFile file
        ,@RequestPart String callback
        ,@RequestPart(required=false) String token
        ,@RequestPart String comments
        ,@RequestPart String method
    ){
//        log.info("comments:{}",comments);
        return serviceApiBizMsgAdmin.callbackCreate(userId,userkey,file,callback,token,comments,method);
//        return null;
    }

    @PostMapping(
        value="callback/delete"
    )
    List<Map<String,Object>> callbackDelete(
        @RequestParam(value="userId") String userId
        ,@RequestParam(value="userkey") String userkey
        ,@RequestBody List<BizMsgRequestData> req
    ){
        return serviceApiBizMsgAdmin.callbackDelete(userId,userkey,req);
    }
}



















