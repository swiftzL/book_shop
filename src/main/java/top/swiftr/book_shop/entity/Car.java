package top.swiftr.book_shop.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
public class Car {
    @Id
    private Integer cid;
    @NotNull
    private Integer bid;
    @NotNull
    private Integer uid;
    @NotNull
    private Integer number;
}
