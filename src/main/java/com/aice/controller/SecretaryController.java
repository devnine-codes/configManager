package com.aice.controller;

import com.aice.dao.DaoSecretary;
import com.aice.dao.ResponseApi;
import com.aice.dao.extension.DaoExtension;
import com.aice.dao.extension.DaoExtensionOb;
import com.aice.dao.secretary.SecretaryDao;
import com.aice.service.SecretaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/{apiVer}/secretary")
public class SecretaryController {
    @Autowired
    SecretaryService secretaryService;

    /**
     * 손비서 자동 자리비움 회사직원들 조회
     * @param apiVer
     * @param fkCompany
     * @return
     */
    @Deprecated
    @GetMapping("/companies/{fkCompany}/autoresponse")
    public ResponseEntity<?> autoResponseList(
            @PathVariable String apiVer,
            @PathVariable Long fkCompany
    ) {
        return ResponseEntity.ok(secretaryService.autoResponseList(fkCompany));
    }

    /**
     * 손비서 자동 자리비움 특정직원 조회
     * @param apiVer
     * @param fkCompany
     * @param pkCompanyStaff
     * @return
     */
    @Deprecated
    @GetMapping("/companies/{fkCompany}/staff/{pkCompanyStaff}/autoresponse")
    public ResponseEntity<?> getAutoResponse(
            @PathVariable String apiVer,
            @PathVariable Long fkCompany,
            @PathVariable Long pkCompanyStaff
    ) {
        return ResponseEntity.ok(secretaryService.getAutoResponse(fkCompany, pkCompanyStaff));
    }

    /**
     * 손비서 자동 자리비움 검색 조회
     * @param apiVer
     * @param fkCompany
     * @param pkCompanyStaff
     * @param fdCompanyName
     * @param fdStaffName
     * @param fdStaffId
     * @param autoResponseYn
     * @return
     */
    @Deprecated
    @GetMapping("/autoresponse/search")
    public ResponseEntity<?> searchAutoResponse(
            @PathVariable String apiVer,
            @RequestParam(value = "fkCompany", required = false) Long fkCompany,
            @RequestParam(value = "pkCompanyStaff", required = false) Long pkCompanyStaff,
            @RequestParam(value = "fdCompanyName", required = false) String fdCompanyName,
            @RequestParam(value = "fdStaffName", required = false) String fdStaffName,
            @RequestParam(value = "fdStaffId", required = false) String fdStaffId,
            @RequestParam(value = "autoResponseYn", required = false) String autoResponseYn
    ) {
        return ResponseEntity.ok(secretaryService.searchAutoResponse(
                fkCompany,
                pkCompanyStaff,
                fdCompanyName,
                fdStaffName,
                fdStaffId,
                autoResponseYn)
        );
    }

    /**
     * 손비서 자동 자리비움 활성화
     * @param apiVer
     * @param fkCompany
     * @param pkCompanyStaff
     * @return
     */
    @Deprecated
    @PutMapping("/companies/{fkCompany}/staff/{pkCompanyStaff}/autoresponse/active")
    public ResponseEntity<?> activeAutoResponse(
            @PathVariable String apiVer,
            @PathVariable Long fkCompany,
            @PathVariable Long pkCompanyStaff
    ) {
        return ResponseEntity.ok(secretaryService.activeAutoResponse(fkCompany, pkCompanyStaff));
    }

    /**
     * 손비서 자동 자리비움 비활성화
     * @param apiVer
     * @param fkCompany
     * @param pkCompanyStaff
     * @return
     */
    @Deprecated
    @PutMapping("/companies/{fkCompany}/staff/{pkCompanyStaff}/autoresponse/inactive")
    public ResponseEntity<?> inactiveAutoResponse(
            @PathVariable String apiVer,
            @PathVariable Long fkCompany,
            @PathVariable Long pkCompanyStaff
    ) {
        return ResponseEntity.ok(secretaryService.inactiveAutoResponse(fkCompany, pkCompanyStaff));
    }

    @Deprecated
    @PutMapping("/companies/{fkCompany}/staff/{pkCompanyStaff}/response/mode")
    public ResponseEntity<?> updateResStatus(
            @PathVariable Long fkCompany
            ,@PathVariable Long pkCompanyStaff
            ,@RequestBody SecretaryDao req
    ) {
        return secretaryService.updateResStatus(fkCompany, pkCompanyStaff, req);
    }

    /**
     * 손비서 앱모드 조회
     * 활동중, 휴식중, 청취모드, 운전모드, 미팅모드, 해외출장모드, 휴가모드
     * @param memberSeq
     * @return
     */
    @GetMapping("/members/{memberSeq}/appmode")
    public ResponseEntity<ResponseApi> getAppMode(
            @PathVariable Long memberSeq
    ) {
        return secretaryService.getAppMode(memberSeq);
    }

    /**
     * 손비서 앱모드 수정
     * @param memberSeq
     * @return
     */
    @PutMapping("/members/{memberSeq}/appmode")
    public ResponseEntity<ResponseApi> setAppMode(
            @PathVariable Long memberSeq
            ,@RequestBody DaoSecretary req
    ) {
        req.setSeqMember(memberSeq);
        return secretaryService.setAppMode(req);
    }

    /**
     * 2024-05-16 발신번호 승인정보 조회 구현.
     * 급하게 만든 것으로 리팩토링 반드시 필요
     * @param seqMember
     * @return
     */
    @GetMapping("/members/{seqMember}/outbound/agreement")
    public ResponseEntity<ResponseApi> findExtOb(
            @PathVariable Long seqMember
    ) {
        return secretaryService.findExtOb(seqMember);
    }

    /**
     * 2024-05-16 발신번호 승인정보 수정 구현.
     * 급하게 만든 것으로 리팩토링 반드시 필요
     * @param req
     * @return
     */
    @PutMapping("/members/{seqMember}/outbound/agreement")
    public ResponseEntity<ResponseApi> setExtOb(
            @PathVariable Long seqMember
            ,@RequestBody DaoExtensionOb req
    ) {
        req.setSeqMember(seqMember);
        return secretaryService.setExtOb(req);
    }

    /**
     * 손비서 전용번호 조회
     * @param seqCompany
     * @param seqStaffAi
     * @return
     */
    @GetMapping("/companies/{seqCompany}/ais/{seqStaffAi}/extensions")
    public ResponseEntity<ResponseApi> findExtensions(
            @PathVariable Long seqCompany
            ,@PathVariable Long seqStaffAi
    ) {
        return secretaryService.findExtensions(seqCompany, seqStaffAi);
    }

    /**
     * 전화번호 사용여부 설정
     * 급하게 만든 건으로 반드시 수정 필요
     * @param seqMember
     * @param req
     * @return
     */
    @PutMapping("/members/{seqMember}/mobile-number/usage")
    public ResponseEntity<ResponseApi> setMobileMainYn(
            @PathVariable Long seqMember
            ,@RequestBody DaoExtension req
    ) {
        return secretaryService.setMobileMainYn(seqMember, req);
    }

    /**
     * 표시할 발신번호 조회
     * @param seqMember
     * @return
     */
    @GetMapping("/members/{seqMember}/outbound/number")
    public ResponseEntity<ResponseApi> findOutboundNumber(
            @PathVariable Long seqMember
    ) {
        return secretaryService.findOutboundNumber(seqMember);
    }

    /**
     * 표시할 발신번호 수정
     * @param seqMember
     * @param req
     * @return
     */
    @PutMapping("/members/{seqMember}/outbound/number")
    public ResponseEntity<ResponseApi> setOutboundNumber(
            @PathVariable Long seqMember
            ,@RequestBody DaoExtension req
    ) {
        return secretaryService.setOutboundNumber(seqMember, req);
    }
}
