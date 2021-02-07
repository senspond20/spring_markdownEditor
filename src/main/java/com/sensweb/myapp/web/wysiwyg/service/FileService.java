package com.sensweb.myapp.web.wysiwyg.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import com.sensweb.myapp.common.model.FileSaveResponseDto;

public interface FileService {
   
  
    public FileSaveResponseDto saveFile(MultipartFile file);

    public List<FileSaveResponseDto> saveFiles(MultipartFile[] files);

    public Resource load(String filename);

    public void deleteAll();

    public List<String> loadAll();
}
