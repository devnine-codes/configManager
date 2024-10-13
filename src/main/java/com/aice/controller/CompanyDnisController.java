package com.aice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import com.aice.dao.ResponseApi;
import com.aice.dao.dnis.DaoCompanyDnis;
import com.aice.dao.vgw.ResVgwNumberPlans;
import com.aice.dao.vgw.ResVgwNumbers;
import com.aice.repo.RepoCompanyDnis;
import com.aice.service.CompanyDnisService;

import lombok.extern.slf4j.Slf4j;

@Tag(name="DNIS", description="워크센터 내선번호 관리")
@Slf4j
@RestController
@RequestMapping("/dnis")
public class CompanyDnisController {
    @Autowired RepoCompanyDnis repoCompanyDnis;
    @Autowired CompanyDnisService companyDnisService;

    /**
     * 임시 번호 voice gateway temp 내선번호 생성 config manager tbl_company_dnis 레코드 생성
     */
    @PostMapping(value="/temp/regist",consumes=MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "임시 내선번호 생성", description = "임시 내선번호 생성")
    public ResponseEntity<?> saveCompanyDnis(@RequestBody DaoCompanyDnis req) {
        req.setNumberType("temp");
        req.setDnisType("temp");
        log.info("## saveCompanyDnis req: {}",req);
        return companyDnisService.saveCompanyDnis(req);
    }

    /**
     * 임시 번호 voice gateway temp 내선 번호 수정 config manager tbl_company_dnis 레코드 수정
     */
    @PutMapping(value="/temp/update",consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCompanyDnis(@RequestBody DaoCompanyDnis req) {
        req.setNumberType("temp");
        req.setDnisType("temp");
        log.info("## updateCompanyDnis req: {}",req);
        return companyDnisService.updateCompanyDnis(req);
    }

    /**
     * 임시 번호 voice gateway temp 내선 번호 제거 config manager tbl_company_dnis 레코드 삭제
     */
    @DeleteMapping(value="/temp/{dnis}",consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCompanyDnis(@PathVariable String dnis,@RequestBody DaoCompanyDnis req) {
        log.info("## deleteCompanyDnis dnis: {}",dnis);
        log.info("## req: {}",req);
        req.setNumberType("temp");
        req.setFdDnis(dnis);
        return companyDnisService.deleteCompanyDnis(req);
    }

    /**
     * 내선 번호 voice gateway normal 내선 번호 생성 config manager tbl_company_dnis 레코드 생성
     * <li>tbl_company_biz_phone 에 dnis,fullDnis 추가</li>
     */
    @PostMapping(value="/save",consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody DaoCompanyDnis req) {
        if (req.getNumberType() == null || "".equals(req.getNumberType())) {
            req.setNumberType("normal");
            req.setDnisType("normal");
        } else {
            req.setDnisType(req.getNumberType());
        }

        if ((req.getFdDnis() == null && req.getFullDnis() == null)) {
            return companyDnisService.saveCompanyDnisAuto(req);
        } else {
            return companyDnisService.saveCompanyDnis(req);
        }
    }

    /**
     * 내선 번호 voice gateway normal 내선 번호 생성 수정 config manager tbl_company_dnis 레코드 수정
     */
    @PutMapping(value="/update",consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody DaoCompanyDnis req) {
        req.setNumberType("normal");
        req.setDnisType("normal");
        log.info("## update req: {}",req);
        return companyDnisService.updateCompanyDnis(req);
    }

    /**
     * 내선 번호 voice gateway normal 내선 번호 생성 제거 config manager tbl_company_dnis 레코드 삭제
     * <li>tbl_company_biz_phone 에 dnis 연결 해제</li>
     * <li>tbl_company_biz_phone 에 dnis,fullDnis 모두 같은것 삭제</li>
     */
    @DeleteMapping(value="/delete/{dnis}",consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable String dnis,@RequestBody DaoCompanyDnis req) {
        if (req.getNumberType() == null || "".equals(req.getNumberType())) {
            req.setNumberType("normal");
            req.setDnisType("normal");
        } else {
            req.setDnisType(req.getNumberType());
        }
        req.setFdDnis(dnis);
        return companyDnisService.deleteCompanyDnis(req);
    }

    /**
     * 내선번호 회수 (회사 기준)
     * @param id
     * @return
     */
    @DeleteMapping(value="/companies/{id}")
    public ResponseEntity<?> deleteByCompany(
            @PathVariable Long id
    ) {
        return companyDnisService.deleteDnisByCompany(id);
    }

    /**
     * 내선 번호 voice gateway normal 내선 번호 생성 조회 config manager tbl_company_dnis 레코드 조회
     */
    @GetMapping("/list")
    public ResponseEntity<?> findAll(@RequestBody DaoCompanyDnis req) {
        log.info("## req: {}",req);
        return companyDnisService.findAll(req);
    }

    /* tbl_company_dnis - find by id */
    @GetMapping(value="/find/{id}")
    public ResponseEntity<?> findById(@PathVariable(name="id") Long fkCompanyDnis) {
        log.info("## findById fkCompanyDnis: {}",fkCompanyDnis);
        Optional<DaoCompanyDnis> vo = repoCompanyDnis.findById(fkCompanyDnis);
        if(vo.isPresent()){
            return ResponseEntity.ok(ResponseApi.success("dnis found",vo));
        }else{
            return ResponseEntity.ok(ResponseApi.fail("dnis not found",null));
        }
    }

    /* tbl_company_dnis - find by fkcompany */
    @GetMapping(value="/find/fkcompany/{id}")
    public ResponseEntity<?> findByFkCompany(@PathVariable(name="id") Long fkCompay) {
        log.info("## findByFkCompany fkCompay: {}",fkCompay);
        List<DaoCompanyDnis> data = repoCompanyDnis.findByFkCompanyOrderByPkCompanyDnisDesc(fkCompay);
        if(ObjectUtils.isEmpty(data) == false){
            return ResponseEntity.ok(ResponseApi.success("dnis found",data));
        }else{
            return ResponseEntity.ok(ResponseApi.fail("dnis not found",null));
        }
    }

    /* voice gateway API - number plan - TEST */
    @PostMapping(value="/numberPlan",consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> requestNumberPlan(@RequestBody ResVgwNumberPlans req) {
        log.info("## requestNumberPlan req: {}",req);
        return companyDnisService.requestNumberPlan(req);
    }

    /* voice gateway에 기존의 070 번호 또는 내선 번호의 목록을 보여준다 - TEST */
    @PostMapping(value="/number",consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> requestNumber(@RequestBody ResVgwNumbers req) {
        log.info("## requestNumber req: {}",req);
        return companyDnisService.requestNumber(req);
    }

    /*
     * 번호 조회 config manager tbl_company_dnis 레코드 조회. 중복번호면 true, 없으면 false 반환
     */
    @GetMapping(value="/check",consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkForDnis(@RequestBody DaoCompanyDnis req) {
        ResponseEntity<?> res = ResponseEntity.ok(null);
        Map<String, Object> mapArg = new HashMap<>();
        mapArg.put("check", companyDnisService.checkForDnis(req));
        log.info("## check req: {}",mapArg);
        res = ResponseEntity.ok(ResponseApi.success("dnis check", mapArg));
        return res;
    }

    /**
     * 고객사 발신번호 리스트 조회
     * 캠페인에서 사용할 목적으로 생성했으며 기존 dnis의 객체가 스네이크 케이스이므로 카멜 케이스로 새로 생성
     * @param fkCompany
     * @return
     */
    @GetMapping("/companies/{companySeq}")
    public ResponseEntity<ResponseApi> findDnisList(
            @PathVariable(value = "companySeq") Long fkCompany,
            @RequestParam(value = "category", defaultValue = "workcenter") String useCategory
    ) {
        return companyDnisService.findDnisList(fkCompany, useCategory);
    }
}
