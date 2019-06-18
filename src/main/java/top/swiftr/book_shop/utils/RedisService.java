package top.swiftr.book_shop.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.swiftr.book_shop.redisVo.RedisObj;

@Service
public class RedisService {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 查询key对应的对象
     * @param key
     * @return
     */
    public  RedisObj findBykey(String key){
        return new RedisObj(key,redisTemplate.opsForValue().get(key));
    }

    public void setObj(RedisObj redisObj){
        redisTemplate.opsForValue().set(redisObj.getKey(),redisObj.getT());
    }
}
