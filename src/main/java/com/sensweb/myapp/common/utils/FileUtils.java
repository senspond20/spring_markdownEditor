package com.sensweb.myapp.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensweb.myapp.common.model.CommandMapDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class FileUtils {

    @Autowired
    private ResourceLoader resourceLoader;

    public String getResoucePath(String path) {
        Resource resource = resourceLoader.getResource(path);
        String realPath = "";
        try {
            realPath = resource.getURL().getPath();
        } catch (IOException e) {
            throw new RuntimeException("경로를 찾을 수가 없습니다.");
        }
        return realPath;
    }

    public String getStaticPath() {
        return getResoucePath("classpath:/static");
    }

    public String getStaticDataPath() {
        return getResoucePath("classpath:/static/data");
    }

    public String ObjectToJsonStr(Object obj, boolean pretty) {
        String jsonStr = "";
        ObjectMapper om = new ObjectMapper();
        try {
            jsonStr = (pretty == false) 
                    ? om.writeValueAsString(obj)
                    : om.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
        return jsonStr;
    }
    
    /*
        Java 객체를 commandMap Dto로 변환
    */
    public CommandMapDto objectToCommandMapDto(Object obj){
        return new ObjectMapper().convertValue(obj, CommandMapDto.class);
    }

    private File mkExcheckAndgetFile (String filePath, String fileName){
        File d = new File(filePath);
        if(!d.exists()){
            d.mkdirs();
        }
        return new File(filePath + "/" + fileName);
    }

    /*
        FileSave : Java Io
    */
    public void saveFile(String filePath, String fileName, String data){
        File f = mkExcheckAndgetFile(filePath, fileName);
        try
        (
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        ) {
            System.out.println("fff " + f.toURI().toString());
            writer.write(data);
            writer.close();
          } catch (IOException e) {
              throw new RuntimeException();
          }
    }

    /*
        FileSave : Jackson ObjectMapper
    */
    public void saveObjToJsonFile(String filePath, String fileName, Object obj){
        File f = mkExcheckAndgetFile(filePath,fileName);
        ObjectMapper om = new ObjectMapper();
        try {
            om.writeValue(f, obj);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
