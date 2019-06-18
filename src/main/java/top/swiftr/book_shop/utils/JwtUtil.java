package top.swiftr.book_shop.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    /** token秘钥， backups:JKKLJOoasdlfj */
    public static final String SECRET = "JKKLJOoasdlfj";
    /** token 过期时间: 10天 */
    public static final int calendarField = Calendar.DATE;
    public static final int calendarInterval = 10;

    /**
     * JWT生成Token
     * @param userId
     * @return
     */
    public static String createJwttoken(Long userId){
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendarField,calendarInterval);
        Date expiressDate = calendar.getTime();

        Map<String,Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        String token = JWT.create().withHeader(map)
                .withClaim("user_id", null == userId ? null : userId.toString())
                .withIssuedAt(new Date())
                .withExpiresAt(expiressDate)
                .sign(Algorithm.HMAC256(SECRET));

        return token;
    }
    /**
     * 解密Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> parseJWT(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            // e.printStackTrace();
            // token 校验失败, 抛出Token验证非法异常
        }
        return jwt.getClaims();
    }

    public static Boolean verifyToken(String token){
        return parseJWT(token).get("user_id").asString().equals("") ? false : true;
    }


}
