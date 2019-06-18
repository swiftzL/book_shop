package top.swiftr.book_shop.utils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.oracle.javafx.jmx.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import top.swiftr.book_shop.vo.ResponseCode;

import java.io.IOException;

/**
 * 腾讯验证码发送api
 */
@Configuration
public class PhoneCodeUtil {

    @Value("${tengxun.appid}")
    public  String appid;

    @Value("${tengxun.appkey}")
    public  String appkey;

    // 需要发送短信的手机号码


    // 短信模板ID，需要在短信应用中申请
   //int templateId = 7839; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请

    // 签名
    String smsSign = "swiftR的小站"; // NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`

    public ResponseCode sendCode(String phone,String code){
        try {
            System.out.println(appid);
            System.out.println(appkey);
            System.out.println(code);
            System.out.println(phone);
            SmsSingleSender ssender = new SmsSingleSender(Integer.parseInt(appid), appkey);
            //0为普通短信
            SmsSingleSenderResult result = ssender.send(0, "86",phone,
                    "【swiftR的小站】"+code+"为您的登录验证码，请于2分钟内填写。如非本人操作，请忽略本短信。 ", "", "");
            System.out.println(result.errMsg);
        } catch (Exception e) {
            System.out.println(e);
           return ResponseCode.error();
        }
        return ResponseCode.success();
    }
}
