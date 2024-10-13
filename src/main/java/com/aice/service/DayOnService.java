package com.aice.service;

import java.util.*;

import com.aice.dao.businesstime.DaoDayOff;
import com.aice.enums.EnumEntityType;
import com.aice.repo.RepoCompanyStaff;
import com.aice.repo.RepoDayOff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.aice.dao.ResponseApi;
import com.aice.dao.businesstime.DaoDayOn;
import com.aice.repo.RepoDayOn;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DayOnService {

	@Autowired
	RepoDayOn repoDayOn;
	@Autowired
	RepoDayOff repoDayOff;
	@Autowired
	RepoCompanyStaff repoCompanyStaff;

	/**
	 * 업무시간 추가
	 * @param entityType companies, members, ais
	 * @param id
	 * @param weekNum 1-7(일-토)
	 * @param daoDayOn
	 * @return
	 */
	public ResponseEntity<?> addWorkhour(
			String entityType
			,Long id
			,Integer weekNum
			,DaoDayOn daoDayOn
	) {
		if (entityType.equals(EnumEntityType.COMPANIES.getValue())) {
			daoDayOn.setFkCompany(id);
		} else if (entityType.equals(EnumEntityType.MEMBERS.getValue()) || entityType.equals(EnumEntityType.AIS.getValue())) {
			daoDayOn.setFkCompany(repoCompanyStaff.findCompanyByStaff(id));
			daoDayOn.setFkCompanyStaffAi(id);
		} else {
			log.warn("Invalid entityType: {}", entityType);
			return ResponseEntity.ok(ResponseApi.fail("Invalid entityType.", entityType));
		}
		daoDayOn.setWeekNum(weekNum);
		daoDayOn.setWorkType("W");
		daoDayOn.setTimeType("WORK_ON");

		DaoDayOn dayOnData = repoDayOn.findStaffAndWeekNumAndTimeTypeAndUseYn(daoDayOn);
		if (dayOnData != null) {
			log.info("Already exists. req: {}, entityType: {}, id: {}", daoDayOn.toJsonTrim(), entityType, id);
			return ResponseEntity.ok(ResponseApi.fail("Already exists.", dayOnData));
		}

		log.info("Workhour add. req: {}, entityType: {}, id: {}", daoDayOn.toJsonTrim(), entityType, id);
		try {
			repoDayOn.insert(daoDayOn);
			DaoDayOn resData = repoDayOn.findStaffAndWeekNumAndTimeTypeAndUseYn(daoDayOn);

			log.info("Workhour added successfully. req: {}, res: {}", daoDayOn.toJsonTrim(), resData.toJsonTrim());
			return ResponseEntity.ok(ResponseApi.success("Workhour added successfully.", resData));
		} catch (Exception e) {
			log.error("Error adding workhour. req: {}, entityType: {}, id: {}", daoDayOn.toJsonTrim(), entityType, id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error adding workhour.", e.getMessage()));
		}
	}

	/**
	 * 업무시간 조회
	 * @param entityType companies, members, ais
	 * @param id
	 * @param weekNum 1-7(일-토)
	 * @return
	 */
	public ResponseEntity<?> findWorkhour(
			String entityType
			,Long id
			,Integer weekNum
	) {
		DaoDayOn daoDayOn = new DaoDayOn();
		if (entityType.equals(EnumEntityType.COMPANIES.getValue())) {
			daoDayOn.setFkCompany(id);
		} else if (entityType.equals(EnumEntityType.MEMBERS.getValue()) || entityType.equals(EnumEntityType.AIS.getValue())) {
			daoDayOn.setFkCompany(repoCompanyStaff.findCompanyByStaff(id));
			daoDayOn.setFkCompanyStaffAi(id);
		} else {
			log.error("Invalid entityType: {}", entityType);
			return ResponseEntity.ok(ResponseApi.fail("Invalid entityType.", entityType));
		}
		daoDayOn.setWeekNum(weekNum);
		daoDayOn.setWorkType("W");
		daoDayOn.setTimeType("WORK_ON");

		log.info("Workhour find. req: {}, entityType: {}, id: {}", daoDayOn.toJsonTrim(), entityType, id);
		try {
			DaoDayOn resData = repoDayOn.findStaffAndWeekNumAndTimeTypeAndUseYn(daoDayOn);
			if (resData == null) {
				log.warn("No data found. entityType: {}, id: {}", entityType, id);
				return ResponseEntity.ok(ResponseApi.fail("No data found.", null));
			}

			log.info("Workhour found. req: {}, res: {}", daoDayOn.toJsonTrim(), resData.toJsonTrim());
			return ResponseEntity.ok(ResponseApi.success("Workhour found.", resData));
		} catch (Exception e) {
			log.error("Error finding workhour. req: {}, entityType: {}, id: {}", daoDayOn.toJsonTrim(), entityType, id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error finding workhour.", e.getMessage()));
		}
	}

	/**
	 * 업무시간 수정
	 * @param entityType companies, members, ais
	 * @param id
	 * @param weekNum 1-7(일-토)
	 * @param daoDayOn
	 * @return
	 */
	public ResponseEntity<?> setWorkhour(
			String entityType
			,Long id
			,Integer weekNum
			,DaoDayOn daoDayOn
	) {
		if (entityType.equals(EnumEntityType.COMPANIES.getValue())) {
			daoDayOn.setFkCompany(id);
		} else if (entityType.equals(EnumEntityType.MEMBERS.getValue()) || entityType.equals(EnumEntityType.AIS.getValue())) {
			daoDayOn.setFkCompany(repoCompanyStaff.findCompanyByStaff(id));
			daoDayOn.setFkCompanyStaffAi(id);
		} else {
			log.warn("Invalid entityType: {}", entityType);
			return ResponseEntity.ok(ResponseApi.fail("Invalid entityType.", entityType));
		}
		daoDayOn.setWeekNum(weekNum);
		daoDayOn.setWorkType("W");
		daoDayOn.setTimeType("WORK_ON");

		DaoDayOn dayOnData = repoDayOn.findStaffAndWeekNumAndTimeTypeAndUseYn(daoDayOn);
		if (dayOnData == null) {
			log.warn("No data found. entityType: {}, id: {}", entityType, id);
			return ResponseEntity.ok(ResponseApi.fail("No data found.", null));
		}

		log.info("Workhour update. req: {}, entityType: {}, id: {}", daoDayOn.toJsonTrim(), entityType, id);
		try {
			repoDayOn.update(daoDayOn);
			DaoDayOn resData = repoDayOn.findStaffAndWeekNumAndTimeTypeAndUseYn(daoDayOn);
			log.info("Workhour updated successfully. req: {}, res: {}", daoDayOn.toJsonTrim(), resData.toJsonTrim());
			return ResponseEntity.ok(ResponseApi.success("Workhour updated successfully.", resData));
		} catch (Exception e) {
			log.error("Error updating workhour. req: {}, entityType: {}, id: {}", daoDayOn.toJsonTrim(), entityType, id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error updating workhour.", e.getMessage()));
		}
	}

	/**
	 * 업무시간 삭제
	 * @param entityType companies, members, ais
	 * @param id
	 * @param weekNum 1-7(일-토)
	 * @return
	 */
	public ResponseEntity<?> deleteWorkhour(
			String entityType
			,Long id
			,Integer weekNum
	) {
		DaoDayOn daoDayOn = new DaoDayOn();
		if (entityType.equals(EnumEntityType.COMPANIES.getValue())) {
			daoDayOn.setFkCompany(id);
		} else if (entityType.equals(EnumEntityType.MEMBERS.getValue()) || entityType.equals(EnumEntityType.AIS.getValue())) {
			daoDayOn.setFkCompany(repoCompanyStaff.findCompanyByStaff(id));
			daoDayOn.setFkCompanyStaffAi(id);
		} else {
			log.warn("Invalid entityType: {}", entityType);
			return ResponseEntity.ok(ResponseApi.fail("Invalid entityType.", entityType));
		}
		daoDayOn.setWeekNum(weekNum);
		daoDayOn.setTimeType("WORK_ON");

		DaoDayOn dayOnData = repoDayOn.findStaffAndWeekNumAndTimeTypeAndUseYn(daoDayOn);
		if (dayOnData == null) {
			log.warn("No data found. entityType: {}, id: {}", entityType, id);
			return ResponseEntity.ok(ResponseApi.fail("No data found.", null));
		}

		try {
			repoDayOn.delete(daoDayOn);
			log.info("Workhour deleted successfully. data: {}", daoDayOn.toJsonTrim());
			return ResponseEntity.ok(ResponseApi.success("Workhour deleted successfully.", daoDayOn));
		} catch (Exception e) {
			log.error("Error deleting workhour. req: {}, entityType: {}, id: {}", daoDayOn.toJsonTrim(), entityType, id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error deleting workhour.", e.getMessage()));
		}
	}


	/**
	 * 식사시간 추가
	 * @param entityType
	 * @param id
	 * @param weekNum
	 * @param timeType
	 * @param daoDayOn
	 * @return
	 */
	public ResponseEntity<?> addMealtime(
			String entityType
			,Long id
			,Integer weekNum
			,String timeType
			,DaoDayOn daoDayOn
	) {
		if (entityType.equals(EnumEntityType.COMPANIES.getValue())) {
			daoDayOn.setFkCompany(id);
		} else if (entityType.equals(EnumEntityType.MEMBERS.getValue()) || entityType.equals(EnumEntityType.AIS.getValue())) {
			daoDayOn.setFkCompany(repoCompanyStaff.findCompanyByStaff(id));
			daoDayOn.setFkCompanyStaffAi(id);
		} else {
			log.error("Invalid entityType: {}", entityType);
			return ResponseEntity.ok(ResponseApi.fail("Invalid entityType.", entityType));
		}
		daoDayOn.setWeekNum(weekNum);
		daoDayOn.setWorkType("R");

		switch(timeType) {
			case "lunch":
				daoDayOn.setTimeType("REST_LUNCH");
				break;
			case "dinner":
				daoDayOn.setTimeType("REST_DINNER");
				break;
			default:
				log.error("Unsupported timeType received: {}", timeType);
				return ResponseEntity.badRequest().body(ResponseApi.fail("Unsupported timeType", timeType));
		}

		DaoDayOn dayOnData = repoDayOn.findStaffAndWeekNumAndTimeTypeAndUseYn(daoDayOn);
		if (dayOnData != null) {
			log.error("Already exists. req: {}, entityType: {}, id: {}", daoDayOn.toJsonTrim(), entityType, id);
			return ResponseEntity.ok(ResponseApi.fail("Already exists.", dayOnData));
		}

		log.info("Mealtime add. req: {}, entityType: {}, id: {}", daoDayOn.toJsonTrim(), entityType, id);
		try {
			repoDayOn.insert(daoDayOn);
			DaoDayOn resData = repoDayOn.findStaffAndWeekNumAndTimeTypeAndUseYn(daoDayOn);
			log.info("Mealtime added successfully. req: {}, res: {}", daoDayOn.toJsonTrim(), resData.toJsonTrim());
			return ResponseEntity.ok(ResponseApi.success("Mealtime added successfully.", resData));
		} catch (Exception e) {
			log.error("Error adding mealtime. req: {}, entityType: {}, id: {}", daoDayOn.toJsonTrim(), entityType, id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error adding mealtime.", e.getMessage()));
		}
	}

	/**
	 * 식사시간 조회
	 * @param entityType
	 * @param id
	 * @param weekNum
	 * @param timeType
	 * @return
	 */
	public ResponseEntity<?> findMealtime(
			String entityType
			,Long id
			,Integer weekNum
			,String timeType
	) {
		DaoDayOn daoDayOn = new DaoDayOn();
		if (entityType.equals(EnumEntityType.COMPANIES.getValue())) {
			daoDayOn.setFkCompany(id);
		} else if (entityType.equals(EnumEntityType.MEMBERS.getValue()) || entityType.equals(EnumEntityType.AIS.getValue())) {
			daoDayOn.setFkCompany(repoCompanyStaff.findCompanyByStaff(id));
			daoDayOn.setFkCompanyStaffAi(id);
		} else {
			log.error("Invalid entityType: {}", entityType);
			return ResponseEntity.ok(ResponseApi.fail("Invalid entityType.", entityType));
		}
		daoDayOn.setWeekNum(weekNum);
		daoDayOn.setWorkType("R");

		switch(timeType) {
			case "lunch":
				daoDayOn.setTimeType("REST_LUNCH");
				break;
			case "dinner":
				daoDayOn.setTimeType("REST_DINNER");
				break;
			default:
				log.error("Unsupported timeType received: {}", timeType);
				return ResponseEntity.badRequest().body(ResponseApi.fail("Unsupported timeType", timeType));
		}

		try {
			DaoDayOn resData = repoDayOn.findStaffAndWeekNumAndTimeTypeAndUseYn(daoDayOn);
			if (resData == null) {
				log.warn("No data found. entityType: {}, id: {}", entityType, id);
				return ResponseEntity.ok(ResponseApi.fail("No data found.", null));
			}

			log.info("Mealtime found. req: {}, res: {}", daoDayOn.toJsonTrim(), resData.toJsonTrim());
			return ResponseEntity.ok(ResponseApi.success("Mealtime found.", resData));
		} catch (Exception e) {
			log.error("Error finding mealtime. req: {}, entityType: {}, id: {}", daoDayOn.toJsonTrim(), entityType, id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error finding mealtime.", e.getMessage()));
		}
	}

	/**
	 * 식사시간 수정
	 * @param entityType
	 * @param id
	 * @param weekNum
	 * @param timeType
	 * @param daoDayOn
	 * @return
	 */
	public ResponseEntity<?> setMealtime(
			String entityType
			,Long id
			,Integer weekNum
			,String timeType
			,DaoDayOn daoDayOn
	) {
		if (entityType.equals(EnumEntityType.COMPANIES.getValue())) {
			daoDayOn.setFkCompany(id);
		} else if (entityType.equals(EnumEntityType.MEMBERS.getValue()) || entityType.equals(EnumEntityType.AIS.getValue())) {
			daoDayOn.setFkCompany(repoCompanyStaff.findCompanyByStaff(id));
			daoDayOn.setFkCompanyStaffAi(id);
		} else {
			log.error("Invalid entityType: {}", entityType);
			return ResponseEntity.ok(ResponseApi.fail("Invalid entityType.", entityType));
		}
		daoDayOn.setWeekNum(weekNum);
		daoDayOn.setWorkType("R");

		switch(timeType) {
			case "lunch":
				daoDayOn.setTimeType("REST_LUNCH");
				break;
			case "dinner":
				daoDayOn.setTimeType("REST_DINNER");
				break;
			default:
				log.warn("Unsupported timeType received: {}", timeType);
				return ResponseEntity.badRequest().body(ResponseApi.fail("Unsupported timeType", timeType));
		}

		DaoDayOn dayOnData = repoDayOn.findStaffAndWeekNumAndTimeTypeAndUseYn(daoDayOn);
		if (dayOnData == null) {
			log.warn("No data found. entityType: {}, id: {}", entityType, id);
			return ResponseEntity.ok(ResponseApi.fail("No data found.", null));
		}

		try {
			repoDayOn.update(daoDayOn);
			DaoDayOn resData = repoDayOn.findStaffAndWeekNumAndTimeTypeAndUseYn(daoDayOn);
			log.info("Mealtime updated successfully. req: {}, res: {}", daoDayOn.toJsonTrim(), resData.toJsonTrim());
			return ResponseEntity.ok(ResponseApi.success("Mealtime updated successfully.", resData));
		} catch (Exception e) {
			log.error("Error updating mealtime. req: {}, entityType: {}, id: {}", daoDayOn.toJsonTrim(), entityType, id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error updating mealtime.", e.getMessage()));
		}
	}

	/**
	 * 식사시간 삭제
	 * @param entityType
	 * @param id
	 * @param weekNum
	 * @param timeType
	 * @return
	 */
	public ResponseEntity<?> deleteMealtime(
			String entityType
			,Long id
			,Integer weekNum
			,String timeType
	) {
		DaoDayOn daoDayOn = new DaoDayOn();
		if (entityType.equals(EnumEntityType.COMPANIES.getValue())) {
			daoDayOn.setFkCompany(id);
		} else if (entityType.equals(EnumEntityType.MEMBERS.getValue()) || entityType.equals(EnumEntityType.AIS.getValue())) {
			daoDayOn.setFkCompany(repoCompanyStaff.findCompanyByStaff(id));
			daoDayOn.setFkCompanyStaffAi(id);
		} else {
			log.error("Invalid entityType: {}", entityType);
			return ResponseEntity.ok(ResponseApi.fail("Invalid entityType.", entityType));
		}
		daoDayOn.setWeekNum(weekNum);

		switch(timeType) {
			case "lunch":
				daoDayOn.setTimeType("REST_LUNCH");
				break;
			case "dinner":
				daoDayOn.setTimeType("REST_DINNER");
				break;
			default:
				log.warn("Unsupported timeType received: {}", timeType);
				return ResponseEntity.badRequest().body(ResponseApi.fail("Unsupported timeType", timeType));
		}

		DaoDayOn dayOnData = repoDayOn.findStaffAndWeekNumAndTimeTypeAndUseYn(daoDayOn);
		if (dayOnData == null) {
			log.warn("No data found. entityType: {}, id: {}", entityType, id);
			return ResponseEntity.ok(ResponseApi.fail("No data found.", null));
		}

		try {
			repoDayOn.delete(daoDayOn);
			log.info("Mealtime deleted successfully. data: {}", daoDayOn.toJsonTrim());
			return ResponseEntity.ok(ResponseApi.success("Mealtime deleted successfully.", daoDayOn));
		} catch (Exception e) {
			log.error("Error deleting mealtime. req: {}, entityType: {}, id: {}", daoDayOn.toJsonTrim(), entityType, id, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error deleting mealtime.", e.getMessage()));
		}
	}

	/**
	 * 현재를 기준으로 업무시간 정보, 휴일 정보, 공휴일 정보를 조회하여 상태를 제공함
	 * AI팀에서 호출
	 */
	public ResponseEntity<?> getBizTimeByNow(String type, Long id) {
		ResponseEntity<?> res = ResponseEntity.ok(null);
		DaoDayOn daoDayOn = new DaoDayOn();
		DaoDayOff daoDayOff = new DaoDayOff();

		// 회사
		// 실제로 사용할 일 없어보임
		if (type.equals(EnumEntityType.COMPANIES.getValue())) {
			daoDayOn.setFkCompany(id);
			daoDayOff.setFkCompany(id);
		}
		// 멤버
		// 사용자의 휴일에 대해 생성할 수 있어야 한다고함. 실제 사용하고 있지는 않지만 요구사항에 있어 생성
		else if (type.equals(EnumEntityType.MEMBERS.getValue())) {
			Long companyId = repoCompanyStaff.findCompanyByStaff(id);
			daoDayOn.setFkCompany(companyId);
			daoDayOn.setFkCompanyStaffAi(id);
			daoDayOff.setFkCompany(companyId);
			daoDayOff.setFkCompanyStaffAi(id);
		}
		// AI
		// AI의 휴일은 회사의 휴일을 따라가도록 dayOff에 대해 aikey를 null로 처리함
		else if (type.equals(EnumEntityType.AIS.getValue())) {
			Long companyId = repoCompanyStaff.findCompanyByStaff(id);
			daoDayOn.setFkCompany(companyId);
			daoDayOn.setFkCompanyStaffAi(id);
			daoDayOff.setFkCompany(companyId);
			daoDayOff.setFkCompanyStaffAi(null); // AI일 때 fkCompanyStaffAi는 null
		} else {
			return ResponseEntity.ok(ResponseApi.fail("Invalid entityType.", type));
		}

		log.info("Businesstime data daoDyOn: {}, daoDayOff: {}", daoDayOn.toJsonTrim(), daoDayOff.toJsonTrim());

		try {
			Map<String, Object> map = new HashMap<>();
			DaoDayOn dayOnItem = repoDayOn.findByStaffAndNow(daoDayOn);
			DaoDayOff dayOffItem = repoDayOff.findByStaffAndNow(daoDayOff);
			Integer publicHoliday = repoDayOff.findOnePublicHoliday(daoDayOff);

			putIfNotBlank(map, "dayType", convertDayOfWeek());
			// 업무유형과 휴일 정보가 존재하지 않은 경우
			if (dayOnItem == null && dayOffItem == null) {
				map.put("timeType", "off");
				res = ResponseEntity.ok(ResponseApi.success("Successfully retrieved businesstime. 1", map));
			}

			// 업무유형은 없고 휴일 정보만 존재하는 경우
			if (dayOnItem == null && dayOffItem != null) {
				map.put("timeType", "off");
				putIfNotBlank(map, "msgIntro", dayOffItem.getMsgIntro());
				String timeType = dayOffItem.getTimeType();
				if (timeType.equals("REST_PUBLIC") || timeType.equals("REST_GENERAL")) {
					map.put("offType", timeType);
					putIfNotBlank(map, "dispName", dayOffItem.getDispName());
				}
				res = ResponseEntity.ok(ResponseApi.success("Successfully retrieved businesstime. 2", map));
			}

			// 업무유형은 존재하고 휴일 정보가 없는 경우
			if (dayOnItem != null && dayOffItem == null) {
				map.put("timeType", convertTimeType(dayOnItem.getTimeType()));
				putIfNotBlank(map, "msgIntro", dayOnItem.getMsgIntro());
				putIfNotBlank(map, "msgOff", dayOnItem.getMsgClose());

				res = ResponseEntity.ok(ResponseApi.success("Successfully retrieved businesstime. 3", map));
			}

			// 업무유형과 휴일 정보가 존재하는 경우
			if (dayOnItem != null && dayOffItem != null) {
				map.put("timeType", "off");
				putIfNotBlank(map, "msgIntro", dayOffItem.getMsgIntro());
				String timeType = dayOffItem.getTimeType();
				if (timeType.equals("REST_PUBLIC") || timeType.equals("REST_GENERAL")) {
					map.put("offType", timeType);
					putIfNotBlank(map, "dispName", dayOffItem.getDispName());
				}
				res = ResponseEntity.ok(ResponseApi.success("Successfully retrieved businesstime. 4", map));
			}

			// 2024-05-24 한나님, 보경님과 커뮤니케이션. 통인 공휴일 정보 적용하는데 아래같이 요청이 들어와서 적용.
			// 오늘이 공휴일이고 공휴일 정보가 존재하면 dayType에 holiday를 보낸다.
			if ("off".equals(map.get("timeType")) && publicHoliday != null) {
				map.put("dayType", "holiday");
			}
		} catch (Exception e) {
			log.error("Error while retrieving businesstime for: {}", daoDayOn.toJsonTrim(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Failed to retrieve businesstime.", e.getMessage()));
		}
		return res;
	}

	// 백업용
	/*
	public ResponseEntity<?> getBizTimeByNow(String type, Long id) {
		ResponseEntity<?> res = ResponseEntity.ok(null);
		DaoDayOn daoDayOn = new DaoDayOn();
		if (type.equals(EnumEntityType.COMPANIES.getValue())) {
			daoDayOn.setFkCompany(id);
		} else if (type.equals(EnumEntityType.MEMBERS.getValue()) || type.equals(EnumEntityType.AIS.getValue())) {
			daoDayOn.setFkCompany(repoCompanyStaff.findCompanyByStaff(id));
			daoDayOn.setFkCompanyStaffAi(id);

		} else {
			return ResponseEntity.ok(ResponseApi.fail("Invalid entityType.", type));
		}
		log.info("Businesstime data request: {}", daoDayOn.toJsonTrim());

		try {
			Map<String, Object> map = new HashMap<>();
			DaoDayOn dayOnItem = repoDayOn.findByStaffAndNow(daoDayOn);
			DaoDayOff dayOffItem = repoDayOff.findByStaffAndNow(daoDayOn);
			Integer publicHoliday = repoDayOff.findOnePublicHoliday(daoDayOn);

			putIfNotBlank(map, "dayType", convertDayOfWeek());
			// 업무유형과 휴일 정보가 존재하지 않은 경우
			if (dayOnItem == null && dayOffItem == null) {
				map.put("timeType", "off");
				res = ResponseEntity.ok(ResponseApi.success("Successfully retrieved businesstime. 1", map));
			}

			// 업무유형은 없고 휴일 정보만 존재하는 경우
			if (dayOnItem == null && dayOffItem != null) {
				map.put("timeType", "off");
				putIfNotBlank(map, "msgIntro", dayOffItem.getMsgIntro());
				String timeType = dayOffItem.getTimeType();
				if (timeType.equals("REST_PUBLIC") || timeType.equals("REST_GENERAL")) {
					map.put("offType", timeType);
					putIfNotBlank(map, "dispName", dayOffItem.getDispName());
				}
				res = ResponseEntity.ok(ResponseApi.success("Successfully retrieved businesstime. 2", map));
			}

			// 업무유형은 존재하고 휴일 정보가 없는 경우
			if (dayOnItem != null && dayOffItem == null) {
				map.put("timeType", convertTimeType(dayOnItem.getTimeType()));
				putIfNotBlank(map, "msgIntro", dayOnItem.getMsgIntro());
				putIfNotBlank(map, "msgOff", dayOnItem.getMsgClose());

				res = ResponseEntity.ok(ResponseApi.success("Successfully retrieved businesstime. 3", map));
			}

			// 업무유형과 휴일 정보가 존재하는 경우
			if (dayOnItem != null && dayOffItem != null) {
				map.put("timeType", "off");
				putIfNotBlank(map, "msgIntro", dayOffItem.getMsgIntro());
				String timeType = dayOffItem.getTimeType();
				if (timeType.equals("REST_PUBLIC") || timeType.equals("REST_GENERAL")) {
					map.put("offType", timeType);
					putIfNotBlank(map, "dispName", dayOffItem.getDispName());
				}
				res = ResponseEntity.ok(ResponseApi.success("Successfully retrieved businesstime. 4", map));
			}

			// 2024-05-24 한나님, 보경님과 커뮤니케이션. 통인 공휴일 정보 적용하는데 아래같이 요청이 들어와서 적용.
			// 오늘이 공휴일이고 공휴일 정보가 존재하면 dayType에 holiday를 보낸다.
			if ("off".equals(map.get("timeType")) && publicHoliday != null) {
				map.put("dayType", "holiday");
			}
		} catch (Exception e) {
			log.error("Error while retrieving businesstime for: {}", daoDayOn.toJsonTrim(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Failed to retrieve businesstime.", e.getMessage()));
		}
		return res;
	}
	*/

	private String convertTimeType(String timeTypeCode) {
		switch(timeTypeCode) {
			case "WORK_ON":
				return "on";
			case "REST_LUNCH":
				return "lunch";
			case "REST_DINNER":
				return "dinner";
			default:
				return "on";
		}
	}

	private String convertDayOfWeek() {
		Integer dayOfWeek = repoDayOff.dayOfWeek();
		switch(dayOfWeek) {
			case 1:
				return "weekend";
			case 2:
				return "weekday";
			case 3:
				return "weekday";
			case 4:
				return "weekday";
			case 5:
				return "weekday";
			case 6:
				return "weekday";
			case 7:
				return "weekend";
			default:
				return "not found";
		}
	}

	private void putIfNotBlank(Map<String, Object> map, String key, String value) {
		Optional.ofNullable(value)
				.filter(v -> !v.isBlank())
				.ifPresent(v -> map.put(key, v));
	}
}