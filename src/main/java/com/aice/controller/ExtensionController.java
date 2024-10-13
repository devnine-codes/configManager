package com.aice.controller;

import com.aice.dao.ResponseApi;
import com.aice.dao.extension.DaoExtension;
import com.aice.repo.RepoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aice.service.ExtensionService;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;

@Slf4j
@RestController
@RequestMapping("/{apiVer}/extensions")
public class ExtensionController {
	@Autowired
    RepoExtension repoExtension;
    @Autowired
    ExtensionService extensionService;

    /**
     * Exntension 번호 생성
     */
    @PostMapping(value="",consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveExtension(
            @PathVariable String apiVer,
            @RequestBody DaoExtension req
    ) {
        return ResponseEntity.ok(extensionService.addExtension(req));
    }

    /**
     * Extension 번호 정보조회
     */
    @GetMapping(value="/{extNum}")
    public ResponseEntity<?> getExtension(
            @PathVariable String apiVer,
            @PathVariable String extNum,
            @RequestParam(required = false) String dnisType
    ) {
        return ResponseEntity.ok(extensionService.getExtension(extNum, dnisType));
    }

    /**
     * Extension 검색 조회
     * @param apiVer
     * @return
     */
    @GetMapping(value="")
    public ResponseEntity<?> searchExtension(
            @PathVariable String apiVer,
            @RequestParam(required = false) Long pkConfDialExtAihandy,
            @RequestParam(required = false) Long fkCompany,
            @RequestParam(required = false) Long fkCompanyStaff,
            @RequestParam(required = false) String dnis,
            @RequestParam(required = false) String extNum,
            @RequestParam(required = false) String dialNum,
            @RequestParam(required = false) String fullDnis
    ) {
        return ResponseEntity.ok(
                extensionService.searchExtension(
                        pkConfDialExtAihandy,
                        fkCompany,
                        fkCompanyStaff,
                        dnis,
                        extNum,
                        dialNum,
                        fullDnis
                ));
    }

    /**
     * Exntension 번호 수정
     */
    @PutMapping(value="/",consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateHandDnis(@RequestBody DaoExtension req) {
        return ResponseEntity.ok(extensionService.setExtension(req));
    }

    /**
     * Exntension 번호 삭제
     * 2024-05-17. aihandy Type이 생기면서 body정보를 받게 수정해야함
     */
    @DeleteMapping(value="/{extNum}")
    public ResponseEntity<?> deleteExtension(
            @PathVariable String apiVer,
            @PathVariable String extNum
    ) {
        return ResponseEntity.ok(extensionService.removeExtension(extNum));
    }

    /**
     * Exntension 번호 삭제 (회사기준)
     */
    @DeleteMapping(value="/companies/{id}")
    public ResponseEntity<?> deleteExtension(
            @PathVariable String apiVer,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(extensionService.deleteExtensionByCompany(id));
    }

    /**
     * Extension 착신전환 활성화
     */
    @PutMapping(value="/{extNum}/forward/{status}")
    public ResponseEntity<?> updateFwdYn(
            @PathVariable String apiVer
            ,@PathVariable String extNum
            ,@PathVariable String status
    ) {
        return ResponseEntity.ok(extensionService.updateFwdYn(extNum, status));
    }

}
