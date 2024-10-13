package com.aice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aice.dao.bizphone.DaoBizPhone;
import com.aice.dao.vgw.ResVgwNumberPlans;
import com.aice.service.CommonConfigService;
import com.aice.service.PhoneLineService;

import lombok.extern.slf4j.Slf4j;

@Deprecated
@Slf4j
@RestController
@RequestMapping("/phoneLine")
public class PhoneLineManagerController {
    @Autowired PhoneLineService phoneLineService;
    @Autowired CommonConfigService commonConfigService;

    /**
     * voice-gw에서 관리하는 전화회선 리스트 voice-gw api로 조회
     *
     * @return
     */
    @PostMapping("/showAvailablePhoneLineList")
    public ResponseEntity<List<ResVgwNumberPlans>> showAvailablePhoneLineList() {
        return phoneLineService.showAvailablePhoneLineList();
    }

    /**
     * 워크센터에 등록되어 있는 특정 전화회선 db조회
     *
     * @param pkCompanyPhone
     * @return
     */
    @PostMapping("/selectPhoneLine")
    public ResponseEntity<DaoBizPhone> selectPhoneLine(String pkCompanyPhone) {
        return phoneLineService.selectPhoneLine(pkCompanyPhone);
    }

    /**
     * 워크센터에 등록되어 있는 전화회선 리스트
     *
     * @return
     */
    @PostMapping("/selectPhoneLineList")
    public ResponseEntity<List<DaoBizPhone>> selectPhoneLineList() {
        return phoneLineService.selectPhoneLineList();
    }

    /**
     * 전화회선 저장
     *
     * @param mainPhoneNum
     * @param bizPhoneNum
     * @param bizPhoneName
     * @param fkCompany
     * @param outboundYn
     * @param fkCompanyStaff
     * @return
     */
    @PostMapping("/insertNumbers")
    public ResponseEntity<String> insertNumbers(
        String mainPhoneNum,
        String bizPhoneNum,
        String bizPhoneName,
        String fkCompany,
        String outboundYn,
        String fkCompanyStaff
    ) {
        return phoneLineService
            .insertNumbers(mainPhoneNum,bizPhoneNum,bizPhoneName,fkCompany,outboundYn,fkCompanyStaff);
    }

    /**
     * 전화회선 정보 업데이트
     *
     * @param pkCompanyPhone
     * @param companyName
     * @param channelName
     * @return
     */
    // 수정 데이터 키값은 pk id로 입력받는지??? or 회사 id랑
    @PostMapping("/updateNumbers")
    public ResponseEntity<String> updateNumbers(String pkCompanyPhone,String companyName,String channelName) {
        return phoneLineService.updateNumbers(pkCompanyPhone,companyName,channelName);
    }

    /**
     * 특정 전화회선 삭제
     *
     * @param pkCompanyPhone
     * @return
     */
    @PostMapping("/deleteNumbers")
    public ResponseEntity<String> deleteNumbers(String pkCompanyPhone) {
        return phoneLineService.deleteNumbers(pkCompanyPhone);
    }

    /**
     * 파일 업로드
     *
     * @param multipartFile
     * @param fkCompany
     * @param docType
     */
    @PostMapping("/uploadDocFile")
    public void uploadDocFile(@RequestParam("file") MultipartFile multipartFile,String fkCompany,String docType) {
        // return phoneLineService.getAllFromTable();
        System.out.println("uploadDocFile start");

        commonConfigService.uploadDocFile(multipartFile,fkCompany,docType);
    }
}
