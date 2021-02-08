package com.sensweb.myapp.web.wysiwyg.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
     * 다중 파일 업로드
     */
    @PostMapping("/uploads")
    public ResponseEntity<?> uploadFiles(MultipartHttpServletRequest multi) {
        List<MultipartFile> fileList = multi.getFiles("files");
        logger.debug("@@@ : " + fileList);
        int count = 1;
        for(MultipartFile file : fileList){
            logger.debug("============= file {} ==============", count);
            logger.debug("fileName {}",file.getOriginalFilename());
            logger.debug("fileSiz {}",file.getSize());
            logger.debug("fileContentType {}",file.getContentType());
            logger.debug("fileResurce {}",file.getResource());
            count++;
        }
       
     //   logger.debug("resourse {}", upService.load(fsd.getFileInfo().getRenameFileName()));
        return ResponseEntity.ok().body("ok");
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


    @GetMapping("/loadAll2")
    public List<Map<String,Object>> loadAll(HttpServletRequest request) {
    	
    	String dirName = "D:\\logs";
    	   File dir = new File(dirName);   
           List<Map<String,Object>> fileList = new ArrayList<Map<String,Object>>();
           Map<String,Object> map = new HashMap<String, Object>();

           File[] files = dir.listFiles();
           //디렉토리의 파일목록(디렉토리포함)을 File 배열로 반환
           for(File f: files){
               // 디렉토리가 아니면
               if(!f.isDirectory()){
                
            	   map.put("name", f.getName());
        	   	   map.put("bytes", f.length());
        	   	   fileList.add(map);
               }
           }
           return fileList;
    }
    

}
