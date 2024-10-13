package com.aice.controller;

import com.aice.dao.DaoAiWork;
import com.aice.dao.ResponseApi;
import com.aice.service.AiWorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Deprecated
@Slf4j
@RestController
@RequestMapping("/{apiVer}/ais")
public class ControllerAiWork {
    @Autowired
    AiWorkService aiWorkService;

    /**
     * work정보 조회
     * 2024-08-02 Description: 호출하는곳이 없음. 삭제 예정
     * @param aiStaffSeq
     * @param aiWorkCd
     * @return
     */
    @GetMapping("/{aiStaffSeq}/work")
    public ResponseEntity<ResponseApi> findAiWork(
            @PathVariable Long aiStaffSeq
            ,@RequestParam(value = "aiWorkCd", required = false) String aiWorkCd
    ) {
        return aiWorkService.findAiWork(aiWorkCd, aiStaffSeq);
    }

    /**
     * work정보 수정
     * 2024-08-02 Description: 호추랄는곳이 없음. 삭제 예정
     * @param aiStaffSeq
     * @param req
     * @return
     */
    @PutMapping("/{aiStaffSeq}/work")
    public ResponseEntity<ResponseApi> setAiWork(
            @PathVariable Long aiStaffSeq
            ,@RequestBody DaoAiWork req
    ) {
        req.setAiStaffSeq(aiStaffSeq);
        return aiWorkService.setAiWork(req);
    }
}
