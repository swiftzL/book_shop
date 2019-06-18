package top.swiftr.book_shop.controller;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.atmosphere.config.service.Get;
import org.atmosphere.config.service.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.swiftr.book_shop.annotation.Log;
import top.swiftr.book_shop.entity.User;
import top.swiftr.book_shop.enums.StatusEnum;
import top.swiftr.book_shop.exception.GlobalException;
import top.swiftr.book_shop.redisVo.Phone;
import top.swiftr.book_shop.redisVo.RedisObj;
import top.swiftr.book_shop.service.UserService;
import top.swiftr.book_shop.utils.PhoneCodeUtil;
import top.swiftr.book_shop.utils.RedisService;
import top.swiftr.book_shop.vo.ResponseCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Api(value = "LoginContrller",tags = {"登录模块接口"})
@RestController
@RequestMapping("/user")
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;
    @Autowired
    private PhoneCodeUtil phoneCodeUtil;

    @Log("注册日志")
    @PostMapping("/register")
    public ResponseCode register(User user,String code,String phonecode,HttpServletRequest request){

        //校验图形验证码
        if (!request.getSession().getAttribute("gifCode").equals(code)) {
            //todo 更新验证码
            request.getSession().setAttribute("gifCode","fsadfasdfasdfas");
            return new ResponseCode(StatusEnum.CODE_ERROR);
        }

        //校验手机验证码
        RedisObj<Phone> phoneCache = redisService.findBykey(user.getTelephone());
        if ((new Date().getTime() - phoneCache.getT().getDate().getTime())/1000 > 300){
            return new ResponseCode(400,"手机验证码已过期");
        }
        if (phoneCache.getT() == null){
            return new ResponseCode(400,"手机验证码错误");
        }
        if (!phoneCache.getT().getCode().toString().equals(phonecode)){
            return new ResponseCode(400,"手机验证码错误");
        }

        //防止恶意传值
        user.setUid(null);
        if (userService.isExist(user.getUsername()))
            return new ResponseCode(StatusEnum.PARAM_REPEAT,"用户名已存在");
        //todo 记得MD5加密
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password);

        try{
            userService.save(user);
            return ResponseCode.success();
        }catch (Exception e){
            throw new GlobalException(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseCode login(User user,String code,HttpServletRequest request){
        //校验图形验证码
        if (!request.getSession().getAttribute("gifCode").equals(code)) {
            //todo 更新验证码
            request.getSession().setAttribute("gifCode","fsadfasdfasdfas");
            return new ResponseCode(StatusEnum.CODE_ERROR);
        }


        return null;
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
            session.removeAttribute("gifCode");
            session.setAttribute("gifCode", iCaptcha.getCode().toLowerCase());
            log.info(iCaptcha.getCode().toLowerCase());//打印验证码
            response.getOutputStream().close();
            return iCaptcha.getCode().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException("获取验证码异常");
        }
    }

    @GetMapping("/getPhoneCode")
    public ResponseCode getPhoneCode(String phone){
        if (StringUtils.isEmpty(phone)){
            return new ResponseCode(400,"手机号不能为空");
        }

        //获取当前时间
        Date nowDate = new Date();
        //生成短信验证码
        String code = RandomUtil.randomNumbers(6);
        log.info(code);
        RedisObj<Phone> phoneRedisObj = redisService.findBykey(phone);
        /**
         * 判断缓存是否为空
         */
        System.out.println(phoneRedisObj.getT());
        Phone phoneCache = phoneRedisObj.getT() == null ? null : phoneRedisObj.getT();
        /**
         * 缓存不为空的操作
         */

        if (phoneCache !=null){
            //如果次数大于三，则提示今天短信次数用完
            if (phoneCache.getCount() >=3){
                return new ResponseCode(400,"短信次数使用完毕");
            }
            //三分钟内不能重复发送短信
            if ((nowDate.getTime() - phoneCache.getDate().getTime()) / 1000 < 180){
                return new ResponseCode(400,"三分钟内请勿发送短信");
            }
            phoneCache.setCode(Integer.parseInt(code));
            phoneCache.setDate(nowDate);
            phoneCache.setCount(phoneCache.getCount()+1);
            /**
             * 设置缓存6小时过期
             */
            redisService.setObjByhours(new RedisObj(phone,phoneCache),6);

            return phoneCodeUtil.sendCode(phone,code);

        }


        /**
         * 设置需要缓存的参数
         */
        phoneCache = new Phone();
        phoneCache.setPhone(phone);
        phoneCache.setCode(Integer.parseInt(code));
        phoneCache.setCount(1);
        phoneCache.setDate(nowDate);
        redisService.setObjByhours(new RedisObj(phone,phoneCache),6);
        return phoneCodeUtil.sendCode(phone,code);
    }

    @GetMapping("/test")
    public ResponseCode test(HttpServletRequest request){

        return ResponseCode.success();
    }


}
