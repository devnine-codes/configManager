package com.aice.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.aice.dao.bizMsg.BizMsgRequestData;
import com.aice.dao.bizMsg.BizmCtgrItem;
import com.aice.service.ServiceApiBizMsgAdmin;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@MockMvcEncoding
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
public class TestBizm {
    static {
      System.setProperty("app.home","./");
      System.setProperty("LOG_DIR","./logs");
      System.setProperty("spring.profiles.active","local");
    }

    @Autowired MockMvc mockMvc;
    @Autowired WebApplicationContext ctx;
    @Autowired ObjectMapper objectMapper;
    @Autowired ServiceApiBizMsgAdmin serviceApiBizMsgAdmin;
    String userId = "aice";
    String senderKey = "86400956e8c479e923077de7dbbdc068306feff4";

    @Test
    void testMain() {
//        this.t01();
//        this.testSenderToken();
//        this.testCategoryAll();
//        this.testSenderList();
//        this.testSenderCreate();
//        this.testTemplateList();
//        this.testTemplateCreate();
//        this.testTemplateUpdate();
//        this.testTemplateDelete();
//        this.testTemplateRequest();
    }

    void t01() {
        try {
            
        } catch (Exception e) {
            log.error("",e);
        }
    }

    void testSenderList() {
        try {
            this.userId = "tracker_partner";
            Map<String,Object> res01 = serviceApiBizMsgAdmin.senderList(userId);
            log.info("res01:{}",res01);
        } catch (Exception e) {
            log.error("",e);
        }
    }

    void testSenderToken() {
        try {
            String plusFriend = "@ai직원";
            String phoneNumber = "01027161479";
            phoneNumber = "01031238227";
            Map<String,Object> res01 = serviceApiBizMsgAdmin.senderToken(plusFriend,phoneNumber);
            log.info("res01:{}",res01);
        } catch (Exception e) {
            log.error("",e);
        }
    }

    void testSenderCreate() {
        try {
            this.userId = "tracker_partner";
            String plusFriend = "@ai직원";
            String phoneNumber = "01027161479";
            phoneNumber = "01031238227";
            BizMsgRequestData req = new BizMsgRequestData();
            req.setPlusFriend(plusFriend);
            req.setPhoneNumber(phoneNumber);
            req.setToken(phoneNumber);
            req.setCategoryCode("02100020001");
            Map<String,Object> res01 = serviceApiBizMsgAdmin.senderCreate(userId,plusFriend,phoneNumber,"0","02100020001");
            log.info("res:{}",res01);
        } catch (Exception e) {
            log.error("",e);
        }
    }

    @SuppressWarnings("unchecked")
    void testCategoryAll() {
        try {
            Map<String,Object> res02 = serviceApiBizMsgAdmin.senderCategoryAll();
            Map<String,Object> res02Data = (Map<String, Object>) res02.get("data");
            List<BizmCtgrItem> res02DataCtgr1 = (List<BizmCtgrItem>) res02Data.get("firstBusinessType");
            List<BizmCtgrItem> res02DataCtgr2 = (List<BizmCtgrItem>) res02Data.get("secondBusinessType");
            List<BizmCtgrItem> res02DataCtgr3 = (List<BizmCtgrItem>) res02Data.get("thirdBusinessType");
            log.info("res02:{}",res02DataCtgr1);
            log.info("res02:{}",res02DataCtgr2);
            log.info("res02:{}",res02DataCtgr3);
        } catch (Exception e) {
            log.error("",e);
        }
    }

    void testTemplateList() {
        try {
            this.userId = "tracker_partner";
            this.senderKey = "ba40a876479b8cae9b84005c0b9a1788c25953d7";
            Map<String,Object> res01 = serviceApiBizMsgAdmin.templateList(userId,senderKey);
            log.info("res:{}",res01);
            ObjectMapper mapper = new ObjectMapper();
            List<BizMsgRequestData> listItem = mapper.convertValue(res01.get("data"),new TypeReference<List<BizMsgRequestData>>() {});
//            List<BizMsgRequestData> listItem = (List<BizMsgRequestData>) res01.get("data");
            for(BizMsgRequestData item : listItem) {
                log.info("item:{}",item.toJsonTrim());
            }
        } catch (Exception e) {
            log.error("",e);
        }
    }

    void testTemplateCreate() {
        try {
            this.userId = "tracker_partner";
            this.senderKey = "ba40a876479b8cae9b84005c0b9a1788c25953d7";
            List<BizMsgRequestData> listReq = new ArrayList<BizMsgRequestData>();
            BizMsgRequestData req = new BizMsgRequestData();
            req.setSenderKey(this.senderKey);
            req.setSenderKeyType("S");
            req.setTemplateCode("t01");
            req.setTemplateName("테스트-템플릿-01");
            req.setTemplateMessageType("BA");
            req.setTemplateEmphasizeType("NONE");
            req.setTemplateContent("안녕하세요.\\n테스트 템플릿01");
            req.setCategoryCode("004001");
            req.setSecurityFlag("false");
            listReq.add(req);
            List<Map<String,Object>> res01 = serviceApiBizMsgAdmin.templateCreate(userId,listReq);
            log.info("res:{}",res01);
        } catch (Exception e) {
            log.error("",e);
        }
    }

    void testTemplateUpdate() {
        try {
            this.userId = "tracker_partner";
            this.senderKey = "ba40a876479b8cae9b84005c0b9a1788c25953d7";
            BizMsgRequestData req = new BizMsgRequestData();
            req.setSenderKey(this.senderKey);
            req.setSenderKeyType("S");
            req.setTemplateCode("t01");
            req.setTemplateName("테스트-템플릿-01");
            req.setTemplateMessageType("BA");
            req.setTemplateEmphasizeType("NONE");
            req.setTemplateContent("안녕하세요.\\n테스트 템플릿01");
            req.setCategoryCode("004001");
            req.setSecurityFlag("false");

            req.setNewSenderKey(this.senderKey);
            req.setNewTemplateCode("t01");
            req.setNewTemplateName("테스트-템플릿-01");
            req.setNewTemplateMessageType("BA");
            req.setNewTemplateEmphasizeType("NONE");
            req.setNewTemplateContent("안녕하세요.\\n테스트 템플릿01");
            Map<String,Object> res01 = serviceApiBizMsgAdmin.templateUpdate(userId,req);
            log.info("res:{}",res01);
        } catch (Exception e) {
            log.error("",e);
        }
    }

    void testTemplateDelete() {
        try {
            this.userId = "tracker_partner";
            this.senderKey = "ba40a876479b8cae9b84005c0b9a1788c25953d7";
            List<BizMsgRequestData> listReq = new ArrayList<BizMsgRequestData>();
            BizMsgRequestData req = new BizMsgRequestData();
            req.setSenderKey(this.senderKey);
            req.setSenderKeyType("S");
            req.setTemplateCode("t01");
            listReq.add(req);

            List<Map<String,Object>> res01 = serviceApiBizMsgAdmin.templateDelete(userId,listReq);
            log.info("res:{}",res01);
        } catch (Exception e) {
            log.error("",e);
        }
    }

    void testTemplateRequest() {
        try {
            this.userId = "tracker_partner";
            this.senderKey = "ba40a876479b8cae9b84005c0b9a1788c25953d7";
            List<BizMsgRequestData> listReq = new ArrayList<BizMsgRequestData>();
            BizMsgRequestData req = new BizMsgRequestData();
            req.setSenderKey(this.senderKey);
            req.setSenderKeyType("S");
            req.setTemplateCode("t01");
            listReq.add(req);

            List<Map<String,Object>> res01 = serviceApiBizMsgAdmin.templateRequest(userId,listReq);
            log.info("res:{}",res01);
        } catch (Exception e) {
            log.error("",e);
        }
    }
}






















