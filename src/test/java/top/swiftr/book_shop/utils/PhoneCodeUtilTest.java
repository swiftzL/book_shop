package top.swiftr.book_shop.utils;

import cn.hutool.core.util.RandomUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PhoneCodeUtilTest {

    @Autowired
    PhoneCodeUtil phoneCodeUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void sendCode() {
        RandomUtil.randomNumbers(6);
        phoneCodeUtil.sendCode("15616580116",RandomUtil.randomNumbers(6));
    }

    @Test
    public void testRedis(){
        redisTemplate.opsForValue().set("name","lzl");
        System.out.println(redisTemplate.opsForValue().get("name"));
    }
}