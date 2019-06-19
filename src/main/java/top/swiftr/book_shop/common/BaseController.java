package top.swiftr.book_shop.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import top.swiftr.book_shop.enums.Identity;
import top.swiftr.book_shop.redisVo.RedisObj;
import top.swiftr.book_shop.utils.JwtUtil;
import top.swiftr.book_shop.utils.RedisService;


public class BaseController  {

    @Autowired
    private RedisService redisService;

    /**
     * 设置jwt并存入redis中
     * @param username
     * @param hours
     * @param identity
     */
    public void setJwt(String username, Integer hours, Identity identity){
        String token = JwtUtil.createJwttoken(username,identity);
        RedisObj<String> redisObj = new RedisObj<>(token,username);
        redisService.setObjByhours(redisObj,hours);
    }

    /**
     * 从redis获取jwt
     * @param token
     * @return
     */
    public String getJwt(String token){
        RedisObj<String> redisObj = redisService.findBykey(token);
        return redisObj.getT();
    }




}
