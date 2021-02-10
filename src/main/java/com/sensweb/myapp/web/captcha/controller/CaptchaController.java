package com.sensweb.myapp.web.captcha.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.text.producer.NumbersAnswerProducer;

@Controller
@RequestMapping("/api")
public class CaptchaController {
    
    private final String API_KEY = "API_KEY";

    @GetMapping("/test")
    public String captchaTest(){
        return "capcha/test";
    }

    private static void resWriter(HttpServletResponse response, String msg) {
		try {
			PrintWriter out = response.getWriter();
			out.append(msg);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	 * -----------------------------------------------------------------------------
	 * -- captcha 이미지를 가져오는 메서드
	 * -----------------------------------------------------------------------------
	 */
	@GetMapping("/getCaptchaImg")
	@ResponseBody
	public void captchaImg(HttpServletRequest req, HttpServletResponse res, HttpSession session) {
		final int WIDTH = 150; /* 보안문자 이미지 가로크기 */
		final int HEIGHT = 50; /* 보안문자 이미지 세로크기 */

        String apiKey = req.getHeader("API_KEY");
        if(apiKey == null){
            
        }
        System.out.println(apiKey);
        if(!apiKey.equals(API_KEY)){ 
            try {
                PrintWriter out = res.getWriter();
                out.append("권한이 없다");
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
                
        }else{
            // 세션 및 체크변수 초기화
            session.removeAttribute(Captcha.NAME);
                
            // 6글자로 생성
            Captcha captcha = new Captcha.Builder(WIDTH, HEIGHT)
                    .addText(new NumbersAnswerProducer(6)).addNoise()
                    .addBorder().addBackground(new GradiatedBackgroundProducer()).build();

            /* 클라이언트에서 Captcha 객체에 접근할 수 있도록 session에 저장 */
            session.setAttribute(Captcha.NAME, captcha);
            
          


            res.setHeader("Cache-Control", "private,no-cache,no-store");
            res.setContentType("image/png"); // PNGs allow for transparency.
            try {
                System.out.println(captcha.getImage());
                ImageIO.write(captcha.getImage(), "png", res.getOutputStream());
                res.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

	
	}
   
}
