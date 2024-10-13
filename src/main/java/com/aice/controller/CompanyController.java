package com.aice.controller;

import com.aice.dao.DaoCompanyStaff;
import com.aice.dao.ResponseApi;
import com.aice.dao.dnis.DaoCompanyDnis;
import com.aice.dto.DtoAiInfoRequest;
import com.aice.service.CompanyDnisService;
import com.aice.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/{apiVer}/companies")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyDnisService companyDnisService;

    /**
     * AI직원 기본정보 생성
     * tbl_ai_conf_work : 업무유형
     * tbl_ai_conf_work_task : 업무 세부유형
     * tbl_ai_conf_day_on : 업무시간
     * tbl_ai_conf_intro : 인사말 멘트
     * @param apiVer
     * @param companySeq
     * @param aiStaffSeq
     * @return
     */
    @PostMapping("/{companySeq}/ais/{aiStaffSeq}/works")
    public ResponseEntity<?> addAiWorks(
            @PathVariable String apiVer
            ,@PathVariable Long companySeq
            ,@PathVariable Long aiStaffSeq
    ) {
        return companyService.addAiWorks(companySeq, aiStaffSeq);
    }

    /**
     * Ai직원 list조회
     * @param apiVer
     * @param companySeq
     * @return
     */
    @GetMapping("/{companySeq}/ais")
    public ResponseEntity<?> getAiInfoList(
            @PathVariable String apiVer
            ,@PathVariable Long companySeq
    ) {
        return companyService.getAiInfoList(companySeq);
    }

    /**
     * Ai직원 정보 수정
     * @param apiVer
     * @param companySeq
     * @param aiStaffSeq
     * @param req
     * @return
     */
    @PatchMapping("/{companySeq}/ais/{aiStaffSeq}")
    public ResponseEntity<?> setAiInfo(
            @PathVariable String apiVer
            ,@PathVariable Long companySeq
            ,@PathVariable Long aiStaffSeq
            ,@RequestBody DaoCompanyStaff req
    ) {
        return companyService.setAiInfo(companySeq, aiStaffSeq, req);
    }

    /**
     * DNIS 정보수정
     * description. DaoCompanyDnis가 스네이크 케이스 이므로 Staff를 받음...
     * @param apiVer
     * @param dnis
     * @param req
     * @return
     */
    @PatchMapping(
            value="/{companySeq}/dnis/{dnis}",
            consumes= MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> patchCompanyDnis(
            @PathVariable String apiVer
            ,@PathVariable Long companySeq
            ,@PathVariable String dnis
            ,@RequestBody DaoCompanyStaff req
    ) {
        DaoCompanyDnis daoCompanyDnis = new DaoCompanyDnis();
        daoCompanyDnis.setNumberType("normal");
        daoCompanyDnis.setDnisType("normal");
        daoCompanyDnis.setFdDnis(dnis);
        daoCompanyDnis.setFkCompany(companySeq);
        daoCompanyDnis.setFkCompanyStaffAi(req.getAiStaffFrom());
        req.setFkCompany(companySeq);
        return companyDnisService.patchCompanyDnis(req, daoCompanyDnis);
    }

    /**
     * AI리스트 조회 (AI팀 요청)
     * @param solutionType
     * @param fDate
     * @param tDate
     * @return
     */
    @GetMapping(value = "/ais/list")
    public ResponseEntity<?> getAiList(
            @RequestParam(required=false,value="solutionType") String solutionType,
            @RequestParam(required=false,value="fDate") String fDate,
            @RequestParam(required=false,value="tDate") String tDate
    ) {
        return companyService.getAiList(
                solutionType,
                fDate,
                tDate
        );
    }

    /**
     * AI리스트 조회2 (AI팀 요청)
     * ex)
     * {
     *   "aiRole" :
     *   [
     *     "RECEPT","HANDS"
     *   ],
     *   "fromDate":"20231213",
     *   "toDate":"20240322"
     * }
     * @param req aiRole: HANDS, KIOSK, MANAGE, RESERV, RECEPT
     * @return
     */
    @PostMapping(
            value = "/ais",
            consumes= MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getAiList2(
            @PathVariable String apiVer,
            @RequestBody DtoAiInfoRequest req
    ) {
        return companyService.getAiList2(req);
    }

    @GetMapping("/{seqCompany}/members")
    public ResponseEntity<ResponseApi> findMembers(
            @PathVariable Long seqCompany
    ) {
        return companyService.findMembers(seqCompany);
    }

}
