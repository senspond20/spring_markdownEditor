package com.sensweb.myapp.web.test;

import javax.activation.CommandMap;

import com.sensweb.myapp.common.model.CommandMapDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

        @PostMapping("/api/test")
        public String test(@RequestBody String icon){

            System.out.println(icon);
            return "test";
        }


        @GetMapping("/api/command")
        public String test2(CommandMapDto cmap){

            System.out.println(cmap.getMap());
            return "test";
        }
}
