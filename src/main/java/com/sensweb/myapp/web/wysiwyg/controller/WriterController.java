package com.sensweb.myapp.web.wysiwyg.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class WriterController {
    
    @GetMapping("/post/insert")
	@ResponseBody
	public Object insert(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
		System.out.println("#####" + file);
		System.out.println("getOriginalFilename : " + file.getOriginalFilename());
		System.out.println("getSize : " + file.getSize());
		System.out.println("getContentType : " + file.getContentType());
		
//		request.getParameter("content");
		return "hello";
	}
}
