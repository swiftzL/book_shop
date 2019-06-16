package top.swiftr.book_shop.exception;

import lombok.Getter;
import lombok.Setter;

public class GlobalException extends RuntimeException {

    @Setter
    @Getter
    private String msg;

    public GlobalException(String msg){
        this.msg = msg;
    }

}
