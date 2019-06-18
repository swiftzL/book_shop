package top.swiftr.book_shop.handle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import top.swiftr.book_shop.enums.StatusEnum;
import top.swiftr.book_shop.exception.GlobalException;
import top.swiftr.book_shop.vo.ResponseCode;
import javax.servlet.http.HttpServletResponse;

/**
 * 简单处理下全局异常信息
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    public ResponseCode exception(Exception e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return ResponseCode.error();
    }

    @ExceptionHandler(value = GlobalException.class)
    public ResponseCode globalExceptionHandle(GlobalException e, HttpServletResponse response) {
        e.printStackTrace();
        log.error(e.getMsg());
        return ResponseCode.error();
    }

}
