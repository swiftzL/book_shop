package top.swiftr.book_shop.redisVo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RedisObj<T> {

    private String key;
    private T t;
}
