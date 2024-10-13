package com.aice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.aice.dao.ResponseApi;
import com.aice.dao.email.DaoEmail;
import com.aice.repo.RepoEmail;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailService {
    @Autowired RepoEmail repoEmail;

    /**
     * 이메일 설정을 등록
     */
    public ResponseEntity<?> insertConfEmail(DaoEmail req) {
        ResponseEntity<?> res = ResponseEntity.ok(null);
        try{
            log.info("insertConfEmail req:{}",req.toJsonTrim());
            DaoEmail daoFound = repoEmail.findOneByUseType(req);
            if(ObjectUtils.isEmpty(daoFound) == true) {
                repoEmail.insertConfEmail(req);
            }else {
                req.setPkAiConfEmail(daoFound.getPkAiConfEmail());
                repoEmail.updateConfEmail(req);
            }

            res = ResponseEntity.ok(ResponseApi.success("",req));
        }catch(Exception e1){
            log.error("insertConfEmail error req:{}",req.toJsonTrim());
            log.error("insertConfEmail error",e1);
            res = ResponseEntity.ok(ResponseApi.error("email.conf.create.undefined",req));
        }
        return res;
    }

    /**
     * 이메일 설정을 업데이트한다.
     */
    public ResponseEntity<?> updateConfEmail(DaoEmail req) {
        ResponseEntity<?> res = ResponseEntity.ok(null);
        try{
            log.info("updateConfEmail req:{}",req.toJsonTrim());
            repoEmail.updateConfEmail(req);
            res = ResponseEntity.ok(ResponseApi.success("",req));
        }catch(Exception e1){
            log.error("updateConfEmail error req:{}",req.toJsonTrim());
            log.error("updateConfEmail error",e1);
            res = ResponseEntity.ok(ResponseApi.error("email.conf.update.undefined",req));
        }
        return res;
    }

    /**
     * *이메일 리스트를 조건에 따라 조회한다. 조건은 다음과 같다 . * outgoing 은 발신, reception은 수신, all 은 모든
     * 메일을 조회한다
     * 
     * @return ResponseEntity<List<EmailVo>>
     */
    public ResponseEntity<?> findAll(
        Long fkCompany
        ,Long fkCompanyStaffAi
        ,String useType
    ) {
        ResponseEntity<?> res = ResponseEntity.ok(null);
        try{
            DaoEmail req = new DaoEmail();
            req.setFkCompany(fkCompany);
            req.setFkCompanyStaffAi(fkCompanyStaffAi);
            req.setUseType(useType);
            List<DaoEmail> emailVoList = repoEmail.findAll(req);
            res = ResponseEntity.ok(ResponseApi.success("",emailVoList));
        }catch(Exception e1){
            log.error("findAll error fkCompany:{},fkCompanyStaffAi:{},useType:{}",fkCompany,fkCompanyStaffAi,useType);
            log.error("findAll error",e1);
            res = ResponseEntity.ok(ResponseApi.error("email.conf.list.undefined",null));
        }
        return res;
    }

    /**
     * * 이메일 설정을 삭제한다.
     */
    public ResponseEntity<?> deleteConfEmail(
        Long fkCompany
        ,Long fkCompanyStaffAi
        ,String useType
    ) {
        ResponseEntity<?> res = ResponseEntity.ok(null);
        try{
            DaoEmail req = new DaoEmail();
            req.setFkCompany(fkCompany);
            req.setFkCompanyStaffAi(fkCompanyStaffAi);
            req.setUseType(useType);
            repoEmail.deleteConfEmail(req);
            res = ResponseEntity.ok(ResponseApi.success("",req));
        }catch(Exception e1){
            log.error("deleteConfEmail error fkCompany:{},fkCompanyStaffAi:{},useType:{}",fkCompany,fkCompanyStaffAi,useType);
            log.error("deleteConfEmail error",e1);
            res = ResponseEntity.ok(ResponseApi.error("email.conf.delete.undefined",null));
        }
        return res;
    }
}













