package com.sensweb.myapp.common.utils;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.audio.AudioCaptcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.servlet.CaptchaServletUtil;
import cn.apiclub.captcha.text.producer.NumbersAnswerProducer;

public class CaptchaUtils {
    
    
    public Captcha captchaImg() throws Exception {
		final int WIDTH = 150; /* 보안문자 이미지 가로크기 */
		final int HEIGHT = 50; /* 보안문자 이미지 세로크기 */

		// 세션 및 체크변수 초기화
		// req.getSession().removeAttribute(Captcha.NAME);
		// checkKey = false;
		
		// 6글자로 생성
		Captcha captcha = new Captcha.Builder(WIDTH, HEIGHT)
				.addText(new NumbersAnswerProducer(6)).addNoise()
				.addBorder().addBackground(new GradiatedBackgroundProducer()).build();
        return captcha;
    }
    	/*
	 * -----------------------------------------------------------------------------
	 * -- captcha 이미지를 가져오는 메서드
	 * -----------------------------------------------------------------------------
	 */
	public void captchaImg(HttpServletRequest req, HttpServletResponse res) throws Exception {
		final int WIDTH = 150; /* 보안문자 이미지 가로크기 */
		final int HEIGHT = 50; /* 보안문자 이미지 세로크기 */

		// 세션 및 체크변수 초기화
		// req.getSession().removeAttribute(Captcha.NAME);
		// checkKey = false;
		
		// 6글자로 생성
		Captcha captcha = new Captcha.Builder(WIDTH, HEIGHT)
				.addText(new NumbersAnswerProducer(6)).addNoise()
				.addBorder().addBackground(new GradiatedBackgroundProducer()).build();

		/* JSP에서 Captcha 객체에 접근할 수 있도록 session에 저장 */
		req.getSession().setAttribute(Captcha.NAME, captcha);
		
		res.setHeader("Cache-Control", "private,no-cache,no-store");
		res.setContentType("image/png"); // PNGs allow for transparency.
		try {
			ImageIO.write(captcha.getImage(), "png", res.getOutputStream());
			res.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    /*
	 * -----------------------------------------------------------------------------
	 * -- captcha 음성 메시지를 가져오는 메서드
	 * -----------------------------------------------------------------------------
	 */

	// public void captchaAudio(HttpServletRequest req, HttpServletResponse res) throws Exception {		
	// 	HttpSession session = req.getSession(); 
	// 	Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME); 
	// 	String getAnswer = captcha.getAnswer();
	// 	if(getAnswer == null || getAnswer.equals("")) getAnswer = captcha.getAnswer(); 
	// 	AudioCaptcha audiocaptcha = new AudioCaptcha.Builder().addAnswer(new CustomTextProducer(getAnswer)).addNoise().build(); 
	// 	CaptchaServletUtil.writeAudio(res, audiocaptcha.getChallenge()); 
	// }
}
