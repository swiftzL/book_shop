package top.swiftr.book_shop.redisVo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Phone {

    private String phone;
    private Integer code;
    private Date date;
    private Integer count;

    public Phone() {

    }
}
