package com.aice.controller;

import com.aice.dao.ment.DaoMent;
import com.aice.service.MentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/{apiVer}/ment")
public class MentController {

    @Autowired
    MentService mentService;

    /**
     * 등록
     * @param entityType
     * @param id
     * @param daoMent
     * @return
     */
    @PostMapping("/{entityType}/{id}")
    public ResponseEntity<?> addMessage(
            @PathVariable String entityType
            ,@PathVariable Long id
            ,@RequestBody DaoMent daoMent
    ) {
        return ResponseEntity.ok(mentService.addMessage(entityType, id, daoMent));
    }

    /**
     * 조회
     * @param entityType
     * @param id
     * @return
     */
    @GetMapping("/{entityType}/{id}")
    public ResponseEntity<?> findMessage(
            @PathVariable String entityType
            ,@PathVariable Long id
    ) {
        return ResponseEntity.ok(mentService.findMessage(entityType, id));
    }

    /**
     * 수정
     * @param entityType
     * @param id
     * @param daoMent
     * @return
     */
    @PutMapping("/{entityType}/{id}")
    public ResponseEntity<?> setMessage(
            @PathVariable String entityType
            ,@PathVariable Long id
            ,@RequestBody DaoMent daoMent
    ) {
        return ResponseEntity.ok(mentService.setMessage(entityType, id, daoMent));
    }

    /**
     * 삭제
     * @param entityType
     * @param id
     * @return
     */
    @DeleteMapping("/{entityType}/{id}")
    public ResponseEntity<?> deleteMessage(
            @PathVariable String entityType
            ,@PathVariable Long id
    ) {
        return ResponseEntity.ok(mentService.deleteMessage(entityType, id));
    }

    /**
     * AI팀에서 02:00에 배치를 돌려 ment정보를 조회해갈때 사용
     * @return
     */
    @GetMapping("/modified")
    public ResponseEntity<?> listByModifyDate() {
        return ResponseEntity.ok(mentService.listByModifyDate());
    }
}
