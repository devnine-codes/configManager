package com.aice.service;

import java.util.*;

import com.aice.dao.DaoCompanyStaff;
import com.aice.dao.dnis.DaoCompanyDnisCamelCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.aice.dao.DaoCompany;
import com.aice.dao.ResponseApi;
import com.aice.dao.bizphone.DaoBizPhone;
import com.aice.dao.dnis.DaoCompanyDnis;
import com.aice.dao.vgw.ResVgwMain;
import com.aice.dao.vgw.ResVgwNumberPlans;
import com.aice.dao.vgw.ResVgwNumbers;
import com.aice.repo.RepoCompany;
import com.aice.repo.RepoCompanyDnis;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException.FeignClientException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CompanyDnisService {
    @Autowired VoiceGatewayServiceAPI voiceGatewayServiceAPI;
    @Autowired ServiceBizPhone serviceBizPhone;
    @Autowired RepoCompanyDnis repoCompanyDnis;
    @Autowired RepoCompany repoCompany;
    @Autowired ObjectMapper objectMapper;
    @Value(value = "${voice-gw.vgw-id}")
    String vgwId;
    String vgwAuthPwAppendic = "!#%&";

    /**
     * voice gateway 내선번호 생성
     * config manager tbl_company_dnis 레코드생성
     */
    public ResponseEntity<?> saveCompanyDnis(DaoCompanyDnis req) {
        log.info("## companyDnisVo: {}", req.toJsonTrim());
        ResponseEntity<?> res = ResponseEntity.ok(null);
        Boolean isDuplicateDnis = checkForDnis(req);

        // 2024-07-30 하나의 AI에 여러 DNIS를 할당할 수 있어야 한다고 하여 주석
        /*
        Optional<DaoCompanyDnis> optionalDnis = repoCompanyDnis.findByStaff(req);
        if (optionalDnis.isPresent()) {
            return ResponseEntity.ok(ResponseApi.fail("Already exist", optionalDnis.get()));
        }
        */
        if(!isDuplicateDnis){
            ObjectMapper mapper = new ObjectMapper();
            String strArgs = null;
            String companyName = null;

            try {
                /* 회사명 조회 */
                Optional<DaoCompany> company = repoCompany.selectCompanyById(req.getFkCompany());
                if (company.isPresent()) {
                    companyName = company.get().getFdCompanyId();
                }

                /* voice-gw api 요청 값 */
                Map<String, Object> mapArg = new HashMap<>();
                mapArg.put("command", "add");
                mapArg.put("numberType", req.getNumberType());
                if(req.getUseCategory() != null) {
                    mapArg.put("serviceType", req.getUseCategory());
                }
                mapArg.put("name", companyName);

                if(ObjectUtils.isEmpty(req.getVgwAuthPw()) == true) {
                    req.setVgwAuthPw(req.getFdDnis() + vgwAuthPwAppendic);
                }
                mapArg.put("password", req.getVgwAuthPw());

                strArgs = mapper.writeValueAsString(mapArg);
                log.info("## strArgs: {}", strArgs);

                /* bizPhone 생성 값 */
                DaoBizPhone bizPhoneData = new DaoBizPhone();
                bizPhoneData.setSolutionType(req.getSolutionType());
                bizPhoneData.setUserType(req.getUserType());
                bizPhoneData.setFkCompany(req.getFkCompany());
                bizPhoneData.setBizPhoneName("기본생성");
                bizPhoneData.setBizPhoneNum(req.getFullDnis());
                bizPhoneData.setDnis(req.getFdDnis());

                /* XXX: voice-gw api call */
                ResponseEntity<ResVgwMain> resVoice = voiceGatewayServiceAPI.requestNumber(req.getFullDnis(), strArgs);
                ResVgwMain resData = resVoice.getBody();

                /* XXX: tbl_company_dnis 레코드 생성 */
                vgwId = ObjectUtils.isEmpty(req.getVgwId()) ? vgwId : req.getVgwId();
                req.setVgwId(vgwId);
                repoCompanyDnis.createDnis(req);

                /* tbl_company_biz_phone 레코드 생성 */
                serviceBizPhone.insertBizPhoneNumber(bizPhoneData);

                res = ResponseEntity.ok(ResponseApi.success("dnis create", req));

            } catch (FeignClientException e) {
                StringBuilder sbResData = new StringBuilder();
                try{
                    sbResData.append(objectMapper.writeValueAsString(e.contentUTF8()));
                }catch(JsonProcessingException e1){
                    e1.printStackTrace();
                }
                res = ResponseEntity.ok(
                        ResponseApi.error("voice-gw error", sbResData.toString()));
            } catch (Exception e) {
                e.printStackTrace();
                res = ResponseEntity.ok(ResponseApi.error("dnis error", e.getMessage()));
            }
            return res;
        } else {
            res = ResponseEntity.ok(ResponseApi.fail("dnis exist", req.getFullDnis()));
            return res;
        }
    }

    /**
     * QUICK START시 DNIS 자동 할당
     * @param req
     * @return
     */
    public ResponseEntity<?> saveCompanyDnisAuto(DaoCompanyDnis req) {
        ResponseEntity<?> res = ResponseEntity.ok(null);

        Optional<DaoCompanyDnis> optionalDnis = repoCompanyDnis.findByStaff(req);
        if (optionalDnis.isPresent()) {
            return ResponseEntity.ok(ResponseApi.fail("Already exist", optionalDnis.get()));
        }

        // QuickStart 전용
        if ((req.getFdDnis() == null && req.getFullDnis() == null)) {
            ResVgwNumberPlans findDnisArg = new ResVgwNumberPlans();
            findDnisArg.setCommand("show");

            ResponseEntity<?> dnisRes = requestNumberPlan(findDnisArg);

            if (dnisRes.getBody() == null) {
                throw new RuntimeException("Response body is null");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseMap = objectMapper.convertValue(dnisRes.getBody(), Map.class);
            Optional<DaoCompanyDnis> optionalVgwNumber;
            Set<String> checkedNumbers = new HashSet<>();
            String availableNumber = null;

            if (responseMap != null && "success".equals(responseMap.get("status"))) {
                Map<String, Object> dataMap = (Map<String, Object>) responseMap.get("data");
                if (dataMap != null) {
                    List<Map<String, Object>> numberPlansList = (List<Map<String, Object>>) dataMap.get("numberPlans");
                    for (Map<String, Object> numberPlan : numberPlansList) {
                        if ("no".equalsIgnoreCase((String) numberPlan.get("inUse")) && req.getNumberType().equalsIgnoreCase((String) numberPlan.get("type"))) {
                            availableNumber = (String) numberPlan.get("number");
                            if (checkedNumbers.contains(availableNumber)) {
                                continue;
                            }
                            optionalVgwNumber = repoCompanyDnis.findByFullDnis(availableNumber);
                            checkedNumbers.add(availableNumber);
                            if (optionalVgwNumber.isPresent()) {
                                continue;
                            }
                            req.setFullDnis(availableNumber);
                            req.setFdDnis(availableNumber.substring(3));
                            break;
                        }
                    }
                    if ((req.getFdDnis() == null && req.getFullDnis() == null)) {
                        return ResponseEntity.ok(ResponseApi.fail("No numbers left.", req));
                    }
                }
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        String strArgs = null;
        String companyName = null;

        try {
            /* 회사명 조회 */
            Optional<DaoCompany> company = repoCompany.selectCompanyById(req.getFkCompany());
            if (company.isPresent()) {
                companyName = company.get().getFdCompanyId();
            }

            /* voice-gw api 요청 값 */
            Map<String, Object> mapArg = new HashMap<>();
            mapArg.put("command", "add");
            mapArg.put("numberType", req.getNumberType());
            if(req.getUseCategory() != null) {
                mapArg.put("serviceType", req.getUseCategory());
            }
            mapArg.put("name", companyName);

            if(ObjectUtils.isEmpty(req.getVgwAuthPw()) == true) {
                req.setVgwAuthPw(req.getFdDnis() + vgwAuthPwAppendic);
            }
            mapArg.put("password", req.getVgwAuthPw());

            strArgs = mapper.writeValueAsString(mapArg);
            log.info("## strArgs: {}", strArgs);

            /* bizPhone 생성 값 */
            DaoBizPhone bizPhoneData = new DaoBizPhone();
            bizPhoneData.setSolutionType(req.getSolutionType());
            bizPhoneData.setUserType(req.getUserType());
            bizPhoneData.setFkCompany(req.getFkCompany());
            bizPhoneData.setBizPhoneName("기본생성");
            bizPhoneData.setBizPhoneNum(req.getFullDnis());
            bizPhoneData.setDnis(req.getFdDnis());

            /* XXX: voice-gw api call */
            ResponseEntity<ResVgwMain> resVoice = voiceGatewayServiceAPI.requestNumber(req.getFullDnis(), strArgs);
            ResVgwMain resData = resVoice.getBody();

            /* XXX: tbl_company_dnis 레코드 생성 */
            vgwId = ObjectUtils.isEmpty(req.getVgwId()) ? vgwId : req.getVgwId();
            req.setVgwId(vgwId);
            repoCompanyDnis.createDnis(req);

            /* tbl_company_biz_phone 레코드 생성 */
            serviceBizPhone.insertBizPhoneNumber(bizPhoneData);

            res = ResponseEntity.ok(ResponseApi.success("dnis auto create", req));

        } catch (FeignClientException e) {
            StringBuilder sbResData = new StringBuilder();
            try{
                sbResData.append(objectMapper.writeValueAsString(e.contentUTF8()));
            }catch(JsonProcessingException e1){
                e1.printStackTrace();
            }
            res = ResponseEntity.ok(
                    ResponseApi.error("voice-gw error", sbResData.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            res = ResponseEntity.ok(ResponseApi.error("dnis error", e.getMessage()));
        }
        return res;
    }

    /**
     * voice gateway 내선번호 조회
     * config manager tbl_company_dnis 레코드 조회
     */
    public ResponseEntity<?> findAll(DaoCompanyDnis req) {
        log.info("## companyDnisVo: {}", req);
        ResponseEntity<?> res = ResponseEntity.ok(null);
        //ObjectMapper mapper = new ObjectMapper();
        //String strArgs = null;
        try {
            /* voice-gw api 요청 값 */
            //Map<String, Object> mapArg = new HashMap<>();
            //mapArg.put("command", "show");
            //mapArg.put("numberType", req.getNumberType());
            
            //strArgs = mapper.writeValueAsString(mapArg);
            //log.info("## strArgs: {}", strArgs);

            /* XXX: voice-gw api call */
            //ResponseEntity<ResVgwMain> resVoice = voiceGatewayServiceAPI.requestNumber(req.getFullDnis(), strArgs);
            //ResVgwMain resData = resVoice.getBody();
            //log.info("## resData: {}", resData);
            
            /* XXX: tbl_company_dnis 레코드 조회 */
            List<DaoCompanyDnis> resData = repoCompanyDnis.findAll(req);

            if(resData.size() > 0) {
                res = ResponseEntity.ok(ResponseApi.success("dnis success", resData));
            } else {
                res = ResponseEntity.ok(ResponseApi.success("not exist", req));
            }
            
        } catch (FeignClientException e) {
            StringBuilder sbResData = new StringBuilder();
            try{
                sbResData.append(objectMapper.writeValueAsString(e.contentUTF8()));
            }catch(JsonProcessingException e1){
                e1.printStackTrace();
            }
            res = ResponseEntity.ok(
                    ResponseApi.error("voice-gw error", sbResData.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            res = ResponseEntity.ok(ResponseApi.error("dnis error", e.getMessage()));
        }
        return res;
    }

    /**
     * 2023.11.29 New
     * voice gateway 내선 번호 수정
     * config manager tbl_company_dnis 레코드 수정
     */
    public ResponseEntity<?> patchCompanyDnis(DaoCompanyStaff req, DaoCompanyDnis daoCompanyDnis) {
        log.info("## patchCompanyDnis. req:{}", daoCompanyDnis.toJsonTrim());
        ResponseEntity<?> res = ResponseEntity.ok(null);
        ObjectMapper mapper = new ObjectMapper();
        String strArgs = null;

        Optional<DaoCompanyDnis> optionalDnis = repoCompanyDnis.findByStaff(daoCompanyDnis);
        if (optionalDnis.isEmpty()) {
            return ResponseEntity.ok(ResponseApi.fail("No data dnis.", daoCompanyDnis));
        } else {
            daoCompanyDnis.setFullDnis(optionalDnis.get().getFullDnis());
        }

        try {
            /* voice-gw api 요청 값 */
            Map<String, Object> mapArg = new HashMap<>();
            mapArg.put("command", "update");
            mapArg.put("numberType", daoCompanyDnis.getNumberType());
            if (ObjectUtils.isEmpty(daoCompanyDnis.getBizPhoneNum()) == false) {
                mapArg.put("callerId", daoCompanyDnis.getBizPhoneNum());
            }

            if(ObjectUtils.isEmpty(daoCompanyDnis.getVgwAuthPw()) == true) {
                daoCompanyDnis.setVgwAuthPw(daoCompanyDnis.getFdDnis() + vgwAuthPwAppendic);
            }
            mapArg.put("password", daoCompanyDnis.getVgwAuthPw());

            strArgs = mapper.writeValueAsString(mapArg);
            log.info("## strArgs: {}", strArgs);

            /* XXX: voice-gw api call */
            ResponseEntity<ResVgwMain> resVoice = voiceGatewayServiceAPI.requestNumber(daoCompanyDnis.getFullDnis(), strArgs);
            ResVgwMain resData = resVoice.getBody();

            /* XXX: tbl_company_dnis 레코드 수정 */
            repoCompanyDnis.updateDnis(req);
            res = ResponseEntity.ok(ResponseApi.success("dnis update", null));

        } catch (FeignClientException e) {
            StringBuilder sbResData = new StringBuilder();
            try{
                sbResData.append(objectMapper.writeValueAsString(e.contentUTF8()));
            }catch(JsonProcessingException e1){
                e1.printStackTrace();
            }
            res = ResponseEntity.ok(
                    ResponseApi.error("voice-gw error", sbResData.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            res = ResponseEntity.ok(ResponseApi.error("dnis error", e.getMessage()));
        }
        return res;
    }

    /**
     * voice gateway 내선 번호 수정
     * config manager tbl_company_dnis 레코드 수정
     */
    public ResponseEntity<?> updateCompanyDnis(DaoCompanyDnis req) {
        ResponseEntity<?> res = ResponseEntity.ok(null);
        ObjectMapper mapper = new ObjectMapper();
        String strArgs = null;

        Optional<DaoCompanyDnis> optionalDnis = repoCompanyDnis.findByStaff(req);
        if (optionalDnis.isEmpty()) {
            return ResponseEntity.ok(ResponseApi.fail("No data dnis.", req));
        }

        try {
            /* voice-gw api 요청 값 */
            Map<String, Object> mapArg = new HashMap<>();
            mapArg.put("command", "update");
            mapArg.put("numberType", req.getNumberType());
            if (ObjectUtils.isEmpty(req.getBizPhoneNum()) == false) {
                mapArg.put("callerId", req.getBizPhoneNum());
            }

            if(ObjectUtils.isEmpty(req.getVgwAuthPw()) == true) {
                req.setVgwAuthPw(req.getFdDnis() + vgwAuthPwAppendic);
            }
            mapArg.put("password", req.getVgwAuthPw());

            strArgs = mapper.writeValueAsString(mapArg);
            log.info("## strArgs: {}", strArgs);

            /* XXX: voice-gw api call */
            ResponseEntity<ResVgwMain> resVoice = voiceGatewayServiceAPI.requestNumber(req.getFullDnis(), strArgs);
            ResVgwMain resData = resVoice.getBody();
            res = ResponseEntity.ok(ResponseApi.success("dnis success", resData));

            /* XXX: tbl_company_dnis 레코드 수정 */
            Optional<DaoCompanyDnis> vo = repoCompanyDnis.findById(req.getPkCompanyDnis());
            if (vo.isPresent()) {
                repoCompanyDnis.updateDnis2(req);
                return ResponseEntity.ok(ResponseApi.success("dnis update", req));
            } else {
                return ResponseEntity.ok(ResponseApi.fail("dnis error", req));
            }

        } catch (FeignClientException e) {
            StringBuilder sbResData = new StringBuilder();
            try{
                sbResData.append(objectMapper.writeValueAsString(e.contentUTF8()));
            }catch(JsonProcessingException e1){
                e1.printStackTrace();
            }
            res = ResponseEntity.ok(
                    ResponseApi.error("voice-gw error", sbResData.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            res = ResponseEntity.ok(ResponseApi.error("dnis error", e.getMessage()));
        }
        return res;
    }

    /**
     * voice gateway 내선 번호 제거
     * config manager tbl_company_dnis 레코드 삭제
     */
    public ResponseEntity<?> deleteCompanyDnis(DaoCompanyDnis req) {
        log.info("## companyDnisVo: {}", req);
        ResponseEntity<?> res = ResponseEntity.ok(null);
        ObjectMapper mapper = new ObjectMapper();
        String strArgs = null;

        Optional<DaoCompanyDnis> optionalDnis = repoCompanyDnis.findByStaff(req);
        if (optionalDnis.isEmpty()) {
            return ResponseEntity.ok(ResponseApi.fail("No data dnis.", req));
        }

        try {
            /* voice-gw api 요청 값 */
            Map<String, Object> mapArg = new HashMap<>();
            mapArg.put("command", "delete");
            mapArg.put("numberType", req.getNumberType());

            if(ObjectUtils.isEmpty(req.getVgwAuthPw()) == true) {
                req.setVgwAuthPw(req.getFdDnis() + vgwAuthPwAppendic);
            }
            mapArg.put("password", req.getVgwAuthPw());

            strArgs = mapper.writeValueAsString(mapArg);
            log.info("## strArgs: {}", strArgs);

            /* XXX: voice-gw api call */
            ResponseEntity<ResVgwMain> resVoice = voiceGatewayServiceAPI.requestNumber(req.getFullDnis(), strArgs);
            ResVgwMain resBody = resVoice.getBody();
            log.info("## resData: {}", resBody.toJsonTrim());

            /* tbl_company_biz_phone 레코드 삭제 */
            serviceBizPhone.deleteBizPhoneNumber(req.getFkCompany(), req.getFullDnis(), req.getFdDnis());

            /* XXX: tbl_company_dnis 레코드 삭제 */
            repoCompanyDnis.deleteDnis(
                    req.getFkCompany(),
                    req.getFkCompanyStaffAi(),
                    req.getFdDnis(),
                    req.getFullDnis());

            res = ResponseEntity.ok(ResponseApi.success("dnis delete", req));

        } catch (FeignClientException e) {
            log.error("deleteCompanyDnis: {}", e);
            StringBuilder sbResData = new StringBuilder();
            try{
                sbResData.append(objectMapper.writeValueAsString(e.contentUTF8()));
            }catch(JsonProcessingException e1){
                e1.printStackTrace();
            }
            res = ResponseEntity.ok(
                    ResponseApi.error("voice-gw error", sbResData.toString()));
        } catch (Exception e) {
            log.error("deleteCompanyDnis: {}",e);
            res = ResponseEntity.ok(ResponseApi.error("dnis error", e.getMessage()));
        }
        return res;
    }

    public ResponseEntity<?> deleteDnisByCompany(Long id) {
        log.info("deleteDnisByCompany id:{}", id);
        ResponseEntity<?> res = ResponseEntity.ok(null);
//        Optional<DaoCompany> daoCompany = repoCompany.selectCompanyById(id);

//        if (daoCompany.isEmpty()) {
//            return ResponseEntity.ok(ResponseApi.fail("No data company.", id));
//        }

//        if (daoCompany.get().getSolutionType().equals("B14")) {
//            res = ResponseEntity.ok(ResponseApi.success("No son secretary user.", id));
//        }

        DaoCompanyDnis daoCompanyDnis = new DaoCompanyDnis();
        daoCompanyDnis.setFkCompany(id);

        List<DaoCompanyDnis> data = repoCompanyDnis.findAll(daoCompanyDnis);

        if (data.isEmpty()) {
            res = ResponseEntity.ok(ResponseApi.fail("No data.", id));
        }

        for(DaoCompanyDnis req : data) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                String strArgs = null;

                /* voice-gw api 요청 값 */
                Map<String, Object> mapArg = new HashMap<>();
                mapArg.put("command", "delete");
                mapArg.put("numberType", req.getNumberType());

                if(ObjectUtils.isEmpty(req.getVgwAuthPw()) == true) {
                    req.setVgwAuthPw(req.getFdDnis() + vgwAuthPwAppendic);
                }
                mapArg.put("password", req.getVgwAuthPw());

                strArgs = mapper.writeValueAsString(mapArg);
                log.info("## strArgs: {}", strArgs);

                /* XXX: voice-gw api call */
                ResponseEntity<ResVgwMain> resVoice = voiceGatewayServiceAPI.requestNumber(req.getFullDnis(), strArgs);
                ResVgwMain resBody = resVoice.getBody();
                log.info("## resData: {}", resBody);

                /* tbl_company_biz_phone 레코드 삭제 */
                serviceBizPhone.deleteBizPhoneNumber(req.getFkCompany(), req.getFullDnis(), req.getFdDnis());

                /* XXX: tbl_company_dnis 레코드 삭제 */
                repoCompanyDnis.deleteDnis(
                        req.getFkCompany(),
                        req.getFkCompanyStaffAi(),
                        req.getFdDnis(),
                        req.getFullDnis());

                res = ResponseEntity.ok(ResponseApi.success("dnis delete", req));
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
                log.error("Error deleting dnis for: {}", req, e);
                res = ResponseEntity.ok(ResponseApi.error("Failed to delete dnis.", e.getMessage()));
            }
        }

        return res;
    }

    /* voice gateway API - number_plans, manager_number_plans */
    public ResponseEntity<?> requestNumberPlan(ResVgwNumberPlans req) {
        ResponseEntity<?> res = ResponseEntity.ok(null);

        try {
            ResVgwMain resData = voiceGatewayServiceAPI.requestNumberPlan(req.toJson());
            res = ResponseEntity.ok(ResponseApi.success("dnis success", resData));

        } catch (FeignClientException e) {
            StringBuilder sbResData = new StringBuilder();
            try{
                sbResData.append(objectMapper.writeValueAsString(e.contentUTF8()));
            }catch(JsonProcessingException e1){
                e1.printStackTrace();
            }
            res = ResponseEntity.ok(
                    ResponseApi.error("voice-gw error", sbResData.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            res = ResponseEntity.ok(ResponseApi.error("dnis error", e.getMessage()));
        }
        return res;
    }

    /*
     * voice gateway에 신규 070 번호 또는 내선 번호를 가져온다. key_numbers, number_plans,
     * manager_number_plans
     */
    public ResponseEntity<?> requestNumber(ResVgwNumbers req) {
        ResponseEntity<?> res = ResponseEntity.ok(null);

        try {
            ResponseEntity<ResVgwMain> resVoice = voiceGatewayServiceAPI.requestNumber(req.getNumber(), req.toJson());
            ResVgwMain resData = resVoice.getBody();
            res = ResponseEntity.ok(ResponseApi.success("dnis success", resData));

        } catch (FeignClientException e) {
            StringBuilder sbResData = new StringBuilder();
            try{
                sbResData.append(objectMapper.writeValueAsString(e.contentUTF8()));
            }catch(JsonProcessingException e1){
                e1.printStackTrace();
            }
            res = ResponseEntity.ok(
                    ResponseApi.error("voice-gw error", sbResData.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            res = ResponseEntity.ok(ResponseApi.error("dnis error", e.getMessage()));
        }
        return res;
    }

    /*
     * voice gateway에 특정 번호 또는 내선 번호가 존재하는지 체크한다.
     * fd_dnis 또는 full_dnis로 조회
     */
    public Boolean checkForDnis(DaoCompanyDnis req) {
        Boolean res = repoCompanyDnis.checkForDnis(req);
        
        if(res != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 고객사 발신번호 리스트 조회
     * 캠페인에서 사용할 목적으로 생성했으며 기존 dnis의 객체가 스네이크 케이스이므로 카멜 케이스로 새로 생성
     * @param fkCompany
     * @return
     */
    public ResponseEntity<ResponseApi> findDnisList(Long fkCompany, String useCategory) {
        try {
            List<DaoCompanyDnisCamelCase> result = repoCompanyDnis.listDnisByCompany(fkCompany, useCategory);
            if (result.isEmpty()) {
                return ResponseEntity.ok(ResponseApi.fail("not found dnis.", null));
            } else {
                return ResponseEntity.ok(ResponseApi.success("success findDnisList.", result));
            }
        } catch (Exception e) {
            ResponseApi errorResponse = ResponseApi.error("Internal server error.", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }
}
