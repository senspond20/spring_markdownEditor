package com.sensweb.myapp.web.wysiwyg.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sensweb.myapp.common.utils.FileUtils;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

public class TestController {

   
    public static void main(String[] args) throws JsonMappingException {

         List<String> values0 = new ArrayList<String>() {
            {
                add("1");
                add("2");
                add("3");
            }
        };

        List<String> values1 = new ArrayList<>(Arrays.asList(new String[]{ "1", "2", "3" }));

        // JDK 5 이상에서 사용 가능
        List<String> values2 = new ArrayList<>(Arrays.asList("1", "2", "3"));
        
        System.out.println(values0);
        System.out.println(values1);
        System.out.println(values2);
        JSONObject jo1 = new JSONObject();

        jo1.put("title", "제목1");
        
        jo1.put("content", "내용1");
        
        
        String jsonStr = jo1.toJSONString();
        
        System.out.println(jsonStr);
        
   //     Map<String,String> a = new ObjectMapper().readValue("{\"title\":\"제목1\",\"content\":\"내용1\"}", Map.class);
        System.out.println(jo1);
    
    

        
   
    }
}
