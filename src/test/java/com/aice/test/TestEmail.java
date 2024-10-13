package com.aice.test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.aice.dao.DaoConfigManager;
import com.aice.dao.ResponseApi;
import com.aice.dao.email.DaoEmail;
import com.aice.enums.EmailUseType;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings({"unused","unchecked"})
@Slf4j
@MockMvcEncoding
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
public class TestEmail {
    static {
        System.setProperty("app.home","./");
        System.setProperty("LOG_DIR","./logs");
        System.setProperty("spring.profiles.active","local");
    }

    @Autowired MockMvc mockMvc;
    @Autowired WebApplicationContext ctx;
    @Autowired ObjectMapper objectMapper;

//    @BeforeEach
//    void beforeSetup() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
//              .alwaysDo(print())
//              .build();
//    }

    @Test
    void testMain() {
//        this.testCreate();
        this.testList();
    }

    @Nested
    @DisplayName("email conf create")
    void testCreate() {
        try {
            Long fkCompany = 52L;
            Long fkCompanyStaffAi = 9999L;
            String useType = EmailUseType.SMTP.getValue();
            DaoEmail req = new DaoEmail();
            req.setFkCompany(fkCompany);
            req.setFkCompanyStaffAi(fkCompanyStaffAi);
            req.setUseType(useType);
            req.setConfName("test conf smtp");
            req.setConfHost("outlook.office365.com");
            req.setConfPort(587);
            req.setConfId("aice@saltlux.com");
            req.setConfPw("MsCy9jC4QyceCNS");
            req.setUseAuthYn("Y");
            req.setUseTlsYn("Y");
            req.setUseSslYn("Y");
            req.setUseYn("Y");

            MvcResult mvcResultStart = mockMvc.perform(
                post("/email/create")
                    .content(req.toJson())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].useType",useType).exists())
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
    @DisplayName("email conf list")
    void testList() {
        try {
            Long fkCompany = 1L;
            Long fkCompanyStaffAi = null;
            String useType = EmailUseType.SMTP.getValue();

            MvcResult mvcResultStart = mockMvc.perform(
                get("/email/list")
                    .param("fkCompany",Long.toString(fkCompany))
//                    .param("fkCompanyStaffAi",Long.toString(fkCompanyStaffAi))
                    .param("useType",useType)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status","success").exists())
            .andExpect(jsonPath("$.data",notNullValue()))
            .andExpect(jsonPath("$.data[0].useType",useType).exists())
//            .andExpect(jsonPath("$.body.greetings",notNullValue()))
//            .andExpect(jsonPath("$.body.sttServer",notNullValue()))
//            .andExpect(jsonPath("$.body.ttsServer",notNullValue()))
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




















