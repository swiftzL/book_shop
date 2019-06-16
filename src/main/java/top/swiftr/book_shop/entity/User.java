package top.swiftr.book_shop.entity;

import lombok.Data;

import javax.persistence.Id;

@Data
public class User {
    @Id
    private Integer uid;
    private String username;
    private String password;
    private String address;
    private String telephone;
    private String headimage;

}
