package com.aice.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.aice.dao.ResponseApi;
import com.aice.dao.bulksend.DaoBulkSendMember;
import com.aice.dao.bulksend.DaoBulkSendPlan;
import com.aice.enums.EnumDateTimeFormat;
import com.aice.enums.EnumMessageType;
import com.aice.service.ServiceBulkSend;
import com.aice.util.UtilDateTime;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@MockMvcEncoding
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
public class TestBulkSend {
    static {
      System.setProperty("app.home","./");
      System.setProperty("LOG_DIR","./logs");
      System.setProperty("spring.profiles.active","local");
    }

    @Autowired MockMvc mockMvc;
    @Autowired WebApplicationContext ctx;
    @Autowired ObjectMapper objectMapper;

    @Autowired ServiceBulkSend serviceBulkSend;

    @Test
    void testMain() {
//        this.testCreateSms();
        this.testCreateCall();
//        this.testList();
    }

    @Test
    @Nested 
    @DisplayName("bulk send sms conf create")
    void testCreateSms() {
        try {
            DaoBulkSendPlan req = new DaoBulkSendPlan();

            Long fkCompany = 1415L;
            Long fkCompanyStaffAi = 3989L;
            String fdDnis = "45000084";
            String fdFullDnis = "07045000084";
            UtilDateTime utilDateTime = new UtilDateTime();
            StringBuilder sbPk = new StringBuilder();
            sbPk.append("junit-OBC-");
            sbPk.append(utilDateTime.getCurrentDateTimeString(EnumDateTimeFormat.YmdHmsTrim.getDtFormat()));
            sbPk.append("-");
            sbPk.append(fdFullDnis);

            req.setPkBulkSendPlan(sbPk.toString());
            req.setFkCompany(fkCompany);
            req.setFkStaffAi(fkCompanyStaffAi);
            req.setNumberFrom(fdFullDnis);
            req.setFullDnis(fdFullDnis);
            req.setDnis(fdDnis);
            req.setReserveYn("N");
            req.setReserveDt("00000000000000");
            req.setChannelType(EnumMessageType.SMS.getValue());
            req.setMsgTitle("junit test");
            req.setMsgBody("(광고) junit test\\n광고성 정보가 포함되어 있습니다");
            req.setAgreeAdYn("N");
            req.setFkStaff(fkCompanyStaffAi);
            req.setChkLevel("C1003");
            req.setSendStatus("INIT");

            List<DaoBulkSendMember> listMem = new ArrayList<>();

            DaoBulkSendMember mem = new DaoBulkSendMember();
            mem.setFkBulkSendPlan(req.getPkBulkSendPlan());
            mem.setNumberTo("01031238227");
//            mem.setFkCustomer();
            mem.setChannelType(req.getChannelType());
            mem.setSendStatus(req.getSendStatus());
            listMem.add(mem);

            req.setListMember(listMem);
            req.setCntUser(listMem.size());

            MvcResult mvcResultStart = mockMvc.perform(
                post("/v1/bulk/send/plan/create")
                    .content(req.toJson())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andReturn()
            ;

            String resultList = mvcResultStart.getResponse().getContentAsString();
            ResponseApi<DaoBulkSendPlan> resList = objectMapper.readValue(resultList,ResponseApi.class);
            log.info("resList:{}",resList.toJson());
        } catch (Exception e) {
            log.error("",e);
        }
    }

    @Test
    @Nested 
    @DisplayName("bulk send call conf create")
    void testCreateCall() {
        try {
            DaoBulkSendPlan req = new DaoBulkSendPlan();

            Long fkCompany = 1415L;
            Long fkCompanyStaffAi = 3989L;
            String fdDnis = "45000084";
            String fdFullDnis = "07045000084";
            UtilDateTime utilDateTime = new UtilDateTime();
            StringBuilder sbPk = new StringBuilder();
            sbPk.append("junit-OBC-");
            sbPk.append(utilDateTime.getCurrentDateTimeString(EnumDateTimeFormat.YmdHmsTrim.getDtFormat()));
            sbPk.append("-");
            sbPk.append(fdFullDnis);

            req.setPkBulkSendPlan(sbPk.toString());
            req.setFkCompany(fkCompany);
            req.setFkStaffAi(fkCompanyStaffAi);
            req.setNumberFrom(fdFullDnis);
            req.setFullDnis(fdFullDnis);
            req.setDnis(fdDnis);
            req.setReserveYn("N");
            req.setReserveDt("00000000000000");
            req.setChannelType(EnumMessageType.CALL_WITH_TALKBOT.getValue());
            req.setMsgTitle("junit test call");
            req.setMsgBody("안녕하세요 유닛테스트 통화 입니다");
            req.setAgreeAdYn("N");
            req.setFkStaff(fkCompanyStaffAi);
            req.setChkLevel("C1003");
            req.setSendStatus("INIT");

            List<DaoBulkSendMember> listMem = new ArrayList<>();

            DaoBulkSendMember mem = new DaoBulkSendMember();
            mem.setFkBulkSendPlan(req.getPkBulkSendPlan());
            mem.setNumberTo("01031238227");
//            mem.setFkCustomer();
            mem.setChannelType(req.getChannelType());
            mem.setSendStatus(req.getSendStatus());
            listMem.add(mem);

            DaoBulkSendMember mem2 = new DaoBulkSendMember();
            mem2.setFkBulkSendPlan(req.getPkBulkSendPlan());
            mem2.setNumberTo("01092827263");//01079991242
            mem2.setChannelType(req.getChannelType());
            mem2.setSendStatus(req.getSendStatus());
            listMem.add(mem2);

            req.setListMember(listMem);
            req.setCntUser(listMem.size());

            MvcResult mvcResultStart = mockMvc.perform(
                post("/v1/bulk/send/plan/create")
                    .content(req.toJson())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andReturn()
            ;

            String resultList = mvcResultStart.getResponse().getContentAsString();
            ResponseApi<DaoBulkSendPlan> resList = objectMapper.readValue(resultList,ResponseApi.class);
            log.info("resList:{}",resList.toJson());
        } catch (Exception e) {
            log.error("",e);
        }
    }

    @Test
    @Nested 
    @DisplayName("bulk send conf list")
    void testList() {
        try {
            Long fkCompany = 1415L;
            String sendDtFrom = "2023-08-21 00:00:00";
            String sendDtTo = "2023-08-31 00:00:00";
            String channelType = EnumMessageType.SMS.getValue();
            String sendStatus = "";
            Long fkStaff = 3989L;
            int page = 0;
            int size = 10;

            MvcResult mvcResultStart = mockMvc.perform(
                get("/v1/bulk/send/plan/list")
                    .param("page",Integer.toString(page))
                    .param("size",Integer.toString(size))
                    .param("fkCompany",Long.toString(fkCompany))
                    .param("sendDtFrom",sendDtFrom)
                    .param("sendDtTo",sendDtTo)
                    .param("channelType",channelType)
//                    .param("sendStatus",sendStatus)
                    .param("fkStaff",Long.toString(fkStaff))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andReturn()
            ;

            String resultList = mvcResultStart.getResponse().getContentAsString();
            ResponseApi<Page<DaoBulkSendPlan>> resList = objectMapper.readValue(resultList,ResponseApi.class);
            log.info("resList:{}",resList.toJson());
        } catch (Exception e) {
            log.error("",e);
        }
    }
}






















