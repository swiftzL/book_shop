package top.swiftr.book_shop.entity;


import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "booktype")
public class BookType {
    @Id
    private Integer tid;
    private String typename;
}
