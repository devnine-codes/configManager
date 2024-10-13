package com.aice.test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.aice.dao.DaoConfigManager;
import com.aice.dao.DaoFileLog;
import com.aice.dao.ResponseApi;
import com.aice.dao.bizphone.DaoBizPhone;
import com.aice.enums.EnumMetaType1;
import com.aice.enums.EnumMetaType2;
import com.aice.enums.EnumMetaType3;
import com.aice.enums.EnumSolutionType;
import com.aice.enums.EnumUserType;
import com.aice.service.ServiceApiFileGw;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings({"unused","unchecked"})
@Slf4j
@MockMvcEncoding
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
public class TestDispPhone {
    static {
        System.setProperty("app.home","./");
        System.setProperty("LOG_DIR","./logs");
        System.setProperty("spring.profiles.active","local");
    }

    @Autowired MockMvc mockMvc;
    @Autowired WebApplicationContext ctx;
    @Autowired ObjectMapper objectMapper;
    @Autowired ServiceApiFileGw serviceApiFileGw;

//    @BeforeEach
//    void beforeSetup() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
//              .alwaysDo(print())
//              .build();
//    }

    @Test
    void testMain() {
//        this.testCreate();
//        this.testUploadDirect();
        this.testUpload();
//        this.testList();
    }

    @Nested
    @DisplayName("disp phone conf create")
    void testCreate() {
        try {
            Long fkCompany = 248L;
            String bizPhoneNum = "1";

            DaoBizPhone req = new DaoBizPhone();
            req.setFkCompany(fkCompany);
            req.setSolutionType(EnumSolutionType.WorkCenter.getValue());
            req.setUserType(EnumUserType.COMPANY.getValue());
            req.setBizPhoneName("junit test");
            req.setBizPhoneNum(bizPhoneNum);
            req.setDnis(null);
            req.setRegStatus("INIT");
            req.setDocStatus(null);
            req.setDocMainStatus(null);
            req.setCallForwardStatus(null);
            req.setDefaultYn("N");
            req.setInboundYn("Y");
            req.setOutboundYn("Y");
            req.setPhoneYn("Y");
            req.setSmsYn("Y");
            req.setEnableYn("Y");
            req.setUseYn("Y");
            req.setUseOrder(1);
            req.setFilePathMainNum(null);
            req.setFilePathJoinCert(null);
            req.setFilePathCompanyCert(null);
            req.setFilePathAttorney(null);
            req.setFilePathTradeCert(null);
            req.setFilePathEmployCert(null);
            req.setBizCateStep1("cate01");
            req.setBizCateStep2("cate02");
            req.setBizCateStep3("cate03");

            MvcResult mvcResultStart = mockMvc.perform(
                post("/disp/phone/number/create")
                    .content(req.toJson())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.bizPhoneNum",bizPhoneNum).exists())
            .andReturn()
            ;

            String resultList = mvcResultStart.getResponse().getContentAsString();
            ResponseApi<DaoConfigManager> resList = objectMapper.readValue(resultList,ResponseApi.class);
            log.info("resList:{}",resList.toJson());
        }catch(Exception e) {
            log.error("",e);
        }
    }

    void testUploadDirect() {
        try {
            Long fkCompany = 248L;
            String bizPhoneNum = "1";
            DaoFileLog daoFileLog = new DaoFileLog();
            daoFileLog.setSolutionType(EnumSolutionType.WorkCenter.getValue());
            daoFileLog.setUserType(EnumUserType.COMPANY.getValue());
            daoFileLog.setFkCompany(Long.toString(fkCompany));
            daoFileLog.setMeta1(EnumMetaType1.Receptionist.getValue());
            daoFileLog.setMeta2(EnumMetaType2.CTGR2021.getValue());
            daoFileLog.setMeta3(EnumMetaType3.META_DOC.getValue());
            daoFileLog.setMeta4("main_num");

            String fileName = "img.png";
            Path pathFile = Path.of("./",fileName);
            File fileObj = pathFile.toFile();
            FileInputStream fisUpload = new FileInputStream(fileObj);
            MultipartFile mockFile = new MockMultipartFile(
                "pathFile"
                ,fileObj.getName()
                ,MediaType.IMAGE_PNG.getType()
                ,IOUtils.toByteArray(fisUpload)
            );
            serviceApiFileGw.uploadAttachFile(daoFileLog,mockFile);
        }catch(Exception e1) {
            log.error("",e1);
        }
    }

    @Nested
    @DisplayName("disp phone conf upload")
    void testUpload() {
        try {
            Long fkCompany = 248L;
            String bizPhoneNum = "1";

            String fileName = "img.png";
            Path pathFile = Path.of("./",fileName);
            FileInputStream fisUpload = new FileInputStream(pathFile.toFile());
            MockMultipartFile mockFile = new MockMultipartFile(
                "file"
                ,fileName
                ,MediaType.IMAGE_PNG.getType()
                ,IOUtils.toByteArray(fisUpload)
            );

            MvcResult mvcResultStart = mockMvc.perform(
                multipart("/disp/phone/number/docs/upload")
                    .file(mockFile)
                    .param("fkCompany",Long.toString(fkCompany))
                    .param("bizPhoneNum",bizPhoneNum)
                    .param("metaType2",EnumMetaType2.CTGR2021.getValue())
                    .param("metaType4","main_num")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
//                    .accept(MediaType.APPLICATION_JSON)
            )
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.data.bizPhoneNum",bizPhoneNum).exists())
            .andReturn()
            ;

            String resultList = mvcResultStart.getResponse().getContentAsString();
            ResponseApi<DaoConfigManager> resList = objectMapper.readValue(resultList,ResponseApi.class);
            log.info("resList:{}",resList.toJson());
        }catch(Exception e) {
            log.error("",e);
        }
    }

    @Nested
    @DisplayName("disp phone conf list")
    void testList() {
        try {
            Long fkCompany = 70L;
            String bizPhoneNum = "0221931600";
            String dnis = null;
            String inboundYn = null;
            String outboundYn = null;
            String phoneYn = null;
            String smsYn = null;
            String enableYn = null;

            MvcResult mvcResultStart = mockMvc.perform(
                get("/disp/phone/number/list")
                    .param("fkCompany",Long.toString(fkCompany))
                    .param("bizPhoneNum",bizPhoneNum)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status","success").exists())
            .andExpect(jsonPath("$.data",notNullValue()))
            .andExpect(jsonPath("$.data[0].bizPhoneNum",bizPhoneNum).exists())
            .andReturn()
            ;
            String resultList = mvcResultStart.getResponse().getContentAsString();
            ResponseApi<List<DaoConfigManager>> resList = objectMapper.readValue(resultList,ResponseApi.class);
            log.info("resList:{}",resList.toJson());
        }catch(Exception e) {
            log.error("",e);
        }
    }
}




















