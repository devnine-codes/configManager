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

import com.aice.dao.email.DaoEmail;
import com.aice.service.EmailService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/email")
public class EmailController {
    @Autowired EmailService emailService;

    /**
     * 이메일 설정을 등록
     */
    @PostMapping("/create")
    public ResponseEntity<?> insertConfEmail(@RequestBody DaoEmail req) {
        return emailService.insertConfEmail(req);
    }

    /**
     * *등록한 이메일 정보 변경
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateConfEmail(@RequestBody DaoEmail req) {
        return emailService.updateConfEmail(req);
    }

    /**
     * * 특정 회사의 이메일 조회. 조회 방식 변경 가능(전체, 수신, 발신 분류)
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<?> findAll(
        @RequestParam(required=true) Long fkCompany
        ,@RequestParam(required=false) Long fkCompanyStaffAi
        ,@RequestParam(required=true) String useType
    ) {
        return emailService.findAll(fkCompany,fkCompanyStaffAi,useType);
    }

    /**
     * * 등록한 이메일 삭제
     * 
     * @param emailId
     */

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCompanyEmail(
        @RequestParam(required=true) Long fkCompany
        ,@RequestParam(required=false) Long fkCompanyStaffAi
        ,@RequestParam(required=true) String useType
    ) {
        return emailService.deleteConfEmail(fkCompany,fkCompanyStaffAi,useType);
    }

    /**
     * * 이메일 발신 테스트용 API , 기능 구현되지 않음
     * 
     * @param emailSendVo
     * @return
     */
    @PostMapping("/send/test")
    public boolean sendTest(@RequestBody DaoEmail req) {
        return true;
    }
}
