package top.swiftr.book_shop.utils;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.swiftr.book_shop.redisVo.RedisObj;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 查询key对应的对象
     * @param key
     * @return
     */
    public  RedisObj findBykey(Object key){
        return new RedisObj(key,redisTemplate.opsForValue().get(key.toString()));
    }

    public void setObj(RedisObj redisObj){
        redisTemplate.opsForValue().set(redisObj.getKey().toString(),redisObj.getT());
    }

    /**
     * 设置缓存过期时间 单位小时
     * @param redisObj
     * @param time
     */
    public void setObjByhours(RedisObj redisObj,Integer time){
        redisTemplate.opsForValue().set(redisObj.getKey().toString(),redisObj.getT(),time, TimeUnit.HOURS);
    }

    public static void main(String[] args) {
        System.out.println(new Date().getTime()-new Date().getTime());
    }
}
