package com.sensweb.myapp.web.wysiwyg.service;


import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import com.sensweb.myapp.common.model.FileIsSaveDto;

public interface UploadFileService {
   
    public FileIsSaveDto saveFile(MultipartFile file);

    public List<FileIsSaveDto> saveFiles(MultipartFile[] files);
}
