package com.sensweb.myapp.web.wysiwyg.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import com.sensweb.myapp.common.model.FileSaveResponseDto;

public interface FileService {
   
  
    public FileSaveResponseDto saveFile(MultipartFile file);

    public List<FileSaveResponseDto> saveFiles(MultipartFile[] files);

    public Resource load(String fileName);

    public void deleteAll();
    
    public void deleteFile(String fileName);

    public List<String> loadAll();
}
