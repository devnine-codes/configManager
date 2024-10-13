package com.aice.test;

import com.aice.dao.ment.DaoMent;
import com.aice.repo.RepoMent;
import com.aice.service.MentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@MockMvcEncoding
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
public class TestMent {
    static {
        System.setProperty("app.home","./");
        System.setProperty("LOG_DIR","./logs");
        System.setProperty("spring.profiles.active","local");
    }

    @Autowired
    RepoMent repoMent;

    @Autowired
    MentService mentService;

    @Test
    void testAdd() {
        DaoMent daoMent = new DaoMent();
        daoMent.setMsgBody("인사말1");
        daoMent.setMsgBody2("인사말2");
        daoMent.setMsgBody3("인사말3");
        daoMent.setMsgBody4("인사말4");
        daoMent.setMsgBody5("인사말5");
        daoMent.setMsgBody6("인사말6");
        daoMent.setMsgOff("종료인사말");
        daoMent.setDefaultYn("N");
        daoMent.setWarnYn("Y");
        daoMent.setUseYn("Y");
        daoMent.setFkWriter(1092L);

        mentService.addMessage("companies", 1092L, daoMent);
    }
}
