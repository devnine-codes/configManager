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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.aice.dao.ResponseApi;
import com.aice.dao.dnis.DaoCompanyDnis;
import com.aice.repo.RepoCompany;
import com.aice.repo.RepoCompanyDnis;
import com.aice.service.CompanyDnisService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@MockMvcEncoding
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
public class TestDins {
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
    @Autowired CompanyDnisService companyDnisService;

    @Test
    void testMain() {
        //testCreate();
    }

    @Test
    @Nested 
    @DisplayName("dnis conf create")
    void testCreate() {
        try {
            DaoCompanyDnis req = new DaoCompanyDnis();

            Long fkCompany = 1092L;
            Long fkCompanyStaff = 3297L;
            Long fkCompanyStaffAi = 3298L;

            String fdDnis = "45000085";
            String fdFullDnis = "07045000085";

            req.setUserType("B2002");
            req.setFkCompanyStaffAi(fkCompanyStaffAi);
            req.setSolutionType("B15");    
            req.setFkCompany(fkCompany);
            req.setFkCompanyStaff(fkCompanyStaff);
            req.setDnisType("shared");
            req.setFwdNum("01092827263");
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
                post("/dnis/save") //일반번호(numberType: normal) 생성
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

            String vgwId = "2";
            String fdDnis = "45000085";
            String fullDnis = "07045000085";
            String numberType = "normal";
            Long pkCompanyDnis = 317L;
            Long fkCompany = 1092L;
            Long fkCompanyStaffAi = 3298L;

            req.setVgwId(vgwId);
            req.setFkCompany(fkCompany);
            req.setFdDnis(fdDnis);
            req.setPkCompanyDnis(pkCompanyDnis);
            req.setFullDnis(fullDnis);
            req.setNumberType(numberType);
            req.setFkCompanyStaffAi(fkCompanyStaffAi);

            MvcResult mvcResultStart = mockMvc.perform(
                //post("/dnis/temp/regist") //임시번호(numberType: temp) 수정 
                put("/dnis/update") //일반번호(numberType: normal) 수정
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
            String fdDnis = "45000085";
            String fullDnis = "07045000085";

            req.setFkCompany(fkCompany);
            req.setFkCompanyStaffAi(fkCompanyStaffAi);
            req.setFullDnis(fullDnis);

            MvcResult mvcResultStart = mockMvc.perform(
                //delete("/dnis/temp/" + fdDnis) //임시번호(numberType: temp) 삭제
                delete("/dnis/delete/" + fdDnis) //일반번호(numberType: normal) 삭제
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
    @DisplayName("dnis conf findAll")
    void testFindAll() {
      ResponseEntity<?> res = ResponseEntity.ok(null);
        try{
            DaoCompanyDnis req = new DaoCompanyDnis();

            Long fkCompany = 1092L;
            String solutionType = "B15";
            String userType = "B2002";
            String fullDnis = "07045000085";
            String numberType = "normal";
            
            req.setFkCompany(fkCompany);
            //req.setSolutionType(solutionType);
            //req.setUserType(userType);


            req.setFullDnis(fullDnis);
            req.setNumberType(numberType);

            MvcResult mvcResultStart = mockMvc.perform(
                get("/dnis/list")
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

            //List<DaoCompanyDnis> dnisList = repoCompanyDnis.findAll(req);
            //res = ResponseEntity.ok(ResponseApi.success("",dnisList));
        }catch(Exception e1){
            log.error("findAll error",e1);
            res = ResponseEntity.ok(ResponseApi.error("dnis.conf.list.undefined",null));
        }
    }


    /**
     * @Deprecated
     */
    @Test
    @Nested
    @DisplayName("dnis conf findId")
    void testFindId() {
        try {
            DaoCompanyDnis req = new DaoCompanyDnis();
            Long pkCompanyDnis = 273L;

            req.setPkCompanyDnis(pkCompanyDnis);

            MvcResult mvcResultStart = mockMvc.perform(
                get("/dnis/find/" + pkCompanyDnis)
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
    @DisplayName("dnis conf findFkCompany")
    void testFindFkCompany() {
        try {
            DaoCompanyDnis req = new DaoCompanyDnis();
            Long fkCompany = 1092L;

            req.setFkCompany(fkCompany);

            MvcResult mvcResultStart = mockMvc.perform(
                get("/dnis/find/fkcompany/" + fkCompany)
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
    @DisplayName("dnis conf checkForDnis")
    void testCheckForDnis() {
        try {
            DaoCompanyDnis req = new DaoCompanyDnis();
            String fdDnis = "45000085";
            String fullDnis = "07045000085";

            req.setFdDnis(fdDnis);
            //req.setFullDnis(fullDnis);

            MvcResult mvcResultStart = mockMvc.perform(
                get("/dnis/check")
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
