package top.swiftr.book_shop.entity;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Order {
    @Id
    private Integer oid;
    private Integer bid;
    private Integer uid;
    private Integer number;
    private Integer status;
}
