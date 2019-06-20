package top.swiftr.book_shop.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarVo {

    private Integer carid;
    private String bookname;
    private String booktype;
    private String bookimg;
    private Integer number;
    private BigDecimal bookprice;

}
