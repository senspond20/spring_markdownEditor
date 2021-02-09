package com.sensweb.myapp.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.icu.math.BigDecimal;
import com.sensweb.myapp.common.model.CommandMapDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FileUtils {

    @Autowired
    private ResourceLoader resourceLoader;

    /*
        Resource 경로를 가져온다. (target 폴더 아래에 생긴다.)
    */
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

    /*
        자바 Object 를 JsonStr 로 변환한다. (Jackson ObjectMapper)
        Spring Framework 를 STS로 생성시 Jackson 라이브러리가 빠져있지만
        Spring Boot 는 기본으로 내장되어 있다.
    */
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
    public Map<String,Object> objectToMapFromObjectMapper(Object obj){
        Map<String,Object> map = null;
        try { 
         map = new ObjectMapper().convertValue(obj, Map.class);
        }catch(Exception e){
            throw new RuntimeException();
        }
        return map;
    }

    public Map<String,Object> objectToMap(Object obj, Map<String,Object> map){
        Class<?> cls = obj.getClass();

        for (Field field : cls.getDeclaredFields()) {
            field.setAccessible(true);
 
            Object value = null;
            String key;
 
            try {
                key = field.getName();
                value = field.get(obj);

                if (value!=null) {
                    map.put(key, value);
                } else if (value instanceof BigDecimal) {
                    map.put(key, value);
                } else {
                    objectToMap(value, map);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
            
            }
        
     
        }
        return map;
    }
    

    public CommandMapDto objectToCommandMapDto(Object obj){
        CommandMapDto dto = new CommandMapDto();
            dto.putAll(objectToMapFromObjectMapper(obj));
        return dto;
    }

    /*
        파일 경로에 폴더가 없으면 생성하고 저장할 파일객체 만들어 리턴.
    */
    private File mkExcheckAndgetFile (String filePath, String fileName){
        File d = new File(filePath);
            if(!d.exists()){
                d.mkdirs();
            }
        return new File(Paths.get(filePath.concat("/").concat(fileName)).toString());
    }
 
    /*
        FileSave : Java Io /       파일 저장
    */
    public void saveFile(String filePath, String fileName, String data){
        File f = mkExcheckAndgetFile(filePath, fileName);
        try
        (
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        ) {
            // System.out.printf("filepath %s \n", f.toURI().toString());
            writer.write(data);
            writer.close();
          } catch (IOException e) {
              throw new RuntimeException();
          }
    }

    /*
        자바 객체를 Json File로 저장. (Jackson ObjectMapper)
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


    /*
        파일경로에서 파일 목록을 가져온다.
    */
    public List<String> loadFileList(String path, boolean includeDirectory) {
		List<String> list = new ArrayList<String>();
		File f = new File(path);
		File[] fileList = null;
        
		if(f.exists()) {
			fileList = f.listFiles();
            
            if(includeDirectory){
                for(File ff : fileList) { // 리렉토리 인것도 추가
                   list.add(ff.getName());
                }	
            }else{
                for(File ff : fileList) {
                    if(! ff.isDirectory()){ // 리렉토리가 아닌 목록만 추가
                        list.add(ff.getName());
                    }	
                }	
            }
		}	
		return list;
	}

}
