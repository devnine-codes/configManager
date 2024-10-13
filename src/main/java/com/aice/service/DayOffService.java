package com.aice.service;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.aice.enums.EnumEntityType;
import com.aice.repo.RepoCompany;
import com.aice.repo.RepoCompanyStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.aice.dao.ResponseApi;
import com.aice.dao.businesstime.DaoDayOff;
import com.aice.repo.RepoDayOff;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DayOffService {

	@Autowired ObjectMapper objectMapper;

	@Autowired RepoDayOff repoDayOff;

	@Autowired RepoCompanyStaff repoCompanyStaff;

	/**
	 * 공공데이터 관련
	 */
	@Autowired ServiceApiDataGoKr serviceApiDataGoKr;

    @Value("${data-go-kr.service-key}") String serviceKey;

	/**
	 * 마스터 공휴일 생성
	 * company가 1인 회사의 휴일정보를 마스터 공휴일로 사용한다.
	 * @param solYear
	 * @return
	 */
	public ResponseEntity<?> addMasterPublicHolidays(String solYear) {
		DaoDayOff req = new DaoDayOff();
		req.setSolYear(solYear);
		req.setFkCompany(1L);
		req.setTimeType("MASTER_PUBLIC");

		log.info("Adding public holidays: {}", req.toJsonTrim());
		try {
			ResponseEntity<?> holidayData = getHolidayData(req.getSolYear());

			Object body = holidayData.getBody();
			String json = objectMapper.writeValueAsString(body);
			String dataJsonString = objectMapper.readTree(json).path("data").asText();
			JsonNode dataNode = objectMapper.readTree(dataJsonString);

			JsonNode itemArray = dataNode
					.path("response")
					.path("body")
					.path("items")
					.path("item");

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

			for (JsonNode itemNode : itemArray) {
				String dayOffFrom = itemNode.path("locdate").asText();
				String dayOffTo = itemNode.path("locdate").asText();
				LocalDate dayOffToPlus = LocalDate.parse(dayOffTo, formatter).plusDays(1);
				String dispName = itemNode.path("dateName").asText();

				req.setDayOffFrom(dayOffFrom);
				req.setDayOffTo(dayOffToPlus.toString());
				req.setDispName(dispName);

				Optional<DaoDayOff> optionalDayOff = repoDayOff.existsHolidays(req.getTimeType(), req.getDayOffFrom());
				if (optionalDayOff.isPresent()) {
					log.info("Skipping existing public holiday: {}", dayOffFrom);
					continue;
				} else {
					log.info("Adding public holiday: {}", req.toJsonTrim());
					repoDayOff.insert(req);
				}
			}

			log.info("Public holidays addition complete.");
			return ResponseEntity.ok(ResponseApi.success("Public holidays added successfully.", null));
		} catch (Exception e) {
			log.error("Adding public holidays failed: {}. Exception: {}", req, e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Failed to add public holidays.", e.getMessage()));
		}
	}

	/**
	 * 마스터 공휴일 조회
	 * company가 1인 회사의 휴일정보를 마스터 공휴일로 사용한다.
	 * @param solYear
	 * @return
	 */
	public ResponseEntity<ResponseApi> getMasterPublicHolidays(String solYear) {
		DaoDayOff holidayDao = new DaoDayOff();
		holidayDao.setTimeType("MASTER_PUBLIC");
		holidayDao.setFkCompany(1L);
		if (solYear == null || solYear.equals("")) {
			String currentYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
			holidayDao.setDayOffFrom(currentYear);
		} else {
			holidayDao.setDayOffFrom(solYear);
		}
		try {
			List<DaoDayOff> resData = repoDayOff.findByStaffAndTimeTypeAndDate(holidayDao);
			if (resData.isEmpty()) {
				return ResponseEntity.ok(ResponseApi.fail("master holidays not found.", resData));
			} else {
				return ResponseEntity.ok(ResponseApi.success("master holidays found.", resData));
			}
		} catch (Exception e) {
			log.error("Error finding holidays. {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error finding master holidays.", e.getMessage()));
		}
	}

	/**
	 * 공휴일 생성
	 * @param entityType
	 * @param entityId
	 * @param solYear
	 * @return
	 */
	public ResponseEntity<?> addPublicHolidays(String entityType, Long entityId, String solYear) {
		DaoDayOff req = new DaoDayOff();
		req.setSolYear(solYear);
		if (entityType.equals(EnumEntityType.COMPANIES.getValue())) {
			req.setFkCompany(entityId);
		} else if (entityType.equals(EnumEntityType.MEMBERS.getValue()) || entityType.equals(EnumEntityType.AIS.getValue())) {
			req.setFkCompany(repoCompanyStaff.findCompanyByStaff(entityId));
			req.setFkCompanyStaffAi(entityId);
		} else {
			log.warn("Invalid entityType: {}", entityType);
			return ResponseEntity.ok(ResponseApi.fail("Invalid entityType.", entityType));
		}
		try {
			int count = repoDayOff.insertPublicHoliday(req);
			log.info("Public holidays addition complete. insert count: {}", count);
			return ResponseEntity.ok(ResponseApi.success("Public holidays added successfully.", count));
		} catch (Exception e) {
			log.error("Adding public holidays failed. Exception: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Failed to add public holidays.", e.getMessage()));
		}
	}

	/**
	 * 휴일 생성
	 * @param entityType
	 * @param entityId
	 * @param daoDayOff
	 * @return
	 */
	public ResponseEntity<?> addHolidays(String entityType, Long entityId, DaoDayOff daoDayOff) {
		if (entityType.equals(EnumEntityType.COMPANIES.getValue())) {
			daoDayOff.setFkCompany(entityId);
		} else if (entityType.equals(EnumEntityType.MEMBERS.getValue()) || entityType.equals(EnumEntityType.AIS.getValue())) {
			daoDayOff.setFkCompany(repoCompanyStaff.findCompanyByStaff(entityId));
			daoDayOff.setFkCompanyStaffAi(entityId);
		} else {
			log.warn("Invalid entityType: {}", entityType);
			return ResponseEntity.ok(ResponseApi.fail("Invalid entityType.", entityType));
		}

		/*switch(holidayType) {
			case "general":
				daoDayOff.setTimeType("REST_GENERAL");
				break;
			case "public":
				daoDayOff.setTimeType("REST_PUBLIC");
				break;
			default:
				log.warn("Unsupported holidayType received: {}", holidayType);
				return ResponseEntity.badRequest().body(ResponseApi.fail("Unsupported holidayType", holidayType));
		}*/
		daoDayOff.setTimeType("REST_GENERAL");

		DaoDayOff dayOffData = repoDayOff.findOneByStaffAndTimeTypeAndDate(daoDayOff);
		if(dayOffData != null) {
			log.warn("Already exists. req: {}, entityType: {}, id: {}", daoDayOff.toJsonTrim(), entityType, entityId);
			return ResponseEntity.ok(ResponseApi.fail("Already exists.", dayOffData));
		}

		try {
			repoDayOff.insert(daoDayOff);
			DaoDayOff resData = repoDayOff.findOneByStaffAndTimeTypeAndDate(daoDayOff);
			log.info("Holidays added successfully. req: {}, res: {}", daoDayOff.toJsonTrim(), resData.toJsonTrim());
			return ResponseEntity.ok(ResponseApi.success("Holidays added successfully.", resData));
		} catch (Exception e) {
			log.error("Error adding holidays. req: {}, entityType: {}, id: {}", daoDayOff.toJsonTrim(), entityType, entityId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error adding holidays.", e.getMessage()));
		}
	}

	/**
	 * 휴일 조회
	 * @param entityType
	 * @param entityId
	 * @param dayOffFrom
	 * @param timeType
	 * @return
	 */
	public ResponseEntity<?> findHolidays2(
			String entityType,
			Long entityId,
			String timeType,
			String dayOffFrom
	) {
		DaoDayOff daoDayOff = new DaoDayOff();
		if (entityType.equals(EnumEntityType.COMPANIES.getValue())) {
			daoDayOff.setFkCompany(entityId);
		} else if (entityType.equals(EnumEntityType.MEMBERS.getValue()) || entityType.equals(EnumEntityType.AIS.getValue())) {
			daoDayOff.setFkCompany(repoCompanyStaff.findCompanyByStaff(entityId));
			daoDayOff.setFkCompanyStaffAi(entityId);
		} else {
			log.warn("Invalid entityType: {}", entityType);
			return ResponseEntity.ok(ResponseApi.fail("Invalid entityType.", entityType));
		}
		if (dayOffFrom == null || dayOffFrom.trim().isEmpty()) {
			String currentYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
			daoDayOff.setDayOffFrom(currentYear);
		} else {
			daoDayOff.setDayOffFrom(dayOffFrom);
		}

		switch(timeType) {
			case "general":
				daoDayOff.setTimeType("REST_GENERAL");
				break;
			case "public":
				daoDayOff.setTimeType("REST_PUBLIC");
				break;
			default:
				log.warn("Unsupported holidayType received: {}", timeType);
				return ResponseEntity.badRequest().body(ResponseApi.fail("Unsupported holidayType", timeType));
		}

		List<DaoDayOff> resData = repoDayOff.findByStaffAndTimeTypeAndDate(daoDayOff);
		if(resData.isEmpty()) {
			log.warn("No data found. entityType: {}, entityId: {}", entityType, entityId);
			return ResponseEntity.ok(ResponseApi.fail("No data found.", null));
		}

		try {
			log.info("Holiday found. req: {}, res: {}", daoDayOff.toJsonTrim(), resData);
			return ResponseEntity.ok(ResponseApi.success("Holidays found.", resData));
		} catch (Exception e) {
			log.error("Error finding holidays. req: {}, entityType: {}, entityId: {}", daoDayOff.toJsonTrim(), entityType, entityId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error finding holidays.", e.getMessage()));
		}
	}

	public ResponseEntity<ResponseApi> findHolidays(
			String entityType
			,Long entityId
			,String timeType
			,String dayOffFrom
	) {
		DaoDayOff daoDayOff = new DaoDayOff();
		if (entityType.equals(EnumEntityType.COMPANIES.getValue())) {
			daoDayOff.setFkCompany(entityId);
		} else if (entityType.equals(EnumEntityType.MEMBERS.getValue()) || entityType.equals(EnumEntityType.AIS.getValue())) {
			daoDayOff.setFkCompany(repoCompanyStaff.findCompanyByStaff(entityId));
			daoDayOff.setFkCompanyStaffAi(entityId);
		} else {
			log.warn("Invalid entityType: {}", entityType);
			return ResponseEntity.ok(ResponseApi.fail("Invalid entityType.", entityType));
		}

		if (dayOffFrom == null || dayOffFrom.isEmpty()) {
			dayOffFrom = String.valueOf(Year.now().getValue());
		}
		daoDayOff.setDayOffFrom(dayOffFrom);

		switch(timeType) {
			case "general":
				daoDayOff.setTimeType("REST_GENERAL");
				break;
			case "public":
				daoDayOff.setTimeType("REST_PUBLIC");
				break;
			case "all":
				break;
			default:
				log.warn("Unsupported holidayType received: {}", timeType);
				return ResponseEntity.badRequest().body(ResponseApi.fail("Unsupported holidayType", timeType));
		}

		List<DaoDayOff> resData = repoDayOff.findByStaffAndTimeTypeAndDate(daoDayOff);
		if(resData.isEmpty()) {
			log.warn("No data found. entityType: {}, entityId: {}", entityType, entityId);
			return ResponseEntity.ok(ResponseApi.fail("No data found.", null));
		}

		try {
			return ResponseEntity.ok(ResponseApi.success("Holidays found.", null));
		} catch (Exception e) {
			log.error("Error finding holidays. req: {}, entityType: {}, entityId: {}", daoDayOff.toJsonTrim(), entityType, entityId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error finding holidays.", e.getMessage()));
		}
	}

	/**
	 * 휴일 리스트 조회 (pagination)
	 * @param entityType
	 * @param entityId
	 * @param dayOffFrom
	 * @return
	 */
	public ResponseEntity<ResponseApi> findHolidays(
			String entityType
			,Long entityId
			,String timeType
			,String dayOffFrom
			,Pageable pageable
	) {
		DaoDayOff daoDayOff = new DaoDayOff();
		if (entityType.equals(EnumEntityType.COMPANIES.getValue())) {
			daoDayOff.setFkCompany(entityId);
		} else if (entityType.equals(EnumEntityType.MEMBERS.getValue()) || entityType.equals(EnumEntityType.AIS.getValue())) {
			daoDayOff.setFkCompany(repoCompanyStaff.findCompanyByStaff(entityId));
			daoDayOff.setFkCompanyStaffAi(entityId);
		} else {
			log.warn("Invalid entityType: {}", entityType);
			return ResponseEntity.ok(ResponseApi.fail("Invalid entityType.", entityType));
		}

		if (dayOffFrom == null || dayOffFrom.isEmpty()) {
			dayOffFrom = String.valueOf(Year.now().getValue());
		}
		daoDayOff.setDayOffFrom(dayOffFrom);

		switch(timeType) {
			case "general":
				daoDayOff.setTimeType("REST_GENERAL");
				break;
			case "public":
				daoDayOff.setTimeType("REST_PUBLIC");
				break;
			case "all":
				break;
			default:
				log.warn("Unsupported holidayType received: {}", timeType);
				return ResponseEntity.badRequest().body(ResponseApi.fail("Unsupported holidayType", timeType));
		}

		List<DaoDayOff> resData = repoDayOff.findHolidays(pageable, daoDayOff);
		int cnt = repoDayOff.countHolidays(daoDayOff);
		Page<DaoDayOff> resPage = new PageImpl<>(resData, pageable, cnt);

		try {
			return ResponseEntity.ok(ResponseApi.success("Holidays found.", resPage));
		} catch (Exception e) {
			log.error("Error finding holidays. req: {}, entityType: {}, entityId: {}", daoDayOff.toJsonTrim(), entityType, entityId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error finding holidays.", e.getMessage()));
		}
	}

	/**
	 * 휴일 수정
	 * @param entityType
	 * @return
	 */
	public ResponseEntity<?> setHolidays(
			String entityType
            ,Long entityId
            ,Long holidayId
            ,DaoDayOff req
	) {
		req.setPkAiConfDayOff(holidayId);
		if (entityType.equals(EnumEntityType.COMPANIES.getValue())) {
			req.setFkCompany(entityId);
			//daoDayOff.setFkCompanyStaffAi(repoCompanyStaff.findMasterStaffByCompany(id));
		} else if (entityType.equals(EnumEntityType.MEMBERS.getValue()) || entityType.equals(EnumEntityType.AIS.getValue())) {
			req.setFkCompany(repoCompanyStaff.findCompanyByStaff(entityId));
			req.setFkCompanyStaffAi(entityId);
		} else {
			log.warn("Invalid entityType: {}", entityType);
			return ResponseEntity.ok(ResponseApi.fail("Invalid entityType.", entityType));
		}

		try {
			repoDayOff.update(req);
			DaoDayOff resData = repoDayOff.findById(req);
			//DaoDayOff resData = repoDayOff.findOneByStaffAndTimeTypeAndDate(req);
			log.info("Holidays updated successfully. req: {}, res: {}", req.toJsonTrim(), resData.toJsonTrim());
			return ResponseEntity.ok(ResponseApi.success("Holidays updated successfully", resData));
		} catch (Exception e) {
			log.error("Error updating holidays. req: {}, entityType: {}, id: {}", req.toJsonTrim(), entityType, entityId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error updating holidays", e.getMessage()));
		}
	}

	/**
	 * 휴일 삭제
	 * @param entityType
	 * @param entityId
	 * @param holidayId
	 * @return
	 */
	public ResponseEntity<?> deleteHolidays(
			String entityType
            ,Long entityId
            ,Long holidayId
	) {
		DaoDayOff req = new DaoDayOff();
		req.setPkAiConfDayOff(holidayId);
		if (entityType.equals(EnumEntityType.COMPANIES.getValue())) {
			req.setFkCompany(entityId);
			//daoDayOff.setFkCompanyStaffAi(repoCompanyStaff.findMasterStaffByCompany(id));
		} else if (entityType.equals(EnumEntityType.MEMBERS.getValue()) || entityType.equals(EnumEntityType.AIS.getValue())) {
			req.setFkCompany(repoCompanyStaff.findCompanyByStaff(entityId));
			req.setFkCompanyStaffAi(entityId);
		} else {
			log.error("Invalid entityType: {}", entityType);
			return ResponseEntity.ok(ResponseApi.fail("Invalid entityType.", entityType));
		}

		try {
			DaoDayOff resData = repoDayOff.findById(req);
			if(resData == null) {
				return ResponseEntity.ok(ResponseApi.fail("No data found.", null));
			}
			repoDayOff.delete(req);
			log.info("Holidays deleted successfully. req: {}, res: {}", req.toJsonTrim(), resData.toJsonTrim());
			return ResponseEntity.ok(ResponseApi.success("Holidays deleted successfully.", resData));
		} catch (Exception e) {
			log.error("Error deleting holidays. req: {}, entityType: {}, id: {}", req.toJsonTrim(), entityType, entityId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error deleting holidays.", e.getMessage()));
		}
	}

	/**
	 * 공공데이터에서 공휴일 가져오기
	 * @param solYear
	 * @return
	 */
	public ResponseEntity<?> getHolidayData(String solYear) {
		try {
			String pageNo = "0";
			String numOfRows = "100";
			String solMonth = "";
			ResponseEntity<String> resMain = serviceApiDataGoKr.getRestDeInfo(
				serviceKey,
				URLEncoder.encode(pageNo, "UTF-8"),
				URLEncoder.encode(numOfRows, "UTF-8"),
				URLEncoder.encode(solYear, "UTF-8"),
				URLEncoder.encode(solMonth, "UTF-8")
			);
			String resBody = resMain.getBody();
	
			return ResponseEntity.ok(ResponseApi.success("getHolidayData success", resBody));
		} catch (Exception e) {
			log.error("getHolidayData error", e);
			return ResponseEntity.ok(ResponseApi.error("getHolidayData error", e));
		}
	}
}
