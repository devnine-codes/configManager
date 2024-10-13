package com.aice.service;

import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.aice.dao.DaoFileLog;
import com.aice.dao.ResponseApi;
import com.aice.dao.bizphone.DaoBizPhone;
import com.aice.enums.EnumMetaType1;
import com.aice.enums.EnumMetaType2;
import com.aice.enums.EnumMetaType3;
import com.aice.repo.RepoBizPhone;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServiceBizPhone {
    @Autowired RepoBizPhone repoBizPhone;
    @Autowired ServiceApiFileGw serviceApiFileGw;

    public ResponseEntity<?> insertBizPhoneNumber(
        DaoBizPhone req
    ){
        ResponseEntity<?> res = ResponseEntity.ok(null);
        try{
            log.info("insertBizPhoneNumber req:{}",req.toJsonTrim());
            DaoBizPhone daoFound = repoBizPhone.findOne(req);

            if (req.getBizPhoneNum().substring(0,3).equals("070")) {
                req.setRegStatus("COMPLETE");
            }
            if(ObjectUtils.isEmpty(daoFound) == true) {
                DaoBizPhone isDefault = repoBizPhone.findDefaultOne(req);
                if (ObjectUtils.isEmpty(isDefault) == false) {
                    req.setDefaultYn("N");
                }
                repoBizPhone.create(req);
            }else {
                req.setPkCompanyPhone(daoFound.getPkCompanyPhone());
                repoBizPhone.update(req);
            }

            res = ResponseEntity.ok(ResponseApi.success("",req));
        }catch(Exception e1){
            log.error("insertBizPhoneNumber error req:{}",req.toJsonTrim());
            log.error("insertBizPhoneNumber error",e1);
            res = ResponseEntity.ok(ResponseApi.error("bizphone.conf.create.undefined",req));
        }
        return res;
    }

    public ResponseEntity<?> updateBizPhoneNumber(
        DaoBizPhone req
    ){
        ResponseEntity<?> res = ResponseEntity.ok(null);
        try{
            log.info("updateBizPhoneNumber req:{}",req.toJsonTrim());
            repoBizPhone.update(req);
            res = ResponseEntity.ok(ResponseApi.success("",req));
        }catch(Exception e1){
            log.error("updateBizPhoneNumber error req:{}",req.toJsonTrim());
            log.error("updateBizPhoneNumber error",e1);
            res = ResponseEntity.ok(ResponseApi.error("bizphone.conf.update.undefined",req));
        }
        return res;
    }

    public ResponseEntity<?> deleteBizPhoneNumber(
        Long fkCompany
        ,String bizPhoneNum
        ,String dnis
    ){
        ResponseEntity<?> res = ResponseEntity.ok(null);
        DaoBizPhone req = new DaoBizPhone();
        req.setFkCompany(fkCompany);
        req.setBizPhoneNum(bizPhoneNum);
        req.setDnis(dnis);
        try{
            repoBizPhone.delete(
                fkCompany
                ,bizPhoneNum
                ,dnis
            );
            res = ResponseEntity.ok(ResponseApi.success("",req));
        }catch(Exception e1){
            log.error("deleteBizPhoneNumber error req:{}",req.toJsonTrim());
            log.error("deleteBizPhoneNumber error",e1);
            res = ResponseEntity.ok(ResponseApi.error("bizphone.conf.delete.undefined",null));
        }
        return res;
    }

    public ResponseEntity<?> listBizPhoneNumber(
        Long fkCompany
        ,String bizPhoneNum
        ,String dnis
        ,String inboundYn
        ,String outboundYn
        ,String phoneYn
        ,String smsYn
        ,String enableYn
    ){
        ResponseEntity<?> res = ResponseEntity.ok(null);
        DaoBizPhone req = new DaoBizPhone();
        req.setFkCompany(fkCompany);
        req.setBizPhoneNum(bizPhoneNum);
        req.setDnis(dnis);
        req.setInboundYn(inboundYn);
        req.setOutboundYn(outboundYn);
        req.setPhoneYn(phoneYn);
        req.setSmsYn(smsYn);
        req.setEnableYn(enableYn);
        try{
            List<DaoBizPhone> listItem = repoBizPhone.findAll(req);
            res = ResponseEntity.ok(ResponseApi.success("",listItem));
        }catch(Exception e1){
            log.error("listBizPhoneNumber error req:{}",req.toJsonTrim());
            log.error("listBizPhoneNumber error",e1);
            res = ResponseEntity.ok(ResponseApi.error("bizphone.conf.list.undefined",null));
        }
        return res;
    }

    public ResponseEntity<?> detailBizPhoneNumber(
        Long fkCompany
        ,String bizPhoneNum
    ){
        ResponseEntity<?> res = ResponseEntity.ok(null);
        DaoBizPhone req = new DaoBizPhone();
        req.setFkCompany(fkCompany);
        req.setBizPhoneNum(bizPhoneNum);
        try{
            DaoBizPhone listItem = repoBizPhone.findOne(req);
            res = ResponseEntity.ok(ResponseApi.success("",listItem));
        }catch(Exception e1){
            log.error("detailBizPhoneNumber error req:{}",req.toJsonTrim());
            log.error("detailBizPhoneNumber error",e1);
            res = ResponseEntity.ok(ResponseApi.error("bizphone.conf.detail.undefined",null));
        }
        return res;
    }

    public ResponseEntity<?> docsUpload(
        MultipartFile file
        ,Long fkCompany
        ,String bizPhoneNum
        ,String metaType2
        ,String metaType4
    ){
        ResponseEntity<?> res = ResponseEntity.ok(null);
        DaoBizPhone req = new DaoBizPhone();
        req.setFkCompany(fkCompany);
        req.setBizPhoneNum(bizPhoneNum);
        req.setMetaType2(metaType2);
        req.setMetaType4(metaType4);
        try {
            log.info("docsUpload req:{}",req.toJsonTrim());
            DaoBizPhone itemPhone = repoBizPhone.findOne(req);

            EnumMetaType1 enumMetaType1 = EnumMetaType1.Receptionist;
            //CTGR2021:대표번호신청서
            //CTGR2022:발신번호신청서
            EnumMetaType2 enumMetaType2 = EnumMetaType2.findByValue(metaType2);
            EnumMetaType3 enumMetaType3 = EnumMetaType3.META_DOC;

            DaoFileLog daoFileLog = new DaoFileLog();
            daoFileLog.setSolutionType(itemPhone.getSolutionType());
            daoFileLog.setUserType(itemPhone.getUserType());
            daoFileLog.setFkCompany(Long.toString(fkCompany));
            daoFileLog.setMeta1(enumMetaType1.getValue());
            daoFileLog.setMeta2(enumMetaType2.getValue());
            daoFileLog.setMeta3(enumMetaType3.getValue());
            daoFileLog.setMeta4(metaType4);

//            FileItem fileItem = new DiskFileItem(
//                file.getOriginalFilename()
//                ,file.getContentType()
//                ,false
//                ,file.getName()
//                ,(int)file.getSize()
//                ,null
//            );
//            file.getInputStream().transferTo(fileItem.getOutputStream());
//            MultipartFile pathFile = new CommonsMultipartFile(fileItem);
//            MultipartFile pathFile = new MockMultipartFile(
//                "pathFile"
//                ,fileName
//                ,MediaType.IMAGE_PNG.getType()
//                ,file.getInputStream()
//            );

            ResponseEntity<DaoFileLog> resFileGw = serviceApiFileGw.uploadAttachFile(daoFileLog,file);
            DaoFileLog resBodyFileGw = resFileGw.getBody();
            if(ObjectUtils.isEmpty(resBodyFileGw) == false) {
                itemPhone.setFilePathMainNum(resBodyFileGw.getFilePath());
                repoBizPhone.update(itemPhone);
            }
            res = ResponseEntity.ok(ResponseApi.success("",itemPhone));
        }catch(Exception e1) {
            log.error("docsUpload error req:{}",req.toJsonTrim());
            log.error("docsUpload error",e1);
        }
        return res;
    }
}
















