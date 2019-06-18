package top.swiftr.book_shop.redisVo;

import lombok.Data;

import java.util.Date;

@Data
public class Phone {

    private String phone;
    private Integer code;
    private Date date;
    private String count;
}
