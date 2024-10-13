package com.aice.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.aice.dao.ResponseApi;
import com.aice.dao.businesstime.DaoDayOff;
import com.aice.dao.dnis.DaoCompanyDnis;
import com.aice.dao.egov.ResEgovMain;
import com.aice.repo.RepoDayOff;
import com.aice.service.ServiceApiDataGoKr;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@MockMvcEncoding
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
public class TestDayOff {
	static {
		System.setProperty("app.home","./");
		System.setProperty("LOG_DIR","./logs");
		System.setProperty("spring.profiles.active","local");
	}

	@Autowired MockMvc mockMvc;
    @Autowired WebApplicationContext ctx;
    @Autowired ObjectMapper objectMapper;

	@Autowired RepoDayOff repoDayOff;

	/**
	 * 공공데이터 관련
	 */
	@Autowired ServiceApiDataGoKr serviceApiDataGoKr;
    @Value("${data-go-kr.service-key}")
    String serviceKey;

	@Test
    @DisplayName("data.go.kr get holiday info")
    ResponseEntity<?> testDataGoKr() {
		ResponseEntity<?> res = ResponseEntity.ok(null);
        try {
			LocalDate currentDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
			
            String pageNo = "0";
            String numOfRows = "100";
            String solYear = currentDate.format(formatter);
            String solMonth = "";
            ResponseEntity<String> resMain = serviceApiDataGoKr.getHoliDeInfo(
                serviceKey
                ,URLEncoder.encode(pageNo,"UTF-8")
                ,URLEncoder.encode(numOfRows,"UTF-8")
                ,URLEncoder.encode(solYear,"UTF-8")
                ,URLEncoder.encode(solMonth,"UTF-8")
            );
            String resBody = resMain.getBody();

            ResEgovMain resEgovMain = objectMapper.readValue(resBody,new TypeReference<ResEgovMain>() {});
            log.info("resBody:{}",resEgovMain.toJson());
			res = ResponseEntity.ok(ResponseApi.success("## testDataGoKr success", resBody));
        }catch(Exception e1) {
			res = ResponseEntity.ok(ResponseApi.error("## testDataGoKr Error : {}", e1.getMessage()));
            log.error("",e1);
        }
		return res;
    }

	/*
	 * 공휴일 생성
	 */
	@Test
	void testAddPublicHoliday() {
		try {
			MvcResult mvcResultStart = mockMvc.perform(
                post("/v1/businesstime/public/holidays")
            )
            .andExpect(status().isOk())
            .andReturn()
            ;

			String resultList = mvcResultStart.getResponse().getContentAsString();
            ResponseApi<DaoCompanyDnis> resList = objectMapper.readValue(resultList,ResponseApi.class);
            log.info("resList:{}",resList.toJson());
		} catch (Exception e) {
			// TODO: handle exception
			log.error("## testEgovCreate Error : {}", e.getMessage());
		}
	}

	/*
	 * 공휴일 조회
	 */
	@Test
	void testEgovSelect() {
		try {
			mockMvc.perform(
			get("/v1/businesstime/public/holidays/202309")
			)
			.andExpect(status().isOk())
			.andReturn()
			;
		} catch (Exception e) {
			log.error("## testEgovSelect Error : {}", e);
		}
	}

	/*
	 * 공휴일 수정
	 */
	@Test
	void testEgovUpdate() {
		try {
			DaoDayOff req = new DaoDayOff();

			req.setDayOffFrom("20230103");
			req.setDayOffTo("20230104");
			req.setDispName("UPDATE TEST2");
			req.setMsgIntro("MsgIntro");
			req.setMsgClose("MsgClose");
			req.setUseYn("Y");
			req.setFkWriter(0L);
			req.setFkModifier(0L);
			req.setFkCompany(1L);

			MvcResult mvcResultStart = mockMvc.perform(
                put("/v1/businesstime/public/holidays/20230622") // 공휴일 수정
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
			log.error("## testEgovUpdate Error : {}", e);
		}
	}

	/*
	 * 공휴일 삭제 
	 */
	@Test
	void testEgovDelete() {
		try {
			mockMvc.perform(
				delete("/v1/businesstime/public/holidays/20230606") // 공휴일 삭제
			)
			.andExpect(status().isOk())
			.andReturn()
			;
		} catch (Exception e) {
			log.error("## testEgovDelete Error : {}", e);
		}
	}

	
	@Test
    @DisplayName("dayOff conf testGetDayOffById")
	void testGetDayOffById() {
		try {
			Long fkCompany = 1L;
			MvcResult mvcResultStart = mockMvc.perform(
                get("/v1/day/off/" + fkCompany + "/info?dtYy=202305")
            )
            .andExpect(status().isOk())
            .andReturn()
            ;
          
            String resultList = mvcResultStart.getResponse().getContentAsString();
            ResponseApi<DaoCompanyDnis> resList = objectMapper.readValue(resultList,ResponseApi.class);
            log.info("resList:{}",resList.toJson());
		} catch (Exception e) {
			log.error("## testDayOffUpdate Error : {}", e);
		}
	}

	/*
	 * 회사 휴일 생성 
	 */
	@Test
    @DisplayName("company conf create")
	void testCompanyCreate() {
		try {
			DaoDayOff req = new DaoDayOff();

			Long fkCompany = 1092L;
			Long fkCompanyStaffAi = 3298L;
			String dayOffFrom = "20230816";
			String dayOffTo = "20230817";
			String timeType = "REST_GENERAL";
			String dispName = "DISP";
			String msgIntro = "INTRO";
			String msgClose = "CLOSE";
			String useYn = "Y";
			Long fkWriter = 0L;

			req.setFkCompany(fkCompany);
			req.setFkCompanyStaffAi(fkCompanyStaffAi);
			req.setDayOffFrom(dayOffFrom);
			req.setDayOffTo(dayOffTo);
			req.setTimeType(timeType);
			req.setDispName(dispName);
			req.setMsgIntro(msgIntro);
			req.setMsgClose(msgClose);
			req.setUseYn(useYn);
			req.setFkWriter(fkWriter);

			MvcResult mvcResultStart = mockMvc.perform(
                post("/v1/day/off/company/list")
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
			// TODO: handle exception
			log.error("## testDayOffCreate Error : {}", e.getMessage());
		}
	}

	/*
	 * 회사 휴일 조회
	 */
	@Test
    @DisplayName("company conf select")
	void testCompanySelect() {
		try {
			mockMvc.perform(
			get("/v1/day/off/company/list?fkCompany=1092&dtYy=20230816") 
			)
			.andExpect(status().isOk())
			.andReturn()
			;
		} catch (Exception e) {
			log.error("## testCompanySelect Error : {}", e);
		}
	}

	/*
	 * 회사 휴일 수정
	 */
	@Test
    @DisplayName("company conf update")
	void testCompanyUpdate() {
		try {
			DaoDayOff req = new DaoDayOff();
			Long pkAiConfDayOff = 402L;
			
			req.setDayOffFrom("20230820");
			req.setDayOffTo("20230821");
			req.setDispName("DayOff Update");
			req.setMsgIntro("MsgIntro");
			req.setMsgClose("MsgClose");
			req.setUseYn("Y");
			req.setFkWriter(0L);
			req.setFkModifier(0L);
			req.setFkCompany(1092L);

			MvcResult mvcResultStart = mockMvc.perform(
				put("/v1/day/off/company/" + pkAiConfDayOff) // 회사 휴일 수정
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
			log.error("## testCompanyUpdate Error : {}", e);
		}
	}

	/*
	 * 공휴일 삭제 
	 */
	@Test
    @DisplayName("company conf delete")
	void testCompanyDelete() {
		try {
			mockMvc.perform(
			delete("/v1/day/off/company/402") // 회사 휴일 삭제
			)
			.andExpect(status().isOk())
			.andReturn()
			;
		} catch (Exception e) {
			log.error("## testCompanyDelete Error : {}", e);
		}
	}
}
