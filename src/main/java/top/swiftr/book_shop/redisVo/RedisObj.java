package top.swiftr.book_shop.redisVo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RedisObj<T> {

    private Object key;
    private T t;
}
