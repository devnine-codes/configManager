/**
 * 활동상태 확인
 */

package com.aice.controller;

import com.aice.dao.ResponseApi;
import com.aice.dao.presence.DaoPresence;
import com.aice.service.PresenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/{apiVer}/companies")
public class PresenceController {

    @Autowired PresenceService presenceService;

    @GetMapping("/{seqCompany}/ais/{seqStaffAi}/activation")
    public ResponseEntity<ResponseApi> findAiWorkStatus(
            @PathVariable Long seqCompany
            ,@PathVariable Long seqStaffAi
    ) {
        return presenceService.findAiWorkStatus(seqCompany, seqStaffAi);
    }

    @PutMapping("/{seqCompany}/ais/{seqStaffAi}/activation")
    public ResponseEntity<ResponseApi> setAiWorkStatus(
            @PathVariable Long seqCompany
            , @PathVariable Long seqStaffAi
            , @RequestBody DaoPresence req
            ) {
        return presenceService.setAiWorkStatus(seqCompany, seqStaffAi, req);
    }

    @PostMapping("/{seqCompany}/members/{seqMember}/presence")
    public ResponseEntity<ResponseApi> findMemberStatus(
            @PathVariable Long seqCompany
            ,@PathVariable Long seqMember
            ,@RequestBody DaoPresence req
    ) {
        return presenceService.addAiWorkStatus(seqCompany, seqMember, req);
    }

    /**
     * tbl_member_res_status의 res_status를 조회 한다.
     * 2024-05-16 기준 손비서의 경우 appmode 조회 api를 이용해야 하므로 손비서에선 사용해선 안된다.
     * @param seqCompany
     * @param solutionType
     * @return
     */
    @GetMapping("/{seqCompany}/presence")
    public ResponseEntity<ResponseApi> findMemberStatus(
            @PathVariable Long seqCompany
            ,@RequestParam(value = "solutionType", required = false) String solutionType
    ) {
        return presenceService.findMemberStatus(seqCompany, solutionType);
    }

    /**
     * tbl_member_res_status의 res_status를 수정 한다.
     * 2024-05-16 기준 손비서의 경우 appmode 수정 api를 이용해야 하므로 손비서에선 사용해선 안된다.
     * 2024-08-05 여전히 appmode와 presence를 같이 호출하고 있음.
     * @param seqCompany
     * @param req
     * @return
     */
    @PutMapping("/{seqCompany}/presence")
    public ResponseEntity<ResponseApi> setMemberStatus(
            @PathVariable Long seqCompany
            ,@RequestBody DaoPresence req
    ) {
        return presenceService.setMemberStatus(seqCompany, req);
    }

    @GetMapping("/{seqCompany}/forward")
    public ResponseEntity<ResponseApi> findCallForwardStatus(
            @PathVariable Long seqCompany
    ) {
        return presenceService.findCallForwardStatus(seqCompany);
    }

    @PutMapping("/{seqCompany}/forward")
    public ResponseEntity<ResponseApi> setCallForwardStatus(
            @PathVariable Long seqCompany
            ,@RequestBody DaoPresence req
    ) {
        return presenceService.setCallForwardStatus(seqCompany, req);
    }
}
