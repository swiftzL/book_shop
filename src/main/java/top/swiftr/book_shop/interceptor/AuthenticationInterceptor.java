package top.swiftr.book_shop.interceptor;

import com.auth0.jwt.JWT;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.swiftr.book_shop.annotation.CheckToken;
import top.swiftr.book_shop.common.BaseController;
import top.swiftr.book_shop.exception.AuthException;
import top.swiftr.book_shop.exception.GlobalException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor extends BaseController implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String token = request.getHeader("jwt");
        if (!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();

        /**
         * 检查有没有需要校验的注解
         */
        if (method.isAnnotationPresent(CheckToken.class)){
            CheckToken checkToken = method.getAnnotation(CheckToken.class);
            if (StringUtils.isEmpty(token) || StringUtils.isEmpty(this.getJwt(token))){
                throw new AuthException("无token,请重新登录");
            }
            //获取用户名
            String username = null;
            try {
                username = JWT.decode(token).getClaim("username").asString();
            }catch (Exception e){
                throw new GlobalException("访问异常");
            }
            //与缓存中的用户对比
            if (!this.getJwt(token).equals(username)){
                throw new GlobalException("非法访问");
            }

            //获取用户权限
            String identity;
            try {
                identity = JWT.decode(token).getClaim("identity").asString();
            }catch (Exception e){
                throw new GlobalException("访问异常");
            }

            if (checkToken.value().equals("admin") && identity.equals("admin")){
                return true;
            }
            if (checkToken.value().equals("user") && identity.equals("user")){
                return true;
            }

        }
        return true;
    }
}
