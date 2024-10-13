package com.aice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.aice.dao.DaoCompany;
import com.aice.dao.DaoCompanyStaff;
import com.aice.dao.bizphone.DaoBizPhone;
import com.aice.dao.vgw.ResVgwMain;
import com.aice.dao.vgw.ResVgwNumberPlans;
import com.aice.enums.EnumSolutionType;
import com.aice.enums.EnumStatusType;
import com.aice.enums.EnumUserType;
import com.aice.repo.PhoneLineDao;
import com.aice.repo.RepoCompany;
import com.aice.repo.RepoStaff;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Deprecated
@Service
@Slf4j
public class PhoneLineService {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PhoneLineDao phoneLineDao;
    @Autowired
    private RepoStaff staffDao;
    @Autowired
    private RepoCompany companyDao;

    @Value("${voice-gw.hostname}")
    private String urlFileGw;
    @Value("${voice-gw.context-path}")
    private String contextPath;

    /**
     * voice-gw에서 관리하는 전화회선 리스트
     * voice-gw api로 조회
     *
     * @return
     */
    public ResponseEntity<List<ResVgwNumberPlans>> showAvailablePhoneLineList(){

        ResponseEntity<List<ResVgwNumberPlans>> res = ResponseEntity.ok(null);

        // voice gw
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        Map<String,String> map = new HashMap<>();
        map.put("command", "show");
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("command", "show");
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(multiValueMap, headers);
        try{
            ResVgwMain result = restTemplate.postForEntity(urlFileGw+contextPath, request, ResVgwMain.class).getBody();
            List<ResVgwNumberPlans> numberPlans = result.getNumberPlans();
            // 사용가능한 전화회선 리스트 필터링 (inUse=no)
            numberPlans = numberPlans.stream().filter(s->"no".equals(s.getInUse())).collect(Collectors.toList());
            res = ResponseEntity.ok(numberPlans);
        }catch (Exception e1){
            log.error("showAvailablePhoneLineList error", e1);
        }
        log.info("showAvailablePhoneLineList complete");

        return res;
    }

    /**
     * 워크센터에 등록되어 있는 특정 전화회선 db조회
     *
     * @param pkCompanyPhone
     * @return
     */
    public ResponseEntity<DaoBizPhone> selectPhoneLine(String pkCompanyPhone) {
        ResponseEntity<DaoBizPhone> res = ResponseEntity.ok(null);

        DaoBizPhone vo = new DaoBizPhone();
        vo.setPkCompanyPhone(Long.valueOf(pkCompanyPhone));

        try{
            DaoBizPhone number = phoneLineDao.selectPhoneLine(vo);
            res = ResponseEntity.ok(number);
        }catch (Exception e1){
            log.error("selectPhoneLine error", e1);
        }
        log.info("selectPhoneLine complete");

        return res;
    }

    /**
     * 워크센터에 등록되어 있는 전화회선 리스트
     *
     * @return
     */
    public ResponseEntity<List<DaoBizPhone>> selectPhoneLineList() {
        ResponseEntity<List<DaoBizPhone>> res = ResponseEntity.ok(null);

        try{
            List<DaoBizPhone> number = phoneLineDao.selectPhoneLineList();
            res = ResponseEntity.ok(number);
        }catch (Exception e1){
            log.error("selectPhoneLineList error", e1);
        }
        log.info("selectPhoneLineList complete");

        return res;
    }

    /**
     * 전화회선 저장
     *
     * @param mainPhoneNum
     * @param bizPhoneNum
     * @param fkCompany
     */
    public ResponseEntity<String> insertNumbers(String mainPhoneNum, String bizPhoneNum, String bizPhoneName,  String fkCompany, String outboundYn, String fkCompanyStaff) {

        ResponseEntity<String> res = ResponseEntity.ok(null);

        Optional<DaoCompanyStaff> staffInfo = staffDao.findByStaffId(Long.parseLong(fkCompanyStaff));
        if(staffInfo.isEmpty()) {
        }
        Optional<DaoCompany> companyInfo = companyDao.selectCompanyById(Long.parseLong(fkCompany));
        if(companyInfo.isEmpty()) {
        }

        DaoBizPhone vo = new DaoBizPhone();
        vo.setSolutionType(EnumSolutionType.WorkCenter.getValue());
        vo.setUserType(EnumUserType.COMPANY.getValue());
        vo.setFkCompany(Long.valueOf(fkCompany));
        vo.setBizPhoneNum(bizPhoneNum);
        vo.setPloonetYn("Y");
        vo.setRegStatus(EnumStatusType.Ready.getValue());
        if(!companyInfo.isEmpty()) {
            vo.setCompanyName(companyInfo.get().getFdCompanyName());
        }
        vo.setBizPhoneName(bizPhoneName);
        vo.setOutboundYn(outboundYn);
        vo.setMainPhoneNum(mainPhoneNum);
        vo.setFkCompanyStaff(Long.valueOf(fkCompanyStaff));
        if(!staffInfo.isEmpty()) {
            vo.setStaffName(staffInfo.get().getFdStaffName());
        }

        try{
            phoneLineDao.insertPhoneLine(vo);
            res = ResponseEntity.ok(null);
        }catch (Exception e1){
            log.error("insertNumbers error", e1);
        }
        log.info("insertNumbers complete");

        return res;
    }

    /**
     * 전화회선 정보 업데이트
     *
     * @param pkCompanyPhone
     * @param companyName
     * @param channelName
     * @return
     */
    public ResponseEntity<String> updateNumbers(String pkCompanyPhone, String companyName, String channelName) {
        ResponseEntity<String> res = ResponseEntity.ok(null);

        DaoBizPhone vo = new DaoBizPhone();
        vo.setCompanyName(companyName);
        vo.setBizPhoneName(channelName);
        vo.setPkCompanyPhone(Long.valueOf(pkCompanyPhone));

        try{
            phoneLineDao.updateNumbers(vo);
            res = ResponseEntity.ok(null);
        }catch (Exception e1){
            log.error("updateNumbers error", e1);
        }
        log.info("updateNumbers complete");

        return res;
    }

    /**
     * 특정 전화회선 삭제
     *
     * @param pkCompanyPhone
     * @return
     */

    public ResponseEntity<String> deleteNumbers(String pkCompanyPhone) {
        ResponseEntity<String> res = ResponseEntity.ok(null);

        DaoBizPhone vo = new DaoBizPhone();
        vo.setPkCompanyPhone(Long.valueOf(pkCompanyPhone));

        try{
            phoneLineDao.deleteNumbers(vo);
            res = ResponseEntity.ok(null);
        }catch (Exception e1){
            log.error("deleteNumbers error", e1);
        }
        log.info("deleteNumbers complete");

        return res;
    }


}
