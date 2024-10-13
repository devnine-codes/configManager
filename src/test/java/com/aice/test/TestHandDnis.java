package com.aice.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.aice.dao.ResponseApi;
import com.aice.dao.dnis.DaoCompanyDnis;
import com.aice.repo.RepoCompany;
import com.aice.repo.RepoCompanyDnis;
import com.aice.service.ExtensionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@MockMvcEncoding
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
public class TestHandDnis {
	static {
		System.setProperty("app.home","./");
		System.setProperty("LOG_DIR","./logs");
		System.setProperty("spring.profiles.active","local");
	  }
  
	  @Autowired MockMvc mockMvc;
	  @Autowired WebApplicationContext ctx;
	  @Autowired ObjectMapper objectMapper;
  
	  @Autowired RepoCompanyDnis repoCompanyDnis;
	  @Autowired RepoCompany repoCompany;
	  @Autowired
      ExtensionService extensionService;

	@Test
    @DisplayName("hand dnis conf create")
    void testCreate() {
        try {
            DaoCompanyDnis req = new DaoCompanyDnis();

            Long fkCompany = 1092L;
            Long fkCompanyStaff = 3297L;
            Long fkCompanyStaffAi = 3298L;

            String fdDnis = "01092827263";
            String fdFullDnis = "07045000000";

            req.setUserType("B2002");
            req.setFkCompanyStaffAi(fkCompanyStaffAi);
            req.setSolutionType("B15");    
            req.setFkCompany(fkCompany);
            req.setFkCompanyStaff(fkCompanyStaff);
            req.setFullDnis(fdFullDnis);
            req.setDefaultYn("Y");
            req.setVgwId("5");
            req.setFdDnis(fdDnis);
            req.setInterjYn("Y");
            req.setInboundYn("Y");
            req.setOutboundYn("Y");
            req.setFdBoundIo("I");
            req.setFdUseYn("Y");
            req.setBizPhoneNum(null);

            MvcResult mvcResultStart = mockMvc.perform(
                post("/dnis/hand/save") //공유번호(numberType: shared) 생성
                    .content(req.toJson())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andReturn()
            ;
          
            String resultList = mvcResultStart.getResponse().getContentAsString();
            ResponseApi<DaoCompanyDnis> resList = objectMapper.readValue(resultList,ResponseApi.class);
            log.info("resList:{}",resList.toJson());
        } catch (Exception e) {
            log.error("",e);
        }
    }

	@Test
    @Nested
    @DisplayName("dnis conf update")
    void testUpdate() {
        try {
            DaoCompanyDnis req = new DaoCompanyDnis();

            String vgwId = "5";
            String fdDnis = "01012345678";
            String fullDnis = "07045000000";
            Long pkCompanyDnis = 341L;
            Long fkCompany = 1092L;
            Long fkCompanyStaffAi = 3298L;

            req.setVgwId(vgwId);
            req.setFkCompany(fkCompany);
            req.setFdDnis(fdDnis);
            req.setPkCompanyDnis(pkCompanyDnis);
            req.setFullDnis(fullDnis);
            req.setFkCompanyStaffAi(fkCompanyStaffAi);

            MvcResult mvcResultStart = mockMvc.perform(
                put("/dnis/hand/update") //공유번호(numberType: shared) 수정
                    .content(req.toJson())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andReturn()
            ;
          
            String resultList = mvcResultStart.getResponse().getContentAsString();
            ResponseApi<DaoCompanyDnis> resList = objectMapper.readValue(resultList,ResponseApi.class);
            log.info("resList:{}",resList.toJson());
        } catch (Exception e) {
            log.error("",e);
        }
    }

	@Test
    @Nested
    @DisplayName("dnis conf delete")
    void testDelete() {
        try {
            DaoCompanyDnis req = new DaoCompanyDnis();

            Long fkCompany = 1092L;
            Long fkCompanyStaffAi = 3298L;
            String fdDnis = "01092827263";
            String fullDnis = "07045000000";

            req.setFkCompany(fkCompany);
            req.setFkCompanyStaffAi(fkCompanyStaffAi);
            req.setFullDnis(fullDnis);

            MvcResult mvcResultStart = mockMvc.perform(
                delete("/dnis/hand/" + fdDnis) //공유번호(numberType: shared) 삭제
                    .content(req.toJson())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andReturn()
            ;
          
            String resultList = mvcResultStart.getResponse().getContentAsString();
            ResponseApi<DaoCompanyDnis> resList = objectMapper.readValue(resultList,ResponseApi.class);
            log.info("resList:{}",resList.toJson());
        } catch (Exception e) {
            log.error("",e);
        }
    }

}
