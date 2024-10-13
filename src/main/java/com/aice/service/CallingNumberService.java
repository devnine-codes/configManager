package com.aice.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aice.dao.bizphone.DaoBizPhone;
import com.aice.repo.RepoCallingNumber;

import lombok.extern.slf4j.Slf4j;

@Deprecated
@Service
@Slf4j
public class CallingNumberService {


  @Autowired
  private RepoCallingNumber callingNumberDao;

  @Autowired
  private CommonConfigService commonConfigService;



  public List<DaoBizPhone> getAllFromTable() {
    return callingNumberDao.selectAll();
  }

  /**
   * * 대표번호 유무를 따져 그를 기준으로 Map 해준다
   * @param companyId
   * @return
   */
  public ResponseEntity<Map<String, List<DaoBizPhone>>> getCallingNumberMapByCompanyId(Long companyId) {
    ResponseEntity< Map<String, List<DaoBizPhone>>> res = ResponseEntity.ok(null);
    try {
      List callingNumber = callingNumberDao.selectAllByCompanyId(companyId);
      log.info(callingNumber.toString() + "조회 완료. 대표번허 유무로 Mapping 시작합니다.");
      Map<String, List<DaoBizPhone>> mapByPloonetYN = new HashMap<>();

      mapByPloonetYN = (Map<String, List<DaoBizPhone>>) callingNumber.stream()
          .collect(Collectors.groupingBy(DaoBizPhone::getPloonetYn, LinkedHashMap::new, Collectors.toList()));

      res = ResponseEntity.ok(mapByPloonetYN);
      log.info(res.toString() + "분류 완료");
    }catch (Exception e){
      e.printStackTrace();

    }
    return res;
  }

  /**
   * * 파일을 우선 fileGateway에 연결하여  파일을 전송, 해당 프로세스가 성공해야지만, 발신 번호를 저장한다
   * @param multipartFile
   * @param docType
   * @param callingNumberVo
   */
  public void insertCompanyCallingNumber(List<MultipartFile> multipartFile, String docType,
      DaoBizPhone callingNumberVo) {
    try {
      for(int i=0; i<multipartFile.size();i++){
        MultipartFile file = multipartFile.get(i);
        commonConfigService.uploadDocFile(file,callingNumberVo.getFkCompany().toString(), docType);
        log.info(file+"추가 완료");
      }

      callingNumberDao.insertCallingNumber(callingNumberVo);
      log.info(callingNumberVo.toString() + "등록 완료");
    }catch (Exception e){
      e.printStackTrace();
      
    }
  }

  /**
   * * 발신번호 정보를 업데이트 한다. 이 때 파일 정보는 수정 및 삭제 추가가 불가능하다.
   * @param callingNumberVo
   */
  public void updateCompanyCallingNumber(DaoBizPhone req) {
    try {
      callingNumberDao.updateCallingNumber(req);
      log.info("전화 회신 업데이트 완료: " + req.toString());
    }catch (Exception e){
      e.printStackTrace();
    }


  }

  /**
   * * 발신 정보를 삭제한다. 파일 삭제는 추가되지 않았다.  
   * @param callingId
   */
  public void deleteCompanyCallingNumber(Long callingId) {
    try {
      callingNumberDao.deleteCompanyCallingNumber(callingId);
      log.info("전화 회신 번호가 무사히 삭제되었습니다.");
    }catch (Exception e){
      e.printStackTrace();

    }

  }


}
