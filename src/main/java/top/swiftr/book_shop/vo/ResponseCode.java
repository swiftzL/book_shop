package top.swiftr.book_shop.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import top.swiftr.book_shop.enums.StatusEnum;

@Data
@AllArgsConstructor
public class ResponseCode {

    private Integer code;
    private String msg;
    private Object obj;

    public ResponseCode(StatusEnum statusEnum){
        this.code = statusEnum.getCode();
        this.msg = statusEnum.getInfo();
    }

    public ResponseCode(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public ResponseCode(StatusEnum statusEnum,Object obj){
        this.code = statusEnum.getCode();
        this.msg = statusEnum.getInfo();
        this.obj = obj;

    }

    public static ResponseCode success() {
        return new ResponseCode(StatusEnum.SUCCESS);
    }

    public static ResponseCode success(Object data) {
        return new ResponseCode(StatusEnum.SUCCESS, data);
    }

    public static ResponseCode error() {
        return new ResponseCode(StatusEnum.SYSTEM_ERROR);
    }


}
