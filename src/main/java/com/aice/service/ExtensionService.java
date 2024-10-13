package com.aice.service;

import java.util.*;

import com.aice.dao.DaoCompanyStaff;
import com.aice.dao.extension.DaoExtension;
import com.aice.dao.vgw.ResVgwNumberPlans;
import com.aice.repo.RepoExtension;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.aice.dao.DaoCompany;
import com.aice.dao.ResponseApi;
import com.aice.dao.vgw.ResVgwMain;
import com.aice.repo.RepoBizPhone;
import com.aice.repo.RepoCompany;
import com.aice.repo.RepoCompanyStaff;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException.FeignClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Service
public class ExtensionService {
    @Autowired
    VoiceGatewayServiceAPI voiceGatewayServiceAPI;
    @Autowired
    ServiceBizPhone serviceBizPhone;

    @Autowired CompanyDnisService companyDnisService;
    @Autowired
    RepoExtension repoExtension;
    @Autowired
    RepoCompany repoCompany;
    @Autowired
    RepoCompanyStaff repoCompanyStaff;

    @Autowired
    RepoBizPhone repoBizPhone;
    @Autowired
    ObjectMapper objectMapper;

    @Value(value = "5")
    String vgwId;
    String vgwAuthPwAppendic = "!#%&";

    /**
     * Extension 번호 생성
     */
    public ResponseEntity<?> addExtension(DaoExtension req) {
        if (req.getDnisType() == null) {
            req.setDnisType("aihandy");
            req.setFullDnis(null);
        }

        log.info("## addExtension request: {}", req.toJsonTrim());

        Optional<DaoCompanyStaff> optionalAiStaff = repoCompanyStaff.findAiStaffByCompanyAndStaff(req.getFkCompany(), req.getFkCompanyStaffAi());
        if (optionalAiStaff.isEmpty()) {
            log.warn("No ai staff seq. req: {}", req.toJsonTrim());
            return ResponseEntity.ok(ResponseApi.fail("No ai staff seq.", req.getFkCompanyStaffAi()));
        }

        /* 2024-05-17 중복조건 변경
        Optional<DaoExtension> optionalDial = repoExtension.findOneByDialNum(req);
        if (optionalDial.isPresent()) {
            log.error("Already exist", req.toJsonTrim());
            return ResponseEntity.ok(ResponseApi.fail("Already exist", req.getDialNum()));
        }
        */

        if (repoExtension.existsExtensionByDialNumAndExtNum(req).isPresent()) {
            log.warn("Already exist", req.toJsonTrim());
            return ResponseEntity.ok(ResponseApi.fail("Already exist", repoExtension.findOneExtension(req).getExtNum()));
        }

        /* 2024-05-09 TEST START */
        if (req.getFullDnis() == null) {
            ResVgwNumberPlans findDnisArg = new ResVgwNumberPlans();
            findDnisArg.setCommand("show");

            ResponseEntity<?> dnisRes = companyDnisService.requestNumberPlan(findDnisArg);

            if (dnisRes.getBody() == null) {
                throw new RuntimeException("Response body is null");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.convertValue(dnisRes.getBody(), Map.class);
            Optional<DaoExtension> optionalVgwNumber;
            Set<String> checkedNumbers = new HashSet<>();
            String availableNumber = null;

            if (responseMap != null && "success".equals(responseMap.get("status"))) {
                Map<String, Object> dataMap = (Map<String, Object>) responseMap.get("data");
                if (dataMap != null) {
                    List<Map<String, Object>> numberPlansList = (List<Map<String, Object>>) dataMap.get("numberPlans");
                    for (Map<String, Object> numberPlan : numberPlansList) {
                        if ("no".equalsIgnoreCase((String) numberPlan.get("inUse")) && req.getDnisType().equalsIgnoreCase((String) numberPlan.get("type"))) {
                            availableNumber = (String) numberPlan.get("number");
                            if (checkedNumbers.contains(availableNumber)) {
                                continue;
                            }
                            optionalVgwNumber = repoExtension.findDnis(availableNumber);
                            checkedNumbers.add(availableNumber);
                            if (optionalVgwNumber.isPresent()) {
                                continue;
                            }
                            req.setFullDnis(availableNumber);
                            req.setDnis(availableNumber.substring(3));
                            break;
                        }
                    }
                    if ((req.getDnis() == null && req.getFullDnis() == null)) {
                        return ResponseEntity.ok(ResponseApi.fail("No numbers left.", req));
                    }
                }
            }
        }
        /* TEST END */

        ObjectMapper mapper = new ObjectMapper();
        String companyName = null;
        Optional<DaoCompany> company = repoCompany.selectCompanyById(req.getFkCompany());
        if (company.isPresent()) {
            companyName = company.get().getFdCompanyId();
        }

        Map<String, Object> mapArg = new HashMap<>();
        mapArg.put("command", "add");
        mapArg.put("numberType", req.getDnisType());
        mapArg.put("forwardedNumber", req.getDialNum());
        mapArg.put("moh", "aihandy");
        mapArg.put("name", companyName);
        if (ObjectUtils.isEmpty(req.getVgwAuthPw())) {
            req.setVgwAuthPw(req.getDialNum() + vgwAuthPwAppendic);
        }
        mapArg.put("password", req.getVgwAuthPw());

        try {
            String strArgs = mapper.writeValueAsString(mapArg);
            log.info("## strArgs: {}", strArgs);

            ResponseEntity<ResVgwMain> resVoice = voiceGatewayServiceAPI.requestNumber(req.getFullDnis(), strArgs);
            ResVgwMain resData = resVoice.getBody();
            req.setDnis(resData.getDnis());
            req.setExtNum(resData.getExtension());

            repoExtension.createExtension(req);
            repoExtension.insertExtOb(req.getFkCompany(), req.getFullDnis());
            return ResponseEntity.ok(ResponseApi.success("Successfully added extension.", req));
        } catch (FeignClientException e) {
            log.error("Error during voice-gw API call for: {}", req, e);
            String errorMsg;
            try {
                errorMsg = objectMapper.writeValueAsString(e.contentUTF8());
            } catch (JsonProcessingException e1) {
                errorMsg = "Error processing FeignClientException";
            }
            return ResponseEntity.ok(ResponseApi.error("voice-gw error", errorMsg));

        } catch (Exception e) {
            log.error("Error while adding extension for: {}", req, e);
            return ResponseEntity.ok(ResponseApi.error("Failed to add extension.", e.getMessage()));
        }
    }

    /** Extension 특정번호 조회 */
    public ResponseEntity<?> getExtension(String extNum, String dnisType) {
        DaoExtension req = new DaoExtension();
        req.setExtNum(extNum);
        if (dnisType == null || "".equals(dnisType)) {
            req.setDnisType("aihandy");
        } else {
            req.setDnisType(dnisType);
        }

        if (!repoExtension.existsExtensionByDialNumAndExtNum(req).isPresent()) {
            log.warn("extension not found for request: {}", req);
            return ResponseEntity.ok(ResponseApi.fail("extension not found", req));
        }

        try {
            DaoExtension resData = repoExtension.findExtension(extNum);
            log.info("Successfully retrieved the extension for extNum: {}", extNum);
            return ResponseEntity.ok(ResponseApi.success("Successfully retrieved the extension.", resData));
        } catch (Exception e) {
            log.error("Error while retrieving extension for: {}", req, e);
            return ResponseEntity.ok(ResponseApi.error("Failed to retrieve extension.", e.getMessage()));
        }
    }

    /** Extension 번호 검색조회 */
    public ResponseEntity<?> searchExtension(
            Long pkConfDialExtAihandy,
            Long fkCompany,
            Long fkCompanyStaffAi,
            String dnis,
            String extNum,
            String dialNum,
            String fullDnis
    ) {
        DaoExtension req = new DaoExtension();
        req.setPkConfDialExtAihandy(pkConfDialExtAihandy);
        req.setFkCompany(fkCompany);
        req.setFkCompanyStaffAi(fkCompanyStaffAi);
        req.setDnis(dnis);
        req.setExtNum(extNum);
        req.setDialNum(dialNum);
        req.setFullDnis(fullDnis);

        try {
            List<DaoExtension> resData = repoExtension.findAllExtension(req);
            if (resData.isEmpty()) {
                log.warn("extension not found for request: {}", req);
                return ResponseEntity.ok(ResponseApi.fail("extension not found", req));
            }
            log.info("Successfully retrieved the extension for extNum: {}", extNum);
            return ResponseEntity.ok(ResponseApi.success("Successfully retrieved the extension.", resData));
        } catch (Exception e) {
            log.error("Error while retrieving extension for: {}", req, e);
            return ResponseEntity.ok(ResponseApi.error("Failed to retrieve extension.", e.getMessage()));
        }
    }

    /**
     * Extension 번호 수정
     * 손비서 발신번호 수정시 호출한다.
     * @param req
     * @return
     */
    public ResponseEntity<?> setExtension(DaoExtension req) {
        ResponseEntity<?> res;
        ObjectMapper mapper = new ObjectMapper();
        String strArgs = null;

        try {
            /* voice-gw api 요청 값 */
            Map<String, Object> mapArg = new HashMap<>();
            mapArg.put("command", "update");
            mapArg.put("numberType", "aihandy");
            mapArg.put("forwardedNumber", req.getDialNum());

            if (ObjectUtils.isEmpty(req.getOriginDnis()) == false) {
                mapArg.put("callerId", req.getOriginDnis());
            }
//            if (ObjectUtils.isEmpty(req.getBizPhoneNum()) == false) {
//                mapArg.put("callerId", req.getBizPhoneNum());
//            }

            if (ObjectUtils.isEmpty(req.getVgwAuthPw()) == true) {
                req.setVgwAuthPw(req.getDialNum() + vgwAuthPwAppendic);
            }
            mapArg.put("password", req.getVgwAuthPw());

            strArgs = mapper.writeValueAsString(mapArg);
            log.info("## strArgs: {}", strArgs);

            /* XXX: voice-gw api call */
            ResponseEntity<ResVgwMain> resVoice = voiceGatewayServiceAPI.requestNumber(req.getFullDnis(), strArgs);
            ResVgwMain resData = resVoice.getBody();
            res = ResponseEntity.ok(ResponseApi.success("dnis success", resData));

            /* XXX: tbl_company_dnis 레코드 수정 */
            /*Optional<DaoExtension> vo = repoExtension.findById(req.getPkConfDialExtAihandy());
            if (vo.isPresent()) {
                repoExtension.updateExtension(req);
                return ResponseEntity.ok(ResponseApi.success("dnis update", req));
            } else {
                return ResponseEntity.ok(ResponseApi.fail("dnis error", null));
            }*/

            return ResponseEntity.ok(ResponseApi.success("extension update", req));

        } catch (FeignClientException e) {
            log.error("Error during voice-gw API call for: {}", req, e);
            String errorMsg;
            try {
                errorMsg = objectMapper.writeValueAsString(e.contentUTF8());
            } catch (JsonProcessingException e1) {
                errorMsg = "Error processing FeignClientException";
            }
            return ResponseEntity.ok(ResponseApi.error("voice-gw error", errorMsg));
        } catch (Exception e) {
            log.error("Error while adding extension for: {}", req, e);
            res = ResponseEntity.ok(ResponseApi.error("dnis error", e.getMessage()));
        }
        return res;
    }

    /**
     * Extension 번호 제거
     */
    public ResponseEntity<?> removeExtension(String extNum) {
        DaoExtension req = new DaoExtension();
        req.setExtNum(extNum);

        if (!repoExtension.existsExtensionByDialNumAndExtNum(req).isPresent()) {
            log.warn("Extension not found for request: {}", req);
            return ResponseEntity.ok(ResponseApi.fail("Extension not found.", extNum));
        }

        DaoExtension data = repoExtension.findOneExtension(req);
        req.setDnisType("shared");
        req.setFullDnis(data.getFullDnis());
        req.setDialNum(data.getDialNum());

        Map<String, Object> mapArg = new HashMap<>();
        mapArg.put("command", "delete");
        mapArg.put("numberType", req.getDnisType());
        mapArg.put("forwardedNumber", req.getDialNum());

        if (ObjectUtils.isEmpty(req.getVgwAuthPw())) {
            req.setVgwAuthPw(req.getDialNum() + vgwAuthPwAppendic);
        }
        mapArg.put("password", req.getVgwAuthPw());

        try {
            String strArgs = new ObjectMapper().writeValueAsString(mapArg);
            log.info("## strArgs: {}", strArgs);

            voiceGatewayServiceAPI.requestNumber(req.getFullDnis(), strArgs);
            repoExtension.deleteExtension(req);

            log.info("Successfully deleted extension for: {}", req);
            return ResponseEntity.ok(ResponseApi.success("Successfully deleted extension.", extNum));

        } catch (FeignClientException e) {
            log.error("Error during voice-gw API call for: {}", req, e);
            String errorMsg;
            try {
                errorMsg = objectMapper.writeValueAsString(e.contentUTF8());
            } catch (JsonProcessingException e1) {
                errorMsg = "Error processing FeignClientException";
            }
            return ResponseEntity.ok(ResponseApi.error("Voice-gw error.", errorMsg));

        } catch (Exception e) {
            log.error("Error deleting extension for: {}", req, e);
            return ResponseEntity.ok(ResponseApi.error("Failed to delete extension.", e.getMessage()));
        }
    }

    /**
     * Extension 번호 제거 (회사 기준)
     */
    public ResponseEntity<?> deleteExtensionByCompany(Long id) {
        log.info("deleteExtensionByCompany id:{}", id);
        ResponseEntity<?> res = ResponseEntity.ok(null);
//        Optional<DaoCompany> daoCompany = repoCompany.selectCompanyById(id);

//        if (daoCompany.isEmpty()) {
//            return ResponseEntity.ok(ResponseApi.fail("No data company.", id));
//        }

//        if (!daoCompany.get().getSolutionType().equals("B14")) {
//            res = ResponseEntity.ok(ResponseApi.success("No son secretary user.", id));
//        }

        DaoExtension daoExtension = new DaoExtension();
        daoExtension.setFkCompany(id);

        List<DaoExtension> data = repoExtension.findAllExtension(daoExtension);

        if (data.isEmpty()) {
            res = ResponseEntity.ok(ResponseApi.fail("No data.", id));
        }

        for(DaoExtension req : data) {
            Map<String, Object> mapArg = new HashMap<>();
            mapArg.put("command", "delete");
            mapArg.put("numberType", req.getDnisType());
            mapArg.put("forwardedNumber", req.getDialNum());
            if (ObjectUtils.isEmpty(req.getVgwAuthPw())) {
                req.setVgwAuthPw(req.getDialNum() + vgwAuthPwAppendic);
            }
            mapArg.put("password", req.getVgwAuthPw());
            try {
                String strArgs = new ObjectMapper().writeValueAsString(mapArg);
                voiceGatewayServiceAPI.requestNumber(req.getFullDnis(), strArgs);
                repoExtension.deleteExtension(req);
                repoExtension.deleteExtOb(req.getFkCompany());
                log.info("Successfully deleted extension for: {}", req);
                res = ResponseEntity.ok(ResponseApi.success("Successfully deleted extension.", req));
            } catch (FeignClientException e) {
                log.error("Error during voice-gw API call for: {}", req, e);
                String errorMsg;
                try {
                    errorMsg = objectMapper.writeValueAsString(e.contentUTF8());
                } catch (JsonProcessingException e1) {
                    errorMsg = "Error processing FeignClientException";
                }
                res = ResponseEntity.ok(ResponseApi.error("Voice-gw error.", errorMsg));
            } catch (Exception e) {
                log.error("Error deleting extension for: {}", req, e);
                res = ResponseEntity.ok(ResponseApi.error("Failed to delete extension.", e.getMessage()));
            }
        }

        return res;
    }

    /** Extension 착신전환 변경 */
    public ResponseEntity<?> updateFwdYn(
            String extNum
            ,String status
    ) {
        DaoExtension req = new DaoExtension();
        req.setExtNum(extNum);

        switch (status) {
            case "active":
                req.setCallFwdYn("Y");
                break;
            case "inactive":
                req.setCallFwdYn("N");
                break;
            default:
                log.error("Unsupported status received: {}", status);
                return ResponseEntity.badRequest().body(ResponseApi.fail("Unsupported status", status));
        }

        try {
            if (repoExtension.existsExtensionByDialNumAndExtNum(req).isEmpty()) {
                log.warn("Extension not found for request: {}", req);
                return ResponseEntity.ok(ResponseApi.fail("Extension not found.", extNum));
            }
            repoExtension.updateFwdYn(req);
            log.info("Successfully " + status + " the forward status for extNum: {}", extNum);
            return ResponseEntity.ok(ResponseApi.success("Successfully " + status + " the forward status.", null));
        } catch (Exception e) {
            log.error("Error while " + status + " forward status for: {}", extNum, e);
            return ResponseEntity.ok(ResponseApi.error("Failed to " + status + " forward status.", e.getMessage()));
        }
    }
}
