package com.sensweb.myapp.web.wysiwyg.service;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jdk.internal.jline.internal.Log;

import com.sensweb.myapp.common.utils.FileSaveUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.FileSystemUtils;



import com.sensweb.myapp.common.model.FileSaveResponseDto;

@Service("fileService")
public class FileServiceImpl implements FileService{
    
    // private final String FILE_ROOT = "d:/upload";
    private final Path FILE_ROOT = Paths.get("d:/upload");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public FileSaveResponseDto saveFile(MultipartFile file){
        FileSaveResponseDto fileIsSaveDto = new FileSaveUtils().saveFile(file, FILE_ROOT.normalize().toString());
          if(fileIsSaveDto.isSaveSuccess()){
            // fileIsSaveDto를 Dao에 전달  DB에 저장
          }
        return fileIsSaveDto;
    }
    @Override
    public List<FileSaveResponseDto> saveFiles(MultipartFile[] files){
        List<FileSaveResponseDto> fileIsSaveDto = new FileSaveUtils().saveFiles(files, FILE_ROOT.normalize().toString());
        
        fileIsSaveDto.forEach(item ->{
            if(item.isSaveSuccess()){
                // db 저장 list
            }
        });
        // 저장에 성공한 파일목록만 batch 쿼리로 DB에 저장
        return fileIsSaveDto;
    }
    
    @Override
    public Resource load(String filename) {
        try {
            Path file = FILE_ROOT.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
      
            if (resource.exists() || resource.isReadable()) {
              return (Resource) resource;
            } else {
                logger.debug("Could not read the file!");
                return null;
            }
          } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
          }
    }
    @Override
    public void deleteAll(){
        FileSystemUtils.deleteRecursively(FILE_ROOT.toFile());
        logger.debug("파일이 삭제되었습니다.");
    }

    @Override
    public List<String> loadAll() {
        List<String> fileList = new ArrayList<>();

      try {
            Stream<Path> stream = Files.walk(FILE_ROOT, 1).filter(path -> !path.equals(FILE_ROOT)).map(FILE_ROOT::relativize);
            stream.forEach(item -> fileList.add(item.normalize().toString()));
            return fileList;
      } catch (IOException e) {
            logger.debug("파일이 존재하지 않습니다.");
            return fileList;
      }
    }
    
}
