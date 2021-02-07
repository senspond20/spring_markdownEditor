package com.sensweb.myapp.common.vo;

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

    public FileInfoVO() { }

    public FileInfoVO(MultipartFile file) {
        this.originfileName = file.getOriginalFilename();
        this.renameFileName = renameFileName();
        this.size = file.getSize();
        this.contentType = file.getContentType();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }    
    public String toStringMultiline() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    private String renameFileName() {
        String uuid = UUID.randomUUID().toString().substring(0, 16);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd_HHmmSS");
        String time = dateFormat.format(System.currentTimeMillis());
        return uuid + "-" + time;
    }
    
    
}
