package top.swiftr.book_shop.entity;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Address {

    @Id
    private Integer id;
    private Integer uid;
    private String username;
    private String address;
    private String phone;
    private String postCode;
}
