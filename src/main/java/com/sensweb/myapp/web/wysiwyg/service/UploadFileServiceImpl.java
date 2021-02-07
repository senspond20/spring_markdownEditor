package com.sensweb.myapp.web.wysiwyg.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.sensweb.myapp.common.utils.FileSaveUtils;
import java.util.List;
import com.sensweb.myapp.common.model.FileIsSaveDto;

@Service("UploadFileService")
public class UploadFileServiceImpl implements UploadFileService{
    
    private final String FILE_SAVE_PATH = "d:/upload";

    public FileIsSaveDto saveFile(MultipartFile file){
          return new FileSaveUtils().saveFile(file, FILE_SAVE_PATH);
    }

    public List<FileIsSaveDto> saveFiles(MultipartFile[] files){
        return new FileSaveUtils().saveFiles(files, FILE_SAVE_PATH);
    }
    
}
