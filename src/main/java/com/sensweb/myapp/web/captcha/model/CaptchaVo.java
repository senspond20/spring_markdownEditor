package com.sensweb.myapp.web.captcha.model;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.Captcha.Builder;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.text.producer.NumbersAnswerProducer;

public class CaptchaVo{
    private int width = 150;
    private int height = 150;
    private int size = 6; 
    public CaptchaVo() {
    
    }
    public CaptchaVo(int width, int height, int size) {
        this.width = width;
        this.height = height;
        this.size = size;
    }
    public Captcha getCaptcha() {
        Builder captcha = new Captcha.Builder(this.width, this.height)
                                     .addText(new NumbersAnswerProducer(this.size));
        captcha.addNoise();
        captcha.addBorder().addBackground(new GradiatedBackgroundProducer());

        return captcha.build();
     }
}
