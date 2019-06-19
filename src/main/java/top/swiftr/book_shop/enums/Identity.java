package top.swiftr.book_shop.enums;

import lombok.Getter;
import lombok.Setter;

public enum Identity {

    Admin(1,"admin"),
    User(2,"user");



    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String info;

    Identity(int code, String info) {
        this.code = code;
        this.info = info;
    }
}
