package com.sensweb.myapp.common.model;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;

@Getter
public class FileInfoVO implements Serializable {
    private static final long serialVersionUID = 4241988244056179672L;

    private String originfileName;
    private String renameFileName;
    private String contentType;
    private long size;
    private String filePath;

    public FileInfoVO() { }

    public FileInfoVO(MultipartFile file, String filePath) {
        this.originfileName = file.getOriginalFilename();
        this.renameFileName = renameFileName();
        this.size = file.getSize();
        this.contentType = file.getContentType();
        this.filePath = filePath;
    }
    
    /* 
        파일 저장경로가 포함된 파일객체를 리턴한다.
    */
    public File getSaveFile(){
        return new File(this.filePath + "/" + this.renameFileName);
    }
    /*
        toString
    */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }    
    public String toStringMultiline() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /* 
        파일 이름 변경
        randomUUID + originFileName으로 하지 않는 이유
        한글 이름이 포함된 파일을 저장하게 될 경우
     */
    private String renameFileName() {
        String uuid = UUID.randomUUID().toString().substring(0, 16);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd_HHmmSS");
        String time = dateFormat.format(System.currentTimeMillis());

        // 확장자를 붙이기 위해 원본 파일명의 마지막 인덱스에서 '.' 을 가져와 잘라 붙인다..
        int dot = this.originfileName.lastIndexOf("."); 
		String ext = (dot!=-1) ? this.originfileName.substring(dot) : "";	

        this.renameFileName = uuid + "-" + time + ext;
        return renameFileName;
    }
        
}
