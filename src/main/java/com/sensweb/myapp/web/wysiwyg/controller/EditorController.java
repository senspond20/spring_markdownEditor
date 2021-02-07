package com.sensweb.myapp.web.wysiwyg.controller;

import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/editor")
public class EditorController {
    
    private final String baseURl = "wywiwyg/";
    
    @GetMapping("")
    @Description("select your editor ..")
    public String home () {
        return baseURl + "index";
    }

    @GetMapping("/basic1")
    @Description("basic fileupload example1 : singlefile page")
    public String basic1(){
        return baseURl + "basic1";
    }

    @GetMapping("/basic2")
    @Description("basic fileupload example2 : multifile page")
    public String basic2(){
        return baseURl + "basic2";
    }

    @GetMapping("/myeditor")
    @Description("my wywiwyg editor page")
    public String myEditor(){
        return baseURl + "myeditor";
    } 

    @GetMapping("/toastui")
    @Description("toastui editor page")
    public String toastuiEditor(){
        return baseURl + "toastui";
    }

    @GetMapping("/summernote")
    @Description("summernote editor page")
    public String summernoteEditor(){
        return baseURl + "summernote";
    }

}
