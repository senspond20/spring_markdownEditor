package com.sensweb.myapp.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sensweb.myapp.common.model.FileInfoVO;
import com.sensweb.myapp.common.model.FileSaveResponseDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import lombok.NoArgsConstructor;

/*
    FileInfoVO 에 의존성
*/
@NoArgsConstructor
public class FileSaveUtils {
     private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /*
       폴더가 존재하지 않으면 생성
    */
    private void dirExistCheakAndMakeDir(String filePath){
        File dir = new File(filePath); 
        if(!dir.exists()) {
            dir.mkdirs();
            logger.debug("[{}] directory not exists ==> make directory", filePath);
        }
    }

    /*
       파일 한개 저장
       FileIsSaveDto 리턴
    */
    public FileSaveResponseDto saveFile(MultipartFile saveFile, String filePath){
        dirExistCheakAndMakeDir(filePath) ; 

        Map<String, Object> resultMap = new HashMap<String, Object>();

        FileInfoVO file = new FileInfoVO(saveFile, filePath);

        if(!saveFile.isEmpty()){
            try { 
                saveFile.transferTo(file.getSaveFile()); 
                return new FileSaveResponseDto(true, file);

            }catch (Exception e) { 
                logger.debug("[{}] file save fail : {}" , file.toString(), e.getMessage());
                resultMap.put("success", false);
                resultMap.put("fileInfo", file);
                return new FileSaveResponseDto(false, file);
            }
        }else{
            logger.debug("MultipartFile empty");
            return new FileSaveResponseDto(false, file);
        }
    }

    /*
       파일 한개 저장
       성공시 : true 리턴
       실패시 : false 리턴
    */
    public boolean saveFileBoolean(MultipartFile files, String filePath){
        dirExistCheakAndMakeDir(filePath) ; 
        if(!files.isEmpty()){
            FileInfoVO file = new FileInfoVO(files, filePath);
            try { 
                files.transferTo(file.getSaveFile()); 
                return true; 
            }catch (Exception e) { 
                logger.debug("[{}] file save fail : {}" , file.toString(), e.getMessage());
                return false; 
            }
        }else{
            logger.debug("MultipartFile empty");
            return false;
        }
    }

    /*
       여러개 파일 저장
       모두 성공시 : true 리턴
       하나라도 실패시 : false 리턴
    */
    public boolean saveFilesBoolean(MultipartFile[] saveFiles, String filePath){
        boolean check = true;
        dirExistCheakAndMakeDir(filePath) ;
        for(MultipartFile f : saveFiles) { 
            // true && true && true ... 되야 true 가 된다.
            check = check && saveFileBoolean(f, filePath); 
            if(!check) break;
        } 
        return check;
    }

    /*
       여러개 파일 저장
       List<FileSaveResponseDto> 리턴
    */
    public List<FileSaveResponseDto> saveFiles(MultipartFile[] saveFiles, String filePath) {

        dirExistCheakAndMakeDir(filePath) ;
        List<FileSaveResponseDto> uploadFileList = new ArrayList<FileSaveResponseDto>();
        for(MultipartFile f : saveFiles) { 
            uploadFileList.add(new FileSaveResponseDto(saveFileBoolean(f, filePath), new FileInfoVO(f, filePath) ));
        } 
        return uploadFileList;
    }

 
}
