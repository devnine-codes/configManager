package com.aice.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.aice.dao.DaoCompanyStaff;
import com.aice.repo.RepoCompanyStaff;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.aice.dao.ResponseApi;
import com.aice.dao.businesstime.DaoDayOn;
import com.aice.dao.dnis.DaoCompanyDnis;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@MockMvcEncoding
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
public class TestDayOn {
	static {
		System.setProperty("app.home","./");
		System.setProperty("LOG_DIR","./logs");
		System.setProperty("spring.profiles.active","local");
	}

	@Autowired MockMvc mockMvc;
    @Autowired WebApplicationContext ctx;
    @Autowired ObjectMapper objectMapper;
	@Autowired
	RepoCompanyStaff repoCompanyStaff;

	/*
	 * 회사 업무시간 생성
	 */
	@Test
	void testAddCompanyWorkhour() {
		try {
			DaoDayOn req = new DaoDayOn();

			Long fkCompany = 1092L;
			Long fkCompanyStaffAi = 3298L;
			Integer weekNum = 4;
			Integer timeFromHh = 9;
			Integer timeFromMin = 0;
			Integer timeToHh = 18;
			Integer timeToMin = 0;
			String workType = "W";
			String timeType = "REST_DINNER";
			String msgIntro = "INTRO";
			String msgClose = "CLOSE";
			String enableYn = "Y";
			String useYn = "Y";
			Long fkWriter = 1092L;

			//req.setFkCompany(fkCompany);
			//req.setFkCompanyStaffAi(fkCompanyStaffAi);
			//req.setWeekNum(weekNum);
			req.setTimeFromHh(timeFromHh);
			req.setTimeFromMin(timeFromMin);
			req.setTimeToHh(timeToHh);
			req.setTimeToMin(timeToMin);
			//req.setWorkType(workType);
			//req.setTimeType(timeType);
			req.setMsgIntro(msgIntro);
			req.setMsgClose(msgClose);
			req.setEnableYn(enableYn);
			req.setUseYn(useYn);
			req.setFkWriter(fkWriter);

			MvcResult mvcResultStart = mockMvc.perform(
                post("/v1/businesstime/companies/" + fkCompany + "/week/" + weekNum + "/workhours")
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
			log.error("## testCreateDayOn Error : {}", e.getMessage());
		}
	}

	/*
	 * 회사 업무시간 조회
	 */
	@Test
	void testGetCompanyWorkhourList() {
		try {
			Long fkCompany = 1092L;
			Integer weekNum = 3;
			mockMvc.perform(
				get("/v1/businesstime/companies/" + fkCompany + "/week/" + weekNum + "/workhours")
			)
			.andExpect(status().isOk())
			.andReturn()
			;
		} catch (Exception e) {
			log.error("## testSelectDayOn Error : {}", e);
		}
	}

	/*
	 * 회사 업무시간 수정
	 */
	@Test
	void testSetCompanyWorkhour() {
		try {
			DaoDayOn req = new DaoDayOn();

			Long fkCompany = 1092L;
			Long fkCompanyStaffAi = 3298L;
			Integer weekNum = 4;
			Integer timeFromHh = 10;
			Integer timeFromMin = 0;
			Integer timeToHh = 18;
			Integer timeToMin = 0;
			String workType = "W";
			String timeType = "WORK_ON";
			String msgIntro = "INTRO";
			String msgClose = "CLOSE";
			String enableYn = "Y";
			String useYn = "Y";
			Long fkWriter = 1092L;

			//req.setFkCompany(fkCompany);
			//req.setFkCompanyStaffAi(fkCompanyStaffAi);
			//req.setWeekNum(weekNum);
			req.setTimeFromHh(timeFromHh);
			req.setTimeFromMin(timeFromMin);
			req.setTimeToHh(timeToHh);
			req.setTimeToMin(timeToMin);
			//req.setWorkType(workType);
			//req.setTimeType(timeType);
			req.setMsgIntro(msgIntro);
			req.setMsgClose(msgClose);
			req.setEnableYn(enableYn);
			req.setUseYn(useYn);
			//req.setFkWriter(fkWriter);
			req.setFkModifier(1092L);

			MvcResult mvcResultStart = mockMvc.perform(
				put("/v1/businesstime/companies/" + fkCompany + "/week/" + weekNum + "/workhours") // 회사 휴일 수정
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
			log.error("## testUpdateDayOn Error : {}", e);
		}
	}

	/*
	 * 회사 업무시간 삭제
	 */
	@Test
	void testRemoveCompanyWorkhour() {
		try {
			DaoDayOn req = new DaoDayOn();
			Long fkCompany = 1092L;
			Integer weekNum = 3;

			mockMvc.perform(
				delete("/v1/businesstime/companies/"+ fkCompany + "/week/" + weekNum + "/workhours")
						.content(req.toJson())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andReturn()
			;
		} catch (Exception e) {
			log.error("## testDeleteDayOn Error : {}", e);
		}
	}


	/*
	 * 회사 식사시간 생성
	 */
	@Test
	void testAddCompanyMealtime() {
		try {
			DaoDayOn req = new DaoDayOn();

			String mealType = "lunch";

			Long fkCompany = 1092L;
			Long fkCompanyStaffAi = 3298L;
			Integer weekNum = 4;
			Integer timeFromHh = 9;
			Integer timeFromMin = 0;
			Integer timeToHh = 18;
			Integer timeToMin = 0;
			String workType = "W";
			String timeType = "REST_LUNCH";
			String msgIntro = "INTRO";
			String msgClose = "CLOSE";
			String enableYn = "Y";
			String useYn = "Y";
			Long fkWriter = 1092L;

			//req.setFkCompany(fkCompany);
			//req.setFkCompanyStaffAi(fkCompanyStaffAi);
			//req.setWeekNum(weekNum);
			req.setTimeFromHh(timeFromHh);
			req.setTimeFromMin(timeFromMin);
			req.setTimeToHh(timeToHh);
			req.setTimeToMin(timeToMin);
			//req.setWorkType(workType);
			//req.setTimeType(timeType);
			req.setMsgIntro(msgIntro);
			req.setMsgClose(msgClose);
			req.setEnableYn(enableYn);
			req.setUseYn(useYn);
			req.setFkWriter(fkWriter);

			MvcResult mvcResultStart = mockMvc.perform(
							post("/v1/businesstime/companies/" + fkCompany + "/week/" + weekNum + "/mealtimes/" + mealType)
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
			log.error("## testCreateDayOn Error : {}", e.getMessage());
		}
	}

	/*
	 * 회사 식사시간 조회
	 */
	@Test
	void testGetCompanyByMealtime() {
		try {
			Long fkCompany = 1092L;
			Integer weekNum = 3;
			String mealType = "lunch";
			mockMvc.perform(
							get("/v1/businesstime/companies/" + fkCompany + "/week/" + weekNum + "/mealtimes")
									.param("timeType", "dinner")
					)
					.andExpect(status().isOk())
					.andReturn()
			;
		} catch (Exception e) {
			log.error("## testSelectDayOn Error : {}", e);
		}
	}

	/*
	 * 회사 식사시간 수정
	 */
	@Test
	void testSetCompanyMealtime() {
		try {
			DaoDayOn req = new DaoDayOn();
			
			String mealType = "lunch";

			Long fkCompany = 1092L;
			Long fkCompanyStaffAi = 3298L;
			Integer weekNum = 4;
			Integer timeFromHh = 10;
			Integer timeFromMin = 0;
			Integer timeToHh = 18;
			Integer timeToMin = 0;
			String workType = "W";
			String timeType = "WORK_ON";
			String msgIntro = "INTRO";
			String msgClose = "CLOSE";
			String enableYn = "Y";
			String useYn = "Y";
			Long fkWriter = 1092L;

			//req.setFkCompany(fkCompany);
			//req.setFkCompanyStaffAi(fkCompanyStaffAi);
			//req.setWeekNum(weekNum);
			req.setTimeFromHh(timeFromHh);
			req.setTimeFromMin(timeFromMin);
			req.setTimeToHh(timeToHh);
			req.setTimeToMin(timeToMin);
			//req.setWorkType(workType);
			//req.setTimeType(timeType);
			req.setMsgIntro(msgIntro);
			req.setMsgClose(msgClose);
			req.setEnableYn(enableYn);
			req.setUseYn(useYn);
			//req.setFkWriter(fkWriter);
			req.setFkModifier(1092L);

			MvcResult mvcResultStart = mockMvc.perform(
							put("/v1/businesstime/companies/" + fkCompany + "/week/" + weekNum + "/mealtimes/" + mealType) // 회사 휴일 수정
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
			log.error("## testUpdateDayOn Error : {}", e);
		}
	}

	/*
	 * 회사 식사시간 삭제
	 */
	@Test
	void testRemoveCompanyMealtime() {
		try {
			DaoDayOn req = new DaoDayOn();
			String mealType = "lunch";
			Long fkCompany = 1092L;
			Integer weekNum = 4;

			mockMvc.perform(
							delete("/v1/businesstime/companies/"+ fkCompany + "/week/" + weekNum + "/mealtimes/" + mealType)
									.content(req.toJson())
									.contentType(MediaType.APPLICATION_JSON)
									.accept(MediaType.APPLICATION_JSON)
					)
					.andExpect(status().isOk())
					.andReturn()
			;
		} catch (Exception e) {
			log.error("## testDeleteDayOn Error : {}", e);
		}
	}

	@Test
	void testStaff() {

		List<DaoCompanyStaff> resData = repoCompanyStaff.findByStatusCode();
		System.out.println("##resData : " + resData);
	}
}
