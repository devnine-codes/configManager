package com.aice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aice.dao.businesstime.DaoDayOn;
import com.aice.service.DayOnService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/{apiVer}/businesstime")
public class ControllerDayOn {
    @Autowired DayOnService dayOnService;

    /**
     * 업무시간 추가
     * @param apiVer
     * @param entityType
     * @param id
     * @param weekNum
     * @param req
     * @return
     */
    @PostMapping("{entityType}/{id}/week/{weekNum}/workhours")
    public ResponseEntity<?> addWorkhour(
            @PathVariable String apiVer
            ,@PathVariable String entityType
            ,@PathVariable Long id
            ,@PathVariable Integer weekNum
            ,@RequestBody DaoDayOn req
    ){
        return ResponseEntity.ok(dayOnService.addWorkhour(entityType, id, weekNum, req));
    }

    /**
     * 업무시간 조회
     * @param apiVer
     * @param entityType
     * @param id
     * @param weekNum
     * @return
     */
    @GetMapping("{entityType}/{id}/week/{weekNum}/workhours")
    public ResponseEntity<?> findWorkhour(
            @PathVariable String apiVer
            ,@PathVariable String entityType
            ,@PathVariable Long id
            ,@PathVariable Integer weekNum
    ){
        return ResponseEntity.ok(dayOnService.findWorkhour(entityType, id, weekNum));
    }

    /**
     * 업무시간 수정
     * @param apiVer
     * @param entityType
     * @param id
     * @param weekNum
     * @param req
     * @return
     */
    @PutMapping("{entityType}/{id}/week/{weekNum}/workhours")
    public ResponseEntity<?> setWorkhour(
            @PathVariable String apiVer
            ,@PathVariable String entityType
            ,@PathVariable Long id
            ,@PathVariable Integer weekNum
            ,@RequestBody DaoDayOn req
    ){
        return ResponseEntity.ok(dayOnService.setWorkhour(entityType, id, weekNum, req));
    }

    /**
     * 업무시간 삭제
     * @param apiVer
     * @param entityType
     * @param id
     * @param weekNum
     * @return
     */
    @DeleteMapping("{entityType}/{id}/week/{weekNum}/workhours")
    public ResponseEntity<?> deleteWorkhour(
            @PathVariable String apiVer
            ,@PathVariable String entityType
            ,@PathVariable Long id
            ,@PathVariable Integer weekNum
    ){
        return ResponseEntity.ok(dayOnService.deleteWorkhour(entityType, id, weekNum));
    }

    /**
     * 식사시간 추가
     * @param apiVer
     * @param entityType
     * @param id
     * @param timeType
     * @param weekNum
     * @param daoDayOn
     * @return
     */
    @PostMapping("{entityType}/{id}/week/{weekNum}/mealtimes/{timeType}")
    public ResponseEntity<?> addMealtime(
            @PathVariable String apiVer
            ,@PathVariable String entityType
            ,@PathVariable Long id
            ,@PathVariable String timeType
            ,@PathVariable Integer weekNum
            ,@RequestBody DaoDayOn daoDayOn
    ){
        return ResponseEntity.ok(dayOnService.addMealtime(entityType, id, weekNum, timeType, daoDayOn));
    }

    /**
     * 식사시간 조회
     * @param apiVer
     * @param entityType
     * @param id
     * @param timeType
     * @param weekNum
     * @return
     */
    @GetMapping("{entityType}/{id}/week/{weekNum}/mealtimes/{timeType}")
    public ResponseEntity<?> findMealtime(
            @PathVariable String apiVer
            ,@PathVariable String entityType
            ,@PathVariable Long id
            ,@PathVariable String timeType
            ,@PathVariable Integer weekNum
    ){
        return ResponseEntity.ok(dayOnService.findMealtime(entityType, id, weekNum, timeType));
    }

    /**
     * 식사시간 수정
     * @param apiVer
     * @param entityType
     * @param id
     * @param timeType
     * @param weekNum
     * @param daoDayOn
     * @return
     */
    @PutMapping("{entityType}/{id}/week/{weekNum}/mealtimes/{timeType}")
    public ResponseEntity<?> setMealtime(
            @PathVariable String apiVer
            ,@PathVariable String entityType
            ,@PathVariable Long id
            ,@PathVariable String timeType
            ,@PathVariable Integer weekNum
            ,@RequestBody DaoDayOn daoDayOn
    ){
        return ResponseEntity.ok(dayOnService.setMealtime(entityType, id, weekNum, timeType, daoDayOn));
    }

    /**
     * 식사시간 삭제
     * @param apiVer
     * @param entityType
     * @param id
     * @param timeType
     * @param weekNum
     * @return
     */
    @DeleteMapping("{entityType}/{id}/week/{weekNum}/mealtimes/{timeType}")
    public ResponseEntity<?> deleteMealtime(
            @PathVariable String apiVer
            ,@PathVariable String entityType
            ,@PathVariable Long id
            ,@PathVariable String timeType
            ,@PathVariable Integer weekNum
    ){
        return ResponseEntity.ok(dayOnService.deleteMealtime(entityType, id, weekNum, timeType));
    }

    /** 현재 시간을 기준으로 BizTime 정보 확인 */
    @GetMapping("/{type}/{id}/now")
    public ResponseEntity<?> getBizTime(
            @PathVariable String apiVer,
            @PathVariable String type,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(dayOnService.getBizTimeByNow(type, id));
    }
}