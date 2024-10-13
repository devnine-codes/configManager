package com.aice.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aice.dao.bizphone.DaoBizPhone;
import com.aice.service.CallingNumberService;

import lombok.extern.slf4j.Slf4j;

@Deprecated
@RestController
@Slf4j
@RequestMapping("/callingNumber")
public class CallingNumberManagerController {

  @Autowired
  private CallingNumberService callingNumberService;

  @GetMapping("/test")
  public List<DaoBizPhone> selectAll() {
    return  callingNumberService.getAllFromTable();
  }

  /**
   * 대표번호 유무를 따져 Mapping 한 리스트 반환
   * @param companyId
   * @return ResponseEntity<Map<String ,List<CallingNumberVo> >>
   */
  @GetMapping("/selectCallingNumberMap")
  public ResponseEntity<Map<String ,List<DaoBizPhone> >> showCallingNumListMap(Long companyId) {
    return  callingNumberService.getCallingNumberMapByCompanyId(companyId);
  }

  /**
   * * 전화 회선 번호를 등록하는 API. 등록할 시 파일을 필수적으로 첨부하도록 구성.
   * @param multipartFile
   * @param docType
   * @param callingNumberVo
   */
  @PostMapping("/insertCallingNumber")
  public void insertCompanyEmail(@RequestParam("file") List<MultipartFile> multipartFile,String docType, @ModelAttribute DaoBizPhone callingNumberVo) {
    callingNumberService.insertCompanyCallingNumber( multipartFile,docType, callingNumberVo);
  }

  /**
   * * 등록한 번호의 정보를 수정
   * @param callingNumberVo
   */

  @PutMapping("/updateCallingNumber")
  public void updateCompanyEmail(@RequestBody DaoBizPhone req) {
    callingNumberService.updateCompanyCallingNumber(req);
  }

  /**
   * * 등록한 번호를 삭제
   * @param callingId
   */
  @DeleteMapping("/deleteCallingNumber")
  public void deleteCompanyEmail(Long callingId){
    callingNumberService.deleteCompanyCallingNumber(callingId);

  }
}
