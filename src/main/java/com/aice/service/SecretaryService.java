package com.aice.service;

import com.aice.dao.DaoSecretary;
import com.aice.dao.ResponseApi;
import com.aice.dao.extension.DaoExtension;
import com.aice.dao.extension.DaoExtensionOb;
import com.aice.dao.secretary.SecretaryDao;
import com.aice.dto.DtoSecretary;
import com.aice.repo.RepoExtension;
import com.aice.repo.RepoSecretary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SecretaryService {
    @Autowired
    RepoSecretary repoSecretary;

    @Autowired
    RepoExtension repoExtension;

    @Autowired
    ExtensionService extensionService;

    /**
     * 손비서 자동 자리비움 회사직원들 조회
     * 2024-05-16 기준 사용하지 않는다.
     * @param fkCompany
     * @return
     */
    @Deprecated
    public ResponseEntity<?> autoResponseList(Long fkCompany) {
        SecretaryDao req = new SecretaryDao();
        req.setFkCompany(fkCompany);

        log.info("Secretary auto response list request: {}", req.toJsonTrim());
        try {
            List<SecretaryDao> listItem = repoSecretary.selectAutoResponse(req);
            if(listItem.isEmpty()) {
                log.warn("Auto response list failed - Not exist for: {}", req.toJsonTrim());
                return ResponseEntity.ok(ResponseApi.fail("Auto response does not exist.", req));
            }

            log.info("Successfully list auto response for: {}", req.toJsonTrim());
            return ResponseEntity.ok(ResponseApi.success("Successfully get auto response.", listItem));
        } catch (Exception e) {
            log.error("Failed to list auto response for: {}", req.toJsonTrim(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Failed to list auto response.", e.getMessage()));
        }
    }

    /**
     * 손비서 자동 자리비움 조회
     * tbl_company_staff의 auto_response_yn를 조회한다.
     * 2024-05-16 기준 사용하지 않는다.
     * @param fkCompany
     * @param pkCompanyStaff
     * @return
     */
    @Deprecated
    public ResponseEntity<?> getAutoResponse(Long fkCompany, Long pkCompanyStaff) {
        SecretaryDao req = new SecretaryDao();
        req.setFkCompany(fkCompany);
        req.setPkCompanyStaff(pkCompanyStaff);

        log.info("Secretary auto response get request: {}", req.toJsonTrim());
        try {
            List<SecretaryDao> listItem = repoSecretary.selectAutoResponse(req);
            if(listItem.isEmpty()) {
                log.warn("Get auto response failed - Not exist for: {}", req.toJsonTrim());
                return ResponseEntity.ok(ResponseApi.fail("Auto response does not exist.", req));
            }

            log.info("Successfully get auto response for: {}", req.toJsonTrim());
            return ResponseEntity.ok(ResponseApi.success("Successfully get auto response.", listItem));
        } catch (Exception e) {
            log.error("Failed to get auto response for: {}", req.toJsonTrim(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Failed to get auto response.", e.getMessage()));
        }
    }

    /** 손비서 자동 자리비움 검색 조회 */
    @Deprecated
    public ResponseEntity<?> searchAutoResponse(
            Long fkCompany,
            Long pkCompanyStaff,
            String fdCompanyName,
            String fdStaffName,
            String fdStaffId,
            String autoResponseYn
    ) {
        SecretaryDao req = new SecretaryDao();
        req.setFkCompany(fkCompany);
        req.setPkCompanyStaff(pkCompanyStaff);
        req.setFdCompanyName(fdCompanyName);
        req.setFdStaffName(fdStaffName);
        req.setFdStaffId(fdStaffId);
        req.setAutoResponseYn(autoResponseYn);

        log.info("Secretary auto response search request: {}", req.toJsonTrim());
        try {
            List<SecretaryDao> listItem = repoSecretary.searchAutoResponse(req);
            if(listItem.isEmpty()) {
                log.warn("Auto response search failed - Not exist for: {}", req.toJsonTrim());
                return ResponseEntity.ok(ResponseApi.fail("Auto response does not exist.", req));
            }

            log.info("Successfully search auto response for: {}", req.toJsonTrim());
            return ResponseEntity.ok(ResponseApi.success("Successfully search auto response.", listItem));
        } catch (Exception e) {
            log.error("Failed to search auto response for: {}", req.toJsonTrim(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Failed to search auto response.", e.getMessage()));
        }
    }

    /** 손비서 자동 자리비움 모드 활성화 */
    @Deprecated
    public ResponseEntity<?> activeAutoResponse(Long fkCompany, Long pkCompanyStaff) {
        SecretaryDao req = new SecretaryDao();
        req.setAutoResponseYn("Y");
        req.setFkCompany(fkCompany);
        req.setPkCompanyStaff(pkCompanyStaff);

        log.info("Secretary auto response active request: {}", req.toJsonTrim());
        try {
            List<SecretaryDao> listItem = repoSecretary.selectAutoResponse(req);
            if(listItem.get(0).getAutoResponseYn().equals("Y")) {
                log.warn("Auto response active failed - already activated for: {}", req.toJsonTrim());
                return ResponseEntity.ok(ResponseApi.fail("Auto response already activated.", req));
            }

            repoSecretary.updateAutoResponse(req);

            log.info("Successfully actived auto response for: {}", req.toJsonTrim());
            return ResponseEntity.ok(ResponseApi.success("Successfully actived auto response.", req));
        } catch (Exception e) {
            log.error("Failed to active auto response for: {}", req.toJsonTrim(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Failed to active auto response.", e.getMessage()));
        }
    }

    /** 손비서 자동 자리비움 모드 활성화 */
    @Deprecated
    public ResponseEntity<?> inactiveAutoResponse(Long fkCompany, Long pkCompanyStaff) {
        SecretaryDao req = new SecretaryDao();
        req.setAutoResponseYn("N");
        req.setFkCompany(fkCompany);
        req.setPkCompanyStaff(pkCompanyStaff);

        log.info("Secretary auto response inactive request: {}", req.toJsonTrim());
        try {
            List<SecretaryDao> listItem = repoSecretary.selectAutoResponse(req);
            if(listItem.get(0).getAutoResponseYn().equals("N")) {
                log.warn("Auto response inactive failed - already inactive for: {}", req.toJsonTrim());
                return ResponseEntity.ok(ResponseApi.fail("Auto response already inactive.", req));
            }

            repoSecretary.updateAutoResponse(req);

            log.info("Successfully inactive auto response for: {}", req.toJsonTrim());
            return ResponseEntity.ok(ResponseApi.success("Successfully inactive auto response.", req));
        } catch (Exception e) {
            log.error("Failed to inactive auto response for: {}", req.toJsonTrim(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Failed to inactive auto response.", e.getMessage()));
        }
    }

    @Deprecated
    public ResponseEntity<ResponseApi> updateResStatus(
            Long fkCompany
            ,Long pkCompanyStaff
            ,SecretaryDao req
    ) {
        try {
            req.setFkCompany(fkCompany);
            req.setPkCompanyStaff(pkCompanyStaff);

            repoSecretary.updateResStatus(req);
            if (req.getResponseStatusCode().equals("A1207")) {
                req.setAutoResponseYn("N");
                req.setEnableYn("N");
            } else {
                req.setAutoResponseYn("Y");
                req.setEnableYn("Y");
            }
            repoSecretary.updateAutoResponse(req);
            repoSecretary.updateAiWorkEnable(req);

            return ResponseEntity.ok(ResponseApi.success("Secretary settings updated successfully", null));
        } catch (Exception e) {
            log.error("Failed to update secretary settings", e); // 로깅 추가
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Update failed", e.getMessage()));
        }
    }

    /**
     * 손비서 앱모드 조회
     * @param seqMember
     * @return
     */
    public ResponseEntity<ResponseApi> getAppMode(
            Long seqMember
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            DtoSecretary dtoSecretary = repoSecretary.findWorkAndStatusBySeqMember(seqMember);
            /**
             * 다른서비스 가입자 appMode값이 존재하지 않으면 insert
             * 현재는 손비서 가입시 appMode를 accountManager에서 생성하고 있기에 다른서비스에서 가입한 사용자는 appMode가 존재하지 않는다.
             * 임시 코드로 다른 서비스로 가입한 사용자의 경우 어떻게 생성할지 확정 되면 수정이 필요하다.
             */
            if (dtoSecretary == null) {
                dtoSecretary = repoSecretary.findAppModeBySeqMember(seqMember);
                if (dtoSecretary != null) {
                    map.put("appMode", dtoSecretary.getDispMode());
                    return ResponseEntity.ok(ResponseApi.success("getAppMode Success", map));
                }
                SecretaryDao masterInfo = repoSecretary.findMasterByMember(seqMember);
                if (masterInfo == null) {
                    return ResponseEntity.ok(ResponseApi.fail("Master info not found for the member.", null));
                }
                if (!"B13".equals(masterInfo.getSolutionType())) {
                    repoSecretary.insertMemberStatus(seqMember);
                    dtoSecretary = repoSecretary.findAppModeBySeqMember(seqMember);
                    if (dtoSecretary != null) {
                        map.put("appMode", dtoSecretary.getDispMode());
                        return ResponseEntity.ok(ResponseApi.success("getAppMode Success", map));
                    } else {
                        return ResponseEntity.ok(ResponseApi.fail("Failed to retrieve app mode after insertion.", null));
                    }
                } else {
                    return ResponseEntity.ok(ResponseApi.fail("studio subscriber.", null));
                }
            }
//            String contactAiyn = dtoSecretary.getContactAiYn();
//            String loggingYn = dtoSecretary.getLoggingYn();
//            String resStatus = dtoSecretary.getResStatus();

            map.put("appMode", dtoSecretary.getDispMode());
            return ResponseEntity.ok(ResponseApi.success("getAppMode Success", map));
        } catch (Exception e) {
            log.error("Failed to getAppMode", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("getAppMode failed", e.getMessage()));
        }
    }

    public ResponseEntity<ResponseApi> setAppMode(
            DaoSecretary req
    ) {
        Map<String, Object> map = new HashMap<>();
        DaoSecretary nextAppMode = new DaoSecretary();
        Long seqMember = req.getSeqMember();
        String appMode = req.getAppMode();
        nextAppMode.setSeqMember(seqMember);
        try {
            DtoSecretary prevAppMode = repoSecretary.findWorkAndStatusBySeqMember(seqMember);
            DtoSecretary isMobileYn = repoSecretary.findMobileMainYn(seqMember);
            String prevContactAiYn = prevAppMode.getContactAiYn();
            String prevLoggingYn = prevAppMode.getLoggingYn();
            String prevResStatus = prevAppMode.getResStatus();
            String prevDispMode = prevAppMode.getDispMode();

            map.put("prevAppMode", prevDispMode);


            switch (appMode) {
                case "AIHANDY_MODE_ONLINE":
                    nextAppMode.setResStatus("STATUS_ONLINE");
                    nextAppMode.setDispMode("AIHANDY_MODE_ONLINE");
                    nextAppMode.setContactAiYn("Y");
                    nextAppMode.setLoggingYn("Y");
                    nextAppMode.setCallFwdYn("Y");
                    break;
                case "AIHANDY_MODE_REST":
                    nextAppMode.setResStatus("STATUS_REST");
                    nextAppMode.setDispMode("AIHANDY_MODE_REST");
                    nextAppMode.setContactAiYn("N");
                    nextAppMode.setLoggingYn("N");
                    nextAppMode.setCallFwdYn("N");
                    break;
                case "AIHANDY_MODE_MEETING":
                    nextAppMode.setResStatus("STATUS_MEETING");
                    nextAppMode.setDispMode("AIHANDY_MODE_MEETING");
                    nextAppMode.setContactAiYn("Y");
                    nextAppMode.setLoggingYn("Y");
                    nextAppMode.setCallFwdYn("Y");
                    break;
                case "AIHANDY_MODE_VACATION":
                    nextAppMode.setResStatus("STATUS_VACATION");
                    nextAppMode.setDispMode("AIHANDY_MODE_VACATION");
                    nextAppMode.setContactAiYn("Y");
                    nextAppMode.setLoggingYn("Y");
                    nextAppMode.setCallFwdYn("Y");
                    break;
                case "AIHANDY_MODE_DRIVE":
                    nextAppMode.setResStatus("STATUS_DRIVE");
                    nextAppMode.setDispMode("AIHANDY_MODE_DRIVE");
                    nextAppMode.setContactAiYn("Y");
                    nextAppMode.setLoggingYn("Y");
                    nextAppMode.setCallFwdYn("Y");
                    break;
                case "AIHANDY_MODE_TRIP":
                    nextAppMode.setResStatus("STATUS_TRIP");
                    nextAppMode.setDispMode("AIHANDY_MODE_TRIP");
                    nextAppMode.setContactAiYn("Y");
                    nextAppMode.setLoggingYn("Y");
                    nextAppMode.setCallFwdYn("Y");
                    break;
                case "AIHANDY_MODE_LISTEN":
                    nextAppMode.setResStatus("STATUS_ONLINE");
                    nextAppMode.setDispMode("AIHANDY_MODE_LISTEN");
                    nextAppMode.setContactAiYn("N");
                    nextAppMode.setLoggingYn("Y");
                    nextAppMode.setCallFwdYn("Y");
                    break;
                case "AIHANDY_MODE_OFFLINE":
                    nextAppMode.setResStatus("STATUS_OFFLINE");
                    nextAppMode.setDispMode("AIHANDY_MODE_OFFLINE");
                    nextAppMode.setContactAiYn("N");
                    nextAppMode.setLoggingYn("N");
                    break;
                default:
                    return ResponseEntity.ok(ResponseApi.fail("Not found appMode", appMode));
            }
            if ("N".equals(isMobileYn.getMobileMainYn())) {
                nextAppMode.setCallFwdYn("N");
            }

            repoSecretary.updateWorkInfo(nextAppMode);
            repoSecretary.updateMemberResStatus(nextAppMode);
            repoSecretary.updateCallFwdYn(nextAppMode);
            map.put("nextAppMode", appMode);

            return ResponseEntity.ok(ResponseApi.success("setAppMode Success", map));
        } catch (Exception e) {
            log.error("Failed to getAppMode", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("setAppMode failed", e.getMessage()));
        }
    }

    /**
     * 2024-05-16 발신번호 승인정보 조회 구현.
     * 급하게 만든 것으로 리팩토링 반드시 필요
     * @param seqMember
     * @return
     */
    public ResponseEntity<ResponseApi> findExtOb(
            Long seqMember
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            DaoExtensionOb extObData = repoExtension.findExtObByMember(seqMember);

            String agreeYn = extObData.getAgreeYn();
            map.put("obAgree", agreeYn);

            return ResponseEntity.ok(ResponseApi.success("Secretary findExtOb successfully", map));
        } catch (Exception e) {
            log.error("Failed to findExtOb secretary", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Secretary findExtOb failed", e.getMessage()));
        }
    }

    /**
     * 2024-05-16 발신번호 승인정보 수정 구현.
     * 급하게 만든 것으로 리팩토링 반드시 필요
     * @param req
     * @return
     */
    public ResponseEntity<ResponseApi> setExtOb(
            DaoExtensionOb req
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            int updateCnt = repoExtension.updateExtObByMember(req);
            return ResponseEntity.ok(ResponseApi.success("Secretary setExtOb successfully", updateCnt));
        } catch (Exception e) {
            log.error("Failed to setExtOb secretary", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Secretary setExtOb failed", e.getMessage()));
        }
    }


    /**
     * 손비서 전용번호 조회
     * @param seqCompany
     * @param seqStaffAi
     * @return
     */
    public ResponseEntity<ResponseApi> findExtensions(
                    Long seqCompany
                    ,Long seqStaffAi
    ) {
        ResponseEntity<ResponseApi> res = ResponseEntity.ok(null);
        DaoExtension daoExtension = new DaoExtension();
        daoExtension.setFkCompany(seqCompany);
        daoExtension.setFkCompanyStaffAi(seqStaffAi);
        try {
            DaoExtension resData = repoExtension.findOneExtension(daoExtension);
            if (resData.getExtNum() != null) {
                res = ResponseEntity.ok(ResponseApi.success("findExtensions successfully.", resData));
            } else {
                res = ResponseEntity.ok(ResponseApi.fail("Not found extensions.", null));
            }

        } catch (Exception e) {
            log.error("Error findExtensions. error:{}", e);
            res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error findExtensions.", e.getMessage()));
        }
        return res;
    }

    /**
     * 전화번호 사용여부 변경
     * 급하게 만든것으로 반드시 수정 필요
     * @param req
     * @return
     */
    public ResponseEntity<ResponseApi> setMobileMainYn(
            Long seqMember
            ,DaoExtension req
    ) {
        Map<String, Object> map = new HashMap<>();
        req.setSeqMember(seqMember);
        try {
            Integer updateCnt = repoExtension.updateMobileMainYn(req);
            map.put("updateCnt", updateCnt);
            return ResponseEntity.ok(ResponseApi.success("Successfully", map));
        } catch (Exception e) {
            log.error("Error for: {}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("setMobileMainYn failed", e.getMessage()));
        }
    }

    /**
     * 발신번호 조회
     * @param seqMember
     * @return
     */
    public ResponseEntity<ResponseApi> findOutboundNumber(Long seqMember) {
        ResponseEntity<ResponseApi> res;
        Map<String, Object> map = new HashMap<>();
        try {
            DaoExtension data = repoExtension.getObNumberByMemberOrCompany(seqMember);
            if (data == null) {
                log.error("Outbound Number not found for member: {}", seqMember);
                res = ResponseEntity.ok(ResponseApi.fail("Not found outbound number.", null));
            } else {
                map.put("outboundNumber", data.getOriginDnis());
                res = ResponseEntity.ok(ResponseApi.success("findOutboundNumber successfully.", map));
            }
        } catch (Exception e) {
            log.error("Error findOutboundNumber. error: {}", e.getMessage(), e);
            res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Error findOutboundNumber.", e.getMessage()));
        }
        return res;
    }

    /**
     * 발신번호 수정
     * @param seqMember
     * @param req
     * @return
     */
    public ResponseEntity<ResponseApi> setOutboundNumber(Long seqMember, DaoExtension req) {
        Map<String, Object> map = new HashMap<>();
        try {
            Integer isExtension = repoExtension.findExtensionByCompanyAndObNumber(seqMember, req.getOutboundNumber());
            if (isExtension != 1) {
                throw new Exception("Not found extension.");
            }

            Integer updateCnt = repoExtension.updateObNumber(seqMember, req.getOutboundNumber());
            DaoExtension data = repoExtension.getObNumberByMemberOrCompany(seqMember);
            if (data == null) {
                log.error("No outbound number data found for member: {}", seqMember);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseApi.fail("No outbound number data found.", null));
            }
            req.setDialNum(data.getDialNum());
            req.setFullDnis(data.getFullDnis());
            req.setOriginDnis(data.getOriginDnis());
            extensionService.setExtension(req);
            map.put("updateCnt", updateCnt);
            return ResponseEntity.ok(ResponseApi.success("Successfully updated outbound number.", map));
        } catch (Exception e) {
            log.error("Error setting outbound number for member {}: {}", seqMember, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Failed to set outbound number.", e.getMessage()));
        }
    }
}
