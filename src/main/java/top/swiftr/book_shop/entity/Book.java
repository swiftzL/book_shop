package top.swiftr.book_shop.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Data
public class Book {

    @Id
    private Integer bid;
    @NotNull(message = "书名不能为空")
    private String bookname;
    @NotNull
    private Integer tid;
    @NotNull
    private BigDecimal bookprice;
    @NotNull
    private String publisher;
    @NotNull
    private String bookimage;
    @NotNull
    private String bookinfo;
    @NotNull
    private String bookcount;

}
