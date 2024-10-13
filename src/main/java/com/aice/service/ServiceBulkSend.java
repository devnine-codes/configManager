package com.aice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.aice.dao.ResponseApi;
import com.aice.dao.bulksend.DaoBulkSendMember;
import com.aice.dao.bulksend.DaoBulkSendPlan;
import com.aice.dao.msggw.ReqMsgMain;
import com.aice.dao.vgw.ReqVgwMain;
import com.aice.dao.vgw.ResVgwMain;
import com.aice.enums.EnumDateTimeFormat;
import com.aice.enums.EnumMessageType;
import com.aice.repo.RepoBulkSend;
import com.aice.util.UtilDateTime;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServiceBulkSend {
    @Autowired RepoBulkSend repoBulkSend;
    @Autowired ServiceApiMessageGw serviceApiMessageGw;
    @Autowired VoiceGatewayServiceAPI voiceGatewayServiceAPI;

    public ResponseApi<?> planCreate(DaoBulkSendPlan req){
        ResponseApi<DaoBulkSendPlan> res = new ResponseApi<>();
        UtilDateTime utilDateTime = new UtilDateTime();
        try {
            if(ObjectUtils.isEmpty(req.getPkBulkSendPlan()) == true) {
                res.setStatus(ResponseApi.ERROR_STATUS);
                res.setMessage("config.manager.bulk.send.plan.create.input.pkBulkSendPlan.empty");
                return res;
            }
            if(ObjectUtils.isEmpty(req.getFkCompany()) == true) {
                res.setStatus(ResponseApi.ERROR_STATUS);
                res.setMessage("config.manager.bulk.send.plan.create.input.fkCompany.empty");
                return res;
            }
            if(ObjectUtils.isEmpty(req.getFkStaffAi()) == true) {
                res.setStatus(ResponseApi.ERROR_STATUS);
                res.setMessage("config.manager.bulk.send.plan.create.input.fkStaffAi.empty");
                return res;
            }
            if(ObjectUtils.isEmpty(req.getDnis()) == true) {
                res.setStatus(ResponseApi.ERROR_STATUS);
                res.setMessage("config.manager.bulk.send.plan.create.input.dnis.empty");
                return res;
            }
            if(ObjectUtils.isEmpty(req.getChannelType()) == true) {
                res.setStatus(ResponseApi.ERROR_STATUS);
                res.setMessage("config.manager.bulk.send.plan.create.input.channelType.empty");
                return res;
            }

            repoBulkSend.upsertBulkPlan(req);
            List<DaoBulkSendMember> listMem = req.getListMember();
            if(ObjectUtils.isEmpty(listMem) == false) {
                String strDtFrom = "";
                if("00000000000000".equals(req.getReserveDt()) == true) {
                    strDtFrom = utilDateTime.getCurrentDateTimeString(EnumDateTimeFormat.YmdHms.getDtFormat());
                }else {
                    LocalDateTime ldtFrom = utilDateTime.StringToLocalDateTime(req.getReserveDt(),EnumDateTimeFormat.YmdHmsTrim.getDtFormat());
                    strDtFrom = utilDateTime.LocalDateTimeToString(ldtFrom,EnumDateTimeFormat.YmdHms.getDtFormat());
                }
                for(DaoBulkSendMember itemMem : listMem) {
                    itemMem.setSendDtFrom(strDtFrom);
                }
                repoBulkSend.upsertBulkMember(req);
            }

            EnumMessageType enumMessageType = EnumMessageType.findByValue(req.getChannelType());
            switch(enumMessageType) {
//            case KAKAO:
//            case KAKAO_IMG:
//            case KAKAO_FRIEND:
//            case KAKAO_FRIEND_IMG:
//            case KAKAO_FRIEND_IMG_WIDE:
//                // call notification
//                try {
//                    serviceApiMessageGw.sendBulkKakao(null);
//                }catch(Exception e2) {
//                    log.error("planCreate error req:{}",req.toJsonTrim());
//                    log.error("planCreate error",e2);
////                    res.setStatus(ResponseApi.ERROR_STATUS);
////                    res.setMessage("config.manager.bulk.send.plan.create.undefined");
//                }
//                break;
            case SMS:
            case LMS:
            case MMS:
                // call notification
                this.planCreateSendSms(req);
                break;
            case CALL_WITH_TALKBOT:
            case CALL_TRANSFER:
                // call voice gw
                this.planCreateSendCall(req);
                break;
            default:
                break;
            }

            res.setData(req);
            res.setStatus(ResponseApi.SUCCESS_STATUS);
        }catch(Exception e1) {
            log.error("planCreate error req:{}",req.toJsonTrim());
            log.error("planCreate error",e1);
            res.setStatus(ResponseApi.ERROR_STATUS);
            res.setMessage("config.manager.bulk.send.plan.create.undefined");
        }
        return res;
    }

    void planCreateSendSms(DaoBulkSendPlan req){
        try {
            ReqMsgMain reqMsgMain = new ReqMsgMain();
            reqMsgMain.setFkCompany(req.getFkCompany());
            reqMsgMain.setFkCompanyStaffAi(req.getFkStaffAi());
            reqMsgMain.setSolutionType("B11");
            reqMsgMain.setNotiCheckType("INSTANT");
            reqMsgMain.setMsgType(req.getChannelType());
//            reqMsgMain.setActCode();
//            reqMsgMain.setTemplateCode();
            reqMsgMain.setChannelType("voice");
//            reqMsgMain.setTbBrokerId();
//            reqMsgMain.setFkCallId(req.getPkBulkSendPlan());
//            reqMsgMain.setFkIssueTicket();
            reqMsgMain.setReserveDt(req.getReserveDt());
            reqMsgMain.setIdFrom(req.getNumberFrom());
            reqMsgMain.setTitle(req.getMsgTitle());
            reqMsgMain.setBody(req.getMsgBody());
            reqMsgMain.setPayYn("Y");
            List<String> listNum = req.getListMember().stream()
                .map(mem -> mem.getNumberTo())
                .collect(Collectors.toList());
            reqMsgMain.setIdsTo(listNum);
            reqMsgMain.setSvcType("TBBR");
            reqMsgMain.setJobCode(req.getPkBulkSendPlan());
            serviceApiMessageGw.sendBulkSms(reqMsgMain);
        }catch(Exception e1) {
            log.error("planCreateSendSms error req:{}",req.toJsonTrim());
            log.error("planCreateSendSms error",e1);
        }
    }

    void planCreateSendCall(DaoBulkSendPlan req){
        try {
            ReqVgwMain reqVgwPlan = new ReqVgwMain();
            reqVgwPlan.setCommand("create");
            reqVgwPlan.setJobCode(req.getPkBulkSendPlan());
            reqVgwPlan.setJobName(req.getPkBulkSendPlan());
            reqVgwPlan.setDnis(req.getDnis());
            reqVgwPlan.setSvcType("BULK_CALL");
            reqVgwPlan.setCompanySeq(req.getFkCompany());
            reqVgwPlan.setAiStaffSeq(req.getFkStaffAi());
            reqVgwPlan.setCallerId(req.getNumberFrom());
            List<DaoBulkSendMember> listMem = req.getListMember();
            if(ObjectUtils.isEmpty(listMem) == false) {
                String numTos = listMem.stream()
                    .map(item -> item.getNumberTo())
                    .collect(Collectors.joining(","));
                reqVgwPlan.setNumbers(numTos);
            }
            ResponseEntity<ResVgwMain> resPlan = voiceGatewayServiceAPI.acs(req.getFullDnis(),reqVgwPlan);
            log.info("resPlan:{}",resPlan.getStatusCodeValue());
            if(HttpStatus.ACCEPTED == resPlan.getStatusCode()) {
                ReqVgwMain reqVgwPlanStart = new ReqVgwMain();
                BeanUtils.copyProperties(reqVgwPlan,reqVgwPlanStart);
                reqVgwPlanStart.setCommand("start");
                String strDtFrom = "";
                UtilDateTime utilDateTime = new UtilDateTime();
                if("00000000000000".equals(req.getReserveDt()) == true) {
                    strDtFrom = utilDateTime.getCurrentDateTimeString(EnumDateTimeFormat.YmdHms.getDtFormat());
                }else {
                    LocalDateTime ldtFrom = utilDateTime.StringToLocalDateTime(req.getReserveDt(),EnumDateTimeFormat.YmdHmsTrim.getDtFormat());
                    strDtFrom = utilDateTime.LocalDateTimeToString(ldtFrom,EnumDateTimeFormat.YmdHms.getDtFormat());
                    reqVgwPlanStart.setStartTime(strDtFrom);//YYYY-MM-DD HH:MM:SS
                }
                ResponseEntity<ResVgwMain> resPlanStart = voiceGatewayServiceAPI.acs(req.getFullDnis(),reqVgwPlanStart);
                log.info("resPlanStart:{}",resPlanStart.getStatusCodeValue());
            }
        }catch(Exception e1) {
            log.error("planCreateSendCall error req:{}",req.toJsonTrim());
            log.error("planCreateSendCall error",e1);
        }
    }

    public ResponseApi<?> planList(
        Pageable pageable
        ,Long fkCompany
        ,String sendDtFrom
        ,String sendDtTo
        ,String channelType
        ,String sendStatus
        ,Long fkStaff
    ){
        ResponseApi<Page<DaoBulkSendPlan>> res = new ResponseApi<>();
        DaoBulkSendPlan req = new DaoBulkSendPlan();
        req.setFkCompany(fkCompany);
        req.setSendDtFrom(sendDtFrom);
        req.setSendDtTo(sendDtTo);
        req.setChannelType(channelType);
        req.setSendStatus(sendStatus);
        req.setFkStaff(fkStaff);
        try {
            int cntAll = repoBulkSend.countAllPagingBulkPlan(req);
            List<DaoBulkSendPlan> listItem = repoBulkSend.findAllPagingBulkPlan(pageable,req);
            Page<DaoBulkSendPlan> page = new PageImpl<>(listItem,pageable,cntAll);
            res.setData(page);
        }catch(Exception e1) {
            log.error("planList error req:{}",req.toJsonTrim());
            log.error("planList error",e1);
        }
        return res;
    }
}


















