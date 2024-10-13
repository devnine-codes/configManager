package com.aice.test;

import com.aice.dao.external.DaoExtApi;
import com.aice.dao.external.DaoExtApiMeta;
import com.aice.repo.RepoExternal;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@MockMvcEncoding
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
public class TestExternal {
    static {
        System.setProperty("app.home","./");
        System.setProperty("LOG_DIR","./logs");
        System.setProperty("spring.profiles.active","local");
    }

    @Autowired
    MockMvc mockMvc;
    @Autowired
    WebApplicationContext ctx;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    RepoExternal repoExternal;

    @Test
    void testAddExtApi() {
        DaoExtApi req = new DaoExtApi();
        req.setFkCompany(1092L);
        req.setApiType("REST_API");
        req.setDispName("TEST");
        req.setUseYn("Y");
        req.setFkWriter(1092L);

        repoExternal.insertExtApi(req);
    }

    @Test
    void testGetExtApi() {
        DaoExtApi req = new DaoExtApi();
        req.setFkCompany(1092L);
        req.setApiType("REST_API");

        repoExternal.findExtApi(req);
    }

    @Test
    void testSetExtApi() {
        DaoExtApi req = new DaoExtApi();
        req.setFkCompany(1092L);
        req.setApiType("REST_API");
        req.setUseYn("N");
        req.setDispName("SET TEST");
        req.setFkModifier(1092L);

        repoExternal.updateExtApi("TCP_SOCKET", req);
    }

    @Test
    void testDelExtApi() {
        DaoExtApi req = new DaoExtApi();
        req.setFkCompany(1092L);
        req.setApiType("REST_API");

        repoExternal.deleteExtApi(req);
    }

    @Test
    void testAddExtApiMeta() {
        DaoExtApi req = new DaoExtApi();
        req.setFkCompany(1092L);
        req.setApiType("REST_API");
        req.setFkConfExtApi(repoExternal.findExtApi(req).getPkConfExtApi());
        req.setMetaKey("XuV=Z3VOypZ4PeS23CF9");
        req.setMetaVal("XuV=Z3VOypZ4PeS23CF9");
        req.setUseYn("Y");
        req.setFkWriter(1092L);

        repoExternal.insertExtApiMeta(req);
    }

    @Test
    void testGetExtApiMeta() {
        DaoExtApi req = new DaoExtApi();
        req.setFkCompany(1092L);
        req.setApiType("REST_API");
        req.setFkConfExtApi(repoExternal.findExtApi(req).getPkConfExtApi());

        repoExternal.findExtApiMeta(req);
    }

    @Test
    void testSetExtApiMeta() {
        DaoExtApi req = new DaoExtApi();
        req.setFkCompany(1092L);
        req.setApiType("REST_API");
        req.setFkConfExtApi(repoExternal.findExtApi(req).getPkConfExtApi());
        req.setMetaKey("XuV=Z3VOypZ4PeS23CF9");
        req.setMetaVal("XuV=Z3VOypZ4PeS23CF9");
        req.setUseYn("Y");
        req.setFkModifier(1092L);

        repoExternal.updateExtApiMeta("XuV=Z3VOypZ4PeS23CF9", req);
    }

    @Test
    void testDelExtApiMeta() {
        DaoExtApi req = new DaoExtApi();
        req.setFkCompany(1092L);
        req.setApiType("REST_API");
        req.setFkConfExtApi(repoExternal.findExtApi(req).getPkConfExtApi());
        req.setMetaKey("XuV=Z3VOypZ4PeS23CF9");

        repoExternal.deleteExtApiMeta(req);
    }
}
