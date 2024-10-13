package com.aice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.aice.dao.DaoFileLog;
import com.aice.dao.FileVO;
import com.aice.enums.EnumMetaType1;
import com.aice.enums.EnumMetaType2;
import com.aice.enums.EnumMetaType3;
import com.aice.enums.EnumSolutionType;
import com.aice.enums.EnumUserType;
import com.aice.util.MultiValueMapConverter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Deprecated
@Slf4j
@Service
public class CommonConfigService {
    @Value("${file-gw.hostname}")
    private String urlFileGw;
    @Value("${file-gw.context-path}")
    private String contextPath;


    @SneakyThrows
    public void uploadDocFile(MultipartFile multipartFile, String fkCompany, String docType){
        EnumMetaType1 enumMetaType1 = EnumMetaType1.Receptionist;
        EnumMetaType2 enumMetaType2 = EnumMetaType2.findByValue(docType);
        EnumMetaType3 enumMetaType3 = EnumMetaType3.META_DOC;

        DaoFileLog daoFileLog = new DaoFileLog();
        daoFileLog.setSolutionType(EnumSolutionType.WorkCenter.getValue());
        daoFileLog.setUserType(EnumUserType.COMPANY.getValue());
        daoFileLog.setFkCompany(fkCompany);
        daoFileLog.setMeta1(enumMetaType1.getValue());
        daoFileLog.setMeta2(enumMetaType2.getValue());
        daoFileLog.setMeta3(enumMetaType3.getValue());

        FileVO fileVO = new FileVO();

        RestTemplate restTemplate = new RestTemplate();

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        fileVO.setPathFile(multipartFile);

        MultiValueMap<String, Object> fileMultiValueMap = new MultiValueMapConverter(fileVO).convert();
        MultiValueMapConverter multiValueMapConverter = new MultiValueMapConverter(fileVO);
        MultiValueMap<String, Object> result = multiValueMapConverter.addMultiValueFromBean(fileMultiValueMap,"",daoFileLog);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(result, headers);
        restTemplate.postForEntity(urlFileGw+contextPath, request, Void.class);
    }
}
