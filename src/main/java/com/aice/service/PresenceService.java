package com.aice.service;

import com.aice.dao.ResponseApi;
import com.aice.dao.presence.DaoPresence;
import com.aice.repo.RepoPresence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class PresenceService {

    @Autowired
    RepoPresence repoPresence;

    /**
     * tbl_ai_conf_work의 enable_yn을 조회 한다.
     * 2024-05-16 기준 contact_ai_yn 필드가 추가되면서 사용하지 않는다.
     * @param seqCompany
     * @param aiStaffSeq
     * @return
     */
    @Deprecated
    public ResponseEntity<ResponseApi> findAiWorkStatus(
            Long seqCompany
            ,Long aiStaffSeq
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            DaoPresence req = new DaoPresence();
            req.setSeqCompany(seqCompany);
            req.setSeqStaffAi(aiStaffSeq);

            DaoPresence res = repoPresence.findAiWorkStatus(req);
            map.put("activationYn", res.getEnableYn());
            return ResponseEntity.ok(ResponseApi.success("AI work status retrieved successfully.", map));
        } catch (Exception e) {
            log.error("Failed to retrieve AI work status", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Failed to retrieve.", e.getMessage()));
        }
    }

    /**
     * tbl_ai_conf_work의 enable_yn을 수정 한다.
     * 2024-05-16 기준 contact_ai_yn 필드가 추가되면서 사용하지 않는다.
     * @param seqCompany
     * @param aiStaffSeq
     * @param req
     * @return
     */
    @Deprecated
    public ResponseEntity<ResponseApi> setAiWorkStatus(
            Long seqCompany
            ,Long aiStaffSeq
            ,DaoPresence req
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            req.setSeqCompany(seqCompany);
            req.setSeqStaffAi(aiStaffSeq);

            int res = repoPresence.updateAiWorkStatus(req);
            map.put("updateCnt", res);
            return ResponseEntity.ok(ResponseApi.success("AI work status updated successfully.", map));
        } catch (Exception e) {
            log.error("Update failed due to internal error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Update failed due to internal error.", e.getMessage()));
        }
    }

    /**
     * tbl_member_res_status에 데이터를 추가 한다.
     * 2024-05-16 기준 Account Manager에서 생성하므로 사용하지는 않는다.
     * @param seqCompany
     * @param aiStaffSeq
     * @param req
     * @return
     */
    @Deprecated
    public ResponseEntity<ResponseApi> addAiWorkStatus(
            Long seqCompany
            ,Long aiStaffSeq
            ,DaoPresence req
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            req.setSeqCompany(seqCompany);
            req.setSeqMember(aiStaffSeq);

            int res = repoPresence.insertMemberStatus(req);
            map.put("insertCnt", res);
            return ResponseEntity.ok(ResponseApi.success("Member status added successfully.", map));
        } catch (Exception e) {
            log.error("Add failed due to internal error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Add failed due to internal error.", e.getMessage()));
        }
    }

    /**
     * tbl_member_res_status의 res_status를 조회 한다.
     * 2024-05-16 기준 손비서의 경우 appmode 조회 api를 이용해야 하므로 손비서에선 사용해선 안된다. 현 기준 손비서에서 아직 호출되고 있다.
     * @param seqCompany
     * @param solutionType
     * @return
     */
    public ResponseEntity<ResponseApi> findMemberStatus(
            Long seqCompany
            ,String solutionType
    ) {
        try {
            DaoPresence req = new DaoPresence();
            req.setSeqCompany(seqCompany);
            if (solutionType == null) {
                req.setSolutionType("B14"); // 손비서에서 먼저 사용중인 API로 기본 값 손비서로 함
            } else {
                req.setSolutionType(solutionType);
            }

            DaoPresence res = repoPresence.findMemberStatus(req);
            if (res == null) {
                return ResponseEntity.ok(ResponseApi.fail("no data", null));
            }
            return ResponseEntity.ok(ResponseApi.success("Member status retrieved successfully.", res));
        } catch (Exception e) {
            log.error("Update failed due to internal error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Failed to retrieve.", e.getMessage()));
        }
    }

    /**
     * tbl_member_res_status의 res_status, disp_mode정보를 수정한다
     * 2024-05-16 기준 손비서의 경우 appmode 수정 api를 이용해야 하므로 손비서에선 사용해선 안된다.
     * @param seqCompany
     * @param req
     * @return
     */
    public ResponseEntity<ResponseApi> setMemberStatus(
            Long seqCompany
            ,DaoPresence req
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            req.setSeqCompany(seqCompany);

            int res = repoPresence.updateMemberStatus(req);
            map.put("updateCnt", res);
            return ResponseEntity.ok(ResponseApi.success("Member status updated successfully.", map));
        } catch (Exception e) {
            log.error("Update failed due to internal error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Update failed due to internal error.", e.getMessage()));
        }
    }

    /**
     * tbl_company_staff의 call_fwd_yn을 조회한다. (착신전환 설정 여부)
     * 2024-05-16 기준 손비서에서만 사용한다.
     * 급하게 만든다고 여기다 만들었지만 다른 클래스로 옮길 예정
     * @param seqCompany
     * @return
     */
    public ResponseEntity<ResponseApi> findCallForwardStatus(
            Long seqCompany
    ) {
        try {
            DaoPresence req = new DaoPresence();
            req.setSeqCompany(seqCompany);

            DaoPresence res = repoPresence.findCallForwardStatus(req);
            return ResponseEntity.ok(ResponseApi.success("Call forward retrieved successfully.", res));
        } catch (Exception e) {
            log.error("Failed to retrieve", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Failed to retrieve.", e.getMessage()));
        }
    }

    /**
     * tbl_company_staff의 call_fwd_yn을 수정한다. (착신전환 설정 여부)
     * 2024-05-16 기준 손비서에서만 사용한다.
     * 급하게 만든다고 여기다 만들었지만 다른 클래스로 옮길 예정
     * @param seqCompany
     * @param req
     * @return
     */
    public ResponseEntity<ResponseApi> setCallForwardStatus(
            Long seqCompany
            ,DaoPresence req
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            req.setSeqCompany(seqCompany);

            int res = repoPresence.updateCallForwardStatus(req);
            map.put("updateCnt", res);
            return ResponseEntity.ok(ResponseApi.success("Call forward updated successfully.", map));
        } catch (Exception e) {
            log.error("Update failed due to internal error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Update failed due to internal error.", e.getMessage()));
        }
    }
}
