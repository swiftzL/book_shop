package top.swiftr.book_shop.controller;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.HashUtil;
import cn.hutool.http.HttpResponse;
import io.swagger.annotations.Api;
import org.atmosphere.config.service.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.swiftr.book_shop.annotation.Log;
import top.swiftr.book_shop.entity.User;
import top.swiftr.book_shop.enums.StatusEnum;
import top.swiftr.book_shop.exception.GlobalException;
import top.swiftr.book_shop.service.UserService;
import top.swiftr.book_shop.vo.ResponseCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Api(value = "LoginContrller",tags = {"登录模块接口"})
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    @Log("注册日志")
    @PostMapping("/register")
    public ResponseCode register(User user,String code,String phonecode,HttpServletRequest request){
        //校验图形验证码
        if (!request.getSession().getAttribute("gifCode").equals(code)) {
            return new ResponseCode(StatusEnum.CODE_ERROR);
        }


        //防止恶意传值
        user.setUid(null);
        if (userService.isExist(user.getUsername()))
            return new ResponseCode(StatusEnum.SUCCESS,"用户名已存在");
        //todo 记得MD5加密
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password);

        System.out.println(user);
        try{
            userService.save(user);
            return ResponseCode.success();
        }catch (Exception e){
            throw new GlobalException(e.getMessage());
        }
    }


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
            System.out.println(session.getId());
            session.removeAttribute("gifCode");
            session.setAttribute("gifCode", iCaptcha.getCode().toLowerCase());
            response.getOutputStream().close();
            return iCaptcha.getCode().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException("获取验证码异常");
        }
    }

    @GetMapping("/getPhoneCode")
    public ResponseCode getPhoneCode(String phone){

        return null;
    }

    @GetMapping("/test")
    public ResponseCode test(HttpServletRequest request){

        return ResponseCode.success();
    }


}
