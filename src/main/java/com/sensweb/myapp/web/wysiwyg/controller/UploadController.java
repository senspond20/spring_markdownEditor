package com.sensweb.myapp.web.wysiwyg.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import com.sensweb.myapp.common.model.FileSaveResponseDto;

import com.sensweb.myapp.web.wysiwyg.service.FileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class UploadController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("fileService")
    private FileService upService;

    /*
     * 파일 업로드
     */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        FileSaveResponseDto fsd = upService.saveFile(file);

        logger.debug("파일 저장 성공 여부 : {}", fsd.isSaveSuccess());
        logger.debug("파일 정보 : {}", fsd.getFileInfo().toStringMultiline());
     //   logger.debug("resourse {}", upService.load(fsd.getFileInfo().getRenameFileName()));
        return ResponseEntity.ok().body(fsd);
    }

    /*
     * 모든 파일 삭제
     */
    @GetMapping("/deleteAll")
    public ResponseEntity<?> deleteAllfiles() {
        upService.deleteAll();
        return ResponseEntity.ok().body(upService.loadAll());
    }

    /*
     * 파일 삭제
     */
    @PostMapping("/delete")
    public ResponseEntity<?> deletefiles(@RequestParam String fileName) {
        upService.deleteFile(fileName);
        return ResponseEntity.ok().body(upService.loadAll());
    }
    /*
        업로드한 파일 목록 
    */
    @GetMapping("/loadAll")
    public ResponseEntity<?> loadAllFiles(){
        return ResponseEntity.ok().body(upService.loadAll());
    }
}
