package top.swiftr.book_shop.redisVo;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * redis 中缓存的page对象
 */
@Data
@AllArgsConstructor
public class BookPage {
    private Integer tid;
    private Integer pagenum;
    private Integer pagesize;

}
