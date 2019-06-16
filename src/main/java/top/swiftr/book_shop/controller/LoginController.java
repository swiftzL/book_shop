package top.swiftr.book_shop.controller;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.http.HttpResponse;
import io.swagger.annotations.Api;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.swiftr.book_shop.exception.GlobalException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Api(value = "LoginContrller",tags = {"登录模块接口"})
@RestController
public class LoginController {

    @GetMapping("/getCode")
    public String getCode(HttpServletResponse response, HttpServletRequest request){
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Expires", "0");
            response.setContentType("image/png");
            ICaptcha iCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
            iCaptcha.write(response.getOutputStream());
            HttpSession session = request.getSession(true);
            session.removeAttribute("gifCode");
            session.setAttribute("gifCode", iCaptcha.getCode().toLowerCase());
            response.getOutputStream().close();
            return iCaptcha.getCode().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException("获取验证码异常");
        }
    }

}
