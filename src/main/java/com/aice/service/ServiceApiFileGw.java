package com.aice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.aice.conf.ConfFeign;
import com.aice.dao.DaoFileLog;

@FeignClient(
    name="api-file-gw"
    ,url="${file-gw.hostname}"
    ,decode404=true
    ,configuration={ConfFeign.class}
)
public interface ServiceApiFileGw {
    @PostMapping(
        value="upload/attach/file"
        ,consumes=MediaType.MULTIPART_FORM_DATA_VALUE
//        ,produces=MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<DaoFileLog> uploadAttachFile(
        @RequestPart(value="daoFileLog") DaoFileLog daoFileLog
        ,@RequestPart(value="pathFile") MultipartFile pathFile
    );
}
