package com.aice.test;

import java.net.URLEncoder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.aice.dao.egov.ResEgovMain;
import com.aice.service.ServiceApiDataGoKr;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@MockMvcEncoding
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
public class TestMain {
    static {
        System.setProperty("app.home","./");
        System.setProperty("LOG_DIR","./logs");
        System.setProperty("spring.profiles.active","local");
    }
    @Autowired ServiceApiDataGoKr serviceApiDataGoKr;
    @Value("${data-go-kr.service-key}")
    String serviceKey;
    @Autowired ObjectMapper objectMapper;

    @Test
    void testMain() {
        this.testDataGoKr();
    }

    @Nested
    @DisplayName("data.go.kr get holiday info")
    void testDataGoKr() {
        try {
            String pageNo = "0";
            String numOfRows = "10";
            String solYear = "2023";
            String solMonth = "";
            ResponseEntity<String> resMain = serviceApiDataGoKr.getHoliDeInfo(
                serviceKey
                ,URLEncoder.encode(pageNo,"UTF-8")
                ,URLEncoder.encode(numOfRows,"UTF-8")
                ,URLEncoder.encode(solYear,"UTF-8")
                ,URLEncoder.encode(solMonth,"UTF-8")
            );
            String resBody = resMain.getBody();
//            log.info("resBody:{}",resBody);
            ResEgovMain resEgovMain = objectMapper.readValue(resBody,new TypeReference<ResEgovMain>() {});
            log.info("resBody:{}",resEgovMain.toJson());
        }catch(Exception e1) {
            log.error("",e1);
        }
    }
}




















