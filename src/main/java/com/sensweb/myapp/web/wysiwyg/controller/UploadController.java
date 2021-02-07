package com.sensweb.myapp.web.wysiwyg.controller;

import com.sensweb.myapp.common.utils.SystemAccessUtils;
import com.sensweb.myapp.common.vo.FileInfoVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
// @RequestMapping("/api")
public class UploadController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/uploadfile")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile){

        // 1. creating fileVO
        FileInfoVO fileInfo = new FileInfoVO(multipartFile);
        logger.debug(fileInfo.toStringMultiline());

        // 2. fileVO to file save servicer

        // 3. success or fail ?
        
        // 4. return
        return ResponseEntity.ok().body(fileInfo);
    }
}
