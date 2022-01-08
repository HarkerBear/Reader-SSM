package reader.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class KaptchaController {
    @Resource
    private Producer kaptchaProducer;

    @GetMapping("/verify_code")
    public void createVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response expires immediately
        response.setDateHeader("Expire",0);
//        no cache for pics
        response.setHeader("Cache-Control","no-store,no-cache,must-revalidate");
        response.setHeader("Cache-Control","post-check=0,pre-check=0");
        response.setHeader("Pragma","no-cache");
        response.setContentType("image/png");
//        create text for verify code
        String verifyCode=kaptchaProducer.createText();
        request.getSession().setAttribute("kaptchaVerifyCode",verifyCode);
        System.out.println(request.getSession().getAttribute("kaptchaVerifyCode"));
//        create pic for verify code
        BufferedImage image=kaptchaProducer.createImage(verifyCode);
//        if output is string, response.getWriter(); if out put is binary, response.getOutputStream
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image,"png",out);
        out.flush();
        out.close();
    }
}
