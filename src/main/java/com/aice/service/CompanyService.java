package com.aice.service;

import com.aice.dao.DaoAiWork;
import com.aice.dao.DaoCompanyStaff;
import com.aice.dao.ResponseApi;
import com.aice.dto.*;
import com.aice.repo.RepoCompanyAi;
import com.aice.repo.RepoCompany;
import com.aice.repo.RepoCompanyStaff;
import com.aice.repo.RepoExtension;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class CompanyService {
    @Autowired
    RepoCompany repoCompany;

    @Autowired
    RepoCompanyAi repoCompanyAi;

    @Autowired
    RepoCompanyStaff repoCompanyStaff;

    @Autowired
    RepoExtension repoExtension;

    /**
     * AI직원 생성 기본정보 INSERT
     * @param companySeq
     * @param aiStaffSeq
     * @return
     */
    public ResponseEntity<?> addAiWorks(
            Long companySeq
            ,Long aiStaffSeq
    ) {
        DaoAiWork daoAiWork = new DaoAiWork();
        daoAiWork.setBotStatus("COMPLETE");
        daoAiWork.setFkCompany(companySeq);
        daoAiWork.setFkCompanyStaffAi(aiStaffSeq);

        List<DaoAiWork> optionalInfo = repoCompanyAi.findAiWorkByStaff(daoAiWork);
        if (!optionalInfo.isEmpty()) {
            log.warn("Already exists: {}", aiStaffSeq);
            return ResponseEntity.ok(ResponseApi.fail("Already exists.", aiStaffSeq));
        }

        try {
            repoCompanyAi.insertWork(daoAiWork);
            repoCompanyAi.insertWorkTask(daoAiWork);
            repoCompanyAi.insertDayOn(daoAiWork);
            repoCompanyAi.insertIntro(daoAiWork);
            log.info("Ai work added successfully. req: {}", daoAiWork.toJsonTrim());
            return ResponseEntity.ok(ResponseApi.success("Ai work added successfully.", daoAiWork));
        } catch (Exception e) {
            log.error("Error adding ai work. req: {}, error:{}", daoAiWork.toJsonTrim(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error adding ai work.", e.getMessage()));
        }
    }

    /** Ai직원 list 조회 */
    public ResponseEntity<?> getAiInfoList(
            Long companySeq
    ) {
        DaoCompanyStaff daoCompanyStaff = new DaoCompanyStaff();
        daoCompanyStaff.setFkCompany(companySeq);
        daoCompanyStaff.setFdStaffAiYn("Y");

        try {
            List<DtoAiInfo> aiInfos = repoCompanyAi.listAisByCompany(daoCompanyStaff);
            if (!aiInfos.isEmpty()) {
                AiInfoResponse res = new AiInfoResponse(aiInfos, aiInfos.size());
                log.info("getAiInfoList successfully. req: {}, res: {}", daoCompanyStaff.toJsonTrim(), res);
                return ResponseEntity.ok(ResponseApi.success("getAiInfoList successfully.", res));
            } else {
                return ResponseEntity.ok(ResponseApi.fail("no data", null));
            }

        } catch (Exception e) {
            log.error("Error getAiInfoList. req: {}, error:{}", daoCompanyStaff.toJsonTrim(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error getAiInfoList.", e.getMessage()));
        }
    }

    /**
     * Ai Staff 정보 수정
     */
    public ResponseEntity<?> setAiInfo(
            Long companySeq
            ,Long aiStaffSeq
            ,DaoCompanyStaff req
    ) {
        req.setFkCompany(companySeq);
        req.setPkCompanyStaff(aiStaffSeq);

        Optional<DaoCompanyStaff> optionalStaff = repoCompanyStaff.findByStaff(aiStaffSeq);
        if (optionalStaff.isEmpty()) {
            log.warn("No staff seq. req: {}", req.toJsonTrim());
            return ResponseEntity.ok(ResponseApi.fail("No staff seq.", aiStaffSeq));
        }
        log.info("data. qStatus:{}, qBotStatus:{}", req.getQuickStartStatus(), req.getQuickStartBotStatus());
        try {
            repoCompanyAi.updateAi(req);
            log.info("setAiInfo successfully. req: {}, res: {}", req.toJsonTrim());
            return ResponseEntity.ok(ResponseApi.success("setAiInfo successfully.", null));
        } catch (Exception e) {
            log.error("Error setAiInfo. req: {}, error:{}", req.toJsonTrim(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error setAiInfo.", e.getMessage()));
        }
    }


    /**
     * AI리스트 조회 (AI팀 요청)
     * @param solutionType
     * @param fDate
     * @param tDate
     * @return
     */
    public ResponseEntity<?> getAiList(
            String solutionType,
            String fDate,
            String tDate
    ) {
        ResponseEntity<?> res = ResponseEntity.ok(null);
        DaoCompanyStaff daoCompanyStaff = new DaoCompanyStaff();
        if (solutionType != null) {
            daoCompanyStaff.setSolutionType(solutionType);
        }

        // 수정예정
        if (fDate != null && tDate != null) {
            daoCompanyStaff.setFromDate(fDate);
            daoCompanyStaff.setToDate(tDate);
        }

        try {
            List<AiRoleData> aiList = repoCompanyAi.getAiList(daoCompanyStaff);
            res = ResponseEntity.ok(ResponseApi.success("getAiList successfully.", aiList));
        } catch (Exception e) {
            log.error("Error getAisList. error:{}", e);
            res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error getAisList.", e.getMessage()));
        }
        return res;
    }

    /**
     * AI리스트 조회2 (AI팀 요청)
     * @param req
     * @return
     */
    public ResponseEntity<?> getAiList2(
            DtoAiInfoRequest req
    ) {
        ResponseEntity<?> res = ResponseEntity.ok(null);

        try {
            List<AiRoleData> aiList = repoCompanyAi.getAiList2(req);
            AiRoleResponse aiRoleResponse = new AiRoleResponse();

            for (AiRoleData data : aiList) {
                aiRoleResponse.addAiRoleData(data.getRole(), data);
            }
            log.info("getAiList data: {}", aiRoleResponse);
            res = ResponseEntity.ok(ResponseApi.success("getAiList successfully.", aiRoleResponse));
        } catch (Exception e) {
            log.error("Error getAisList. error:{}", e);
            res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error getAisList.", e.getMessage()));
        }
        return res;
    }

    public ResponseEntity<ResponseApi> findMembers(Long seqCompany) {
        ResponseEntity<ResponseApi> res = ResponseEntity.ok(null);
        Map<String, Object> map = new HashMap<>();
        try {
            DaoCompanyStaff seqMember = repoCompany.findMemberByCompanySeq(seqCompany);
            if (seqMember.getPkCompanyStaff() != null) {
                map.put("seqMember", seqMember.getPkCompanyStaff());
                res = ResponseEntity.ok(ResponseApi.success("findMembers successfully.", map));
            } else {
                res = ResponseEntity.ok(ResponseApi.fail("Not found member.", null));
            }

        } catch (Exception e) {
            log.error("Error getAisList. error:{}", e);
            res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error findMembers.", e.getMessage()));
        }
        return res;
    }
}
