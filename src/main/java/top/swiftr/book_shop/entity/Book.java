package top.swiftr.book_shop.entity;

import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;


@Data
public class Book {

    @Id
    private Integer bid;
    private String bookname;
    private Integer tid;
    private BigDecimal bookprice;
    private String publisher;
    private String bookimage;
    private String bookinfo;
    private String bookcount;

}
