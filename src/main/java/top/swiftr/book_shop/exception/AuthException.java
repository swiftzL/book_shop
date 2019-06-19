package top.swiftr.book_shop.exception;

import lombok.Getter;
import lombok.Setter;

public class AuthException extends RuntimeException {
    @Setter
    @Getter
    private String msg;

    public AuthException(String msg){
        this.msg = msg;
    }
}
