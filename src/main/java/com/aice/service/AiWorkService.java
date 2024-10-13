package com.aice.service;

import com.aice.dao.DaoAiWork;
import com.aice.dao.ResponseApi;
import com.aice.repo.RepoAiConfWork;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Deprecated
@Slf4j
@Service
public class AiWorkService {
    @Autowired
    RepoAiConfWork repoAiConfWork;

    public ResponseEntity<ResponseApi> findAiWork(
            String aiWorkCd
            ,Long aiStaffSeq
    ) {
        try {
            DaoAiWork res = repoAiConfWork.findAiWork(aiWorkCd, aiStaffSeq);
            return ResponseEntity.ok(ResponseApi.success("findAiWork success", res));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Failed to retrieve.", e.getMessage()));
        }
    }

    public ResponseEntity<ResponseApi> setAiWork(
            DaoAiWork req
    ) {
        Map<String, Object> map = new HashMap<>();
        try {
            int cnt = repoAiConfWork.updateAiWork(req);
            map.put("updateCnt", cnt);
            return ResponseEntity.ok(ResponseApi.success("setAiWork success", map));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseApi.error("Failed to update.", e.getMessage()));
        }
    }

}
