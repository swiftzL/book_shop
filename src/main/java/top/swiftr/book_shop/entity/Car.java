package top.swiftr.book_shop.entity;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Car {
    @Id
    private Integer cid;
    private Integer bid;
    private Integer uid;
    private Integer number;
}
