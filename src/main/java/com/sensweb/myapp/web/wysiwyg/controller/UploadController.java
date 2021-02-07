package com.sensweb.myapp.web.wysiwyg.controller;

import com.sensweb.myapp.common.model.FileInfoVO;
import com.sensweb.myapp.common.model.FileIsSaveDto;
import com.sensweb.myapp.common.utils.FileSaveUtils;
import com.sensweb.myapp.common.utils.SystemAccessUtils;
import com.sensweb.myapp.web.wysiwyg.service.UploadFileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RestController
// @RequestMapping("/api")
public class UploadController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("UploadFileService")
    private UploadFileService upService;

    @PostMapping("/uploadfile")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile){

        // 1. creating fileVO
        FileIsSaveDto uploadInfo = upService.saveFile(multipartFile);

        logger.debug("파일 저장 성공 여부 : {}", uploadInfo.isSaveSuccess());
        logger.debug("파일 정보 : {}", uploadInfo.getFileInfo().toStringMultiline());

        // logger.debug(uploadInfo);

        // 2. fileVO to file save servicer

        // 3. success or fail ?
        
        // 4. return
        return ResponseEntity.ok().body(uploadInfo);
    }
}
