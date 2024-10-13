package com.aice.controller;

import com.aice.dao.ResponseApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aice.dao.businesstime.DaoDayOff;
import com.aice.service.DayOffService;

@RestController
@RequestMapping("/{apiVer}/businesstime")
public class ControllerDayOff {
    @Autowired DayOffService dayOffService;

    /**
     * 마스터 공휴일 생성
     * @param apiVer
     * @return
     */
    @PostMapping("/holidays/{solYear}/public")
    public ResponseEntity<?> addMasterPublicHolidays(
            @PathVariable("apiVer") String apiVer,
            @PathVariable("solYear") String solYear
    ) {
        return dayOffService.addMasterPublicHolidays(solYear);
    }

    /**
     * 마스터 공휴일 조회
     * @param apiVer
     * @param solYear YYYY년도
     * @return
     */
    @GetMapping("/holidays/{solYear}/public")
    public ResponseEntity<ResponseApi> getMasterPublicHolidays(
            @PathVariable("apiVer") String apiVer,
            @PathVariable("solYear") String solYear
    ) {
        return dayOffService.getMasterPublicHolidays(solYear);
    }

    /**
     * 공휴일 생성
     * @param apiVer
     * @return
     */
    @PostMapping("{entityType}/{entityId}/holidays/{solYear}/public")
    public ResponseEntity<?> addPublicHolidays(
            @PathVariable("apiVer") String apiVer,
            @PathVariable String entityType,
            @PathVariable Long entityId,
            @PathVariable String solYear
    ) {
        return dayOffService.addPublicHolidays(entityType, entityId, solYear);
    }


    /**
     * 휴일 등록
     * @param apiVer
     * @param entityType
     * @param entityId
     * @param daoDayOff
     * @return
     */
    @PostMapping("/{entityType}/{entityId}/holidays")
    public ResponseEntity<?> addHolidays(
            @PathVariable String apiVer
            ,@PathVariable String entityType
            ,@PathVariable Long entityId
            ,@RequestBody DaoDayOff daoDayOff
    ) {
        return dayOffService.addHolidays(entityType, entityId, daoDayOff);
    }

    /**
     * 휴일 조회
     * @param apiVer
     * @param entityType
     * @param entityId
     * @param dayOffFrom
     * @return
     */
    @GetMapping("/{entityType}/{entityId}/holidays/{holidayId}")
    public ResponseEntity<?> findHolidays(
            @PathVariable String apiVer
            ,@PathVariable String entityType
            ,@PathVariable Long entityId
            ,@RequestParam(required = false, value = "holidayType") String timeType
            ,@RequestParam(required = false,value = "date") String dayOffFrom
    ) {
        return dayOffService.findHolidays(entityType, entityId, timeType, dayOffFrom);
    }

    /**
     * 휴일 수정
     * @param apiVer
     * @param entityType
     * @return
     */
    @PutMapping("/{entityType}/{entityId}/holidays/{holidayId}")
    public ResponseEntity<?> setHolidays(
            @PathVariable String apiVer
            ,@PathVariable String entityType
            ,@PathVariable Long entityId
            ,@PathVariable Long holidayId
            ,@RequestBody DaoDayOff req
    ) {
        return dayOffService.setHolidays(entityType, entityId, holidayId, req);
    }

    /**
     * 휴일 삭제
     * @param apiVer
     * @param entityType
     * @param entityId
     * @param holidayId
     * @return
     */
    @DeleteMapping("/{entityType}/{entityId}/holidays/{holidayId}")
    public ResponseEntity<?> deleteHolidays(
            @PathVariable String apiVer
            ,@PathVariable String entityType
            ,@PathVariable Long entityId
            ,@PathVariable Long holidayId
    ) {
        return dayOffService.deleteHolidays(entityType, entityId, holidayId);
    }

    /**
     * 휴일 검색 조회
     * @param apiVer
     * @param entityType
     * @param entityId
     * @param dayOffFrom
     * @param pageable
     * @return
     */
    @GetMapping("/{entityType}/{entityId}/holidays")
    public ResponseEntity<ResponseApi> getDayOffs(
            @PathVariable String apiVer,
            @PathVariable String entityType,
            @PathVariable Long entityId,
            @RequestParam(required = false, value = "holidayType", defaultValue = "all") String timeType,
            @RequestParam(required = false, value = "date") String dayOffFrom,
            Pageable pageable
    ) {
        return dayOffService.findHolidays(entityType, entityId, timeType, dayOffFrom, pageable);
    }
}