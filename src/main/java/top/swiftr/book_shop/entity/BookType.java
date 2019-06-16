package top.swiftr.book_shop.entity;


import lombok.Data;

import javax.persistence.Id;

@Data
public class BookType {
    @Id
    private Integer tid;
    private String typename;
}
