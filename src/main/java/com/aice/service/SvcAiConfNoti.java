package com.aice.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aice.dao.DaoConfigManager;
import com.aice.dao.ResponseApi;
import com.aice.repo.RepoAiConfNoti;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SvcAiConfNoti {
    @Autowired RepoAiConfNoti repoAiConfNoti;

    public ResponseApi<?> findAllPaging(
        Pageable pageable
        ,Long seqCompany
        ,Long seqStaffAi
        ,String checkType
    ){
        String tag = "ai.conf.noti.find.all.paging";
        DaoConfigManager req = new DaoConfigManager();
        req.setSeqCompany(seqCompany);
        req.setSeqStaffAi(seqStaffAi);
        req.setCheckType(checkType);

        try {
            int cntAll = 0;
            List<DaoConfigManager> listItem = null;
            cntAll = repoAiConfNoti.findAllPagingCnt(req);
            listItem = repoAiConfNoti.findAllPaging(pageable,req);

            Page<DaoConfigManager> page = new PageImpl<>(listItem,pageable,cntAll);
            return ResponseApi.success("",page);
        }catch(Exception e1) {
            log.error("{} error req:{}",tag,req.toJsonTrim());
            log.error(tag,e1);
            return ResponseApi.error(tag,null);
        }
    }

    public ResponseApi<?> findOne(
        Long seqAiConfNoti
    ){
        String tag = "ai.conf.noti.find.one";
        DaoConfigManager req = new DaoConfigManager();
        req.setSeqAiConfNoti(seqAiConfNoti);

        try {
            DaoConfigManager item = repoAiConfNoti.findOne(req);
            return ResponseApi.success("",item);
        }catch(Exception e1) {
            log.error("{} error req:{}",tag,req.toJsonTrim());
            log.error(tag,e1);
            return ResponseApi.error(tag,null);
        }
    }

    public ResponseApi<?> upsert(
        DaoConfigManager req
    ){
        String tag = "ai.conf.noti.upsert";

        try {
            repoAiConfNoti.upsert(req);
            return ResponseApi.success("",req);
        }catch(Exception e1) {
            log.error("{} error req:{}",tag,req.toJsonTrim());
            log.error(tag,e1);
            return ResponseApi.error(tag,null);
        }
    }

    public ResponseApi<?> insertAll(
        DaoConfigManager req
    ){
        String tag = "ai.conf.noti.insert.all";

        try {
            DaoConfigManager req1 = new DaoConfigManager();
            BeanUtils.copyProperties(req,req1);
            req1.setCheckType("CUSTOMER");
            repoAiConfNoti.upsert(req1);

            DaoConfigManager req2 = new DaoConfigManager();
            BeanUtils.copyProperties(req,req2);
            req2.setCheckType("STAFF");
            repoAiConfNoti.upsert(req2);

            DaoConfigManager req3 = new DaoConfigManager();
            BeanUtils.copyProperties(req,req3);
            req3.setCheckType("TKMANAGER");
            repoAiConfNoti.upsert(req3);

            return ResponseApi.success("",Arrays.asList(req1,req2,req3));
        }catch(Exception e1) {
            log.error("{} error req:{}",tag,req.toJsonTrim());
            log.error(tag,e1);
            return ResponseApi.error(tag,null);
        }
    }
}




















