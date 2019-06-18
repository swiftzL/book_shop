package top.swiftr.book_shop.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import top.swiftr.book_shop.redisVo.RedisObj;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisServiceTest {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisService redisService;
    @Test
    public void findBykey() {
        redisTemplate.opsForValue().set("name",new Date());
        RedisObj<Date> redisObj = redisService.findBykey("name");
        System.out.println(redisObj.getT());
        System.out.println(redisService.findBykey("name"));
    }
}