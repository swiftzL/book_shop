package top.swiftr.book_shop.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@RestController
@RequestMapping(value = "/alipay")
public class Alipay {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016091700531249";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC0vcuSC0TiJFD+XjIWf3FcCjz9vklgEf4DoJskUzOkX6W3Y2v8ssYySQVIaE3wgifKVwE3RoUFd5qkyvO2nN+3W6OHBcQ1cP1u3hIuzGJrzkzfx4UJHk0mqwTFSlidMsUJ2VlVJS7mxCti3GBv3QzrQ6Q9mt3d5JtKTuyRRmcXqpC5QXiTMomSTqKHPVfG2L3OswBrhQ3lWmwjsrelcKosp86Y8qypdP4L7SnwfRQLI+g5CO2hsNqxPWJLeuwwvAEvfu/JAD/dm7nWo5EMBJ5neKhkPLb1j7IngYGUB6i95B3KAZYHOG3yeYomxBWE0wIsWWH6KnM8cYPW0IHgj/jHAgMBAAECggEAP3jTj6MjeqM0Sk/8GQiCX8c8qgN0Mye+Y7m4IeaSISxxvthMaP7DOY+JKmduz4J1xMoPJEFAn63kAfE/6yZmLjchU16EDBx088VK72JZQfC98GxZ3NzmTSkL0NkOC45zwcSxaYRYVzQW5G3t8/rV+ZzUhcnoFm+c1VioHVBzDc0APROwzMqQmJkfpU3M+Meq8jilnm1L6BCUSfuKkLMpry2z0vGVBUxvDUoXZKPDZmru7Ih7YHrhop1JrSz+8oFtuW0GFvuQzMNstkL32XNZpzfD3XWheLmumjULYu5jLQVmG74Mk0PeUq1v3o6s1RyeIEEPw70IRDcuWlNfILtGgQKBgQD+Gy8+3zMPY7Zpuv0ZohA0DiF9g+08AG/FD8M/rDjTd3bras+eoR6EOjMvl2/PM1ezTOTtXzT4PU/pJrQKD5//nVMMefr9rFMSc3s3DYuY8XP/w+ZT1oM3M4Cl8ckYDivOLqrHk9XKkZEdy/ET1/OQKArFfyh+LYumcKy5Fs2+QQKBgQC2FqLZsY7UGZjgG6SrvrtwIwNZBmqUBecOUSXuyeRa3p7Z5XQcHafnnoZesWx7n2NpVGTIjiQTsIzBAWAdaQnwhVDbC0jBpj3WOwSC7DTNPPZ278M3l6IpSbj2VGVRMNTaAs4fC7k0EBQTXpETOuN6/qHVrZkfCMlLUAerxxiFBwKBgQDWTUjoJkciuH5z5v5j6tjtfeliuKuPyfJ5aJTLE00NkxgY7QjSiKF69LaMcZQbU/qnvbboNj4vCU5mnHyEa68NXbpXsFwsKnQZAImu4V6VCzVARA/xLt2U5Xue+C4XeoQZLCRdkuFgKKoRThkwGhVQw2RqMZaN6412Plp88q1rwQKBgEecU2Nl7xP/FR5eAL8Cfdi2DGUAnv2j7KC2zUJB6CrMjpCHKsi76QKwV4v6Hv3nS0NrS0+MhyevMUvebJxkaOJMq+XZZD20UXFlOHr8xi9JIGzaEl5UphOyyKknu8jYeX1byRBwnd+Zyytq74cui7QdXCwa37NS00dpK/ski8IVAoGBALZYVZ95ManDyLbBPorzFv44OZtwwhJprUgS1BCkjcOKyCUUkxRJPGjvfZD9ENccSbWQJsluYgRket97aNzhl1SMmEzWcLTAsLZ5CCJi2m+XUHcdtj1UcZwQkbIEhAV2VVVbTgOOGdlB/nSEbXyWC7rPbpjP/BeGyFTWYH/lPMsQ";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwjAWFuQLj4rZp6BC9HZ9mP7TEk1BVlcEq76limMGnPJzXD/oNeRBZ6Y6+iN2ojWHTJ6lzeWmCxXEaeFkG0rKNpLyg1tBrIoIjOSHMvPpnEMZkVnKgzf1PVk2b4tk5WKouejyrKi9yb42r0uK9t2Rr6trTg8gd+bN8dVn0irfFkRWzQPVWCtJOARrJSlkjfQugb+lYLxWw6owAyZTsPhCNhghCVVP76+9urUVLopTrQYGOAkWMYQkbecpy4Pyqh8Sv0kuyQvp2hlnTapHM7RfVLgUDv6wmvDK9Esa0CgS7Pyz8Nngo9NilsuDOPcTjFPAMR3cHToiCYDhbWhvkcFSOwIDAQAB";

    // 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://119.23.25.226:8080/alipay/notifyUrl";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://119.23.25.226:8080/alipay/returnUrl";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    //沙箱网关
    public static String gatewayUrl ="https://openapi.alipaydev.com/gateway.do";

    // 仅支持JSON
    public static String format = "JSON";

    @ApiOperation(value = "发起支付", notes = "支付宝")
    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    public void pay(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
            throws ServletException, IOException {
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key, format, charset,
                alipay_public_key, sign_type); // 获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();// 创建API对应的request
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);// 在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" + "    \"out_trade_no\":\"2015032001010100"+(int)(Math.random()*1000)+"\","
                + "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," + "    \"total_amount\":"+(int)(Math.random()*10)+0.88+","
                + "    \"subject\":\"Iphone6 16G\"," + "    \"body\":\"Iphone6 16G 耐克金\","
                + "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\","
                + "    \"extend_params\":{" + "    \"sys_service_provider_id\":\"2088511833207846\"" + "    }" + "  }");// 填充业务参数
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + charset);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    /**
     * 支付宝同步回调
     * @param request
     * @param response
     */
//    @ApiOperation(value = "支付同步回调", notes = "支付宝")
//    @RequestMapping(value = "/returnUrl", method = RequestMethod.POST)
//    public void returnUrl(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
//        System.out.println("=================================同步回调=====================================");
//
//        // 获取支付宝GET过来反馈信息
//        Map<String, String> params = new HashMap<String, String>();
//        Map<String, String[]> requestParams = request.getParameterMap();
//        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
//            String name = (String) iter.next();
//            String[] values = (String[]) requestParams.get(name);
//            String valueStr = "";
//            for (int i = 0; i < values.length; i++) {
//                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
//            }
//            // 乱码解决，这段代码在出现乱码时使用
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//            params.put(name, valueStr);
//        }
//
//        System.out.println(params);
//        boolean signVerified =  AlipaySignature.rsaCheckV1(params, alipay_public_key, charset, sign_type);
//
//        // ——请在这里编写您的程序（以下代码仅作参考）——
//        if (signVerified) {
//            // 商户订单号
//            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
//
//            // 支付宝交易号
//            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
//
//            // 付款金额
//            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
//
//            System.out.println("商户订单号="+out_trade_no);
//            System.out.println("支付宝交易号="+trade_no);
//            System.out.println("付款金额="+total_amount);
//
//            response.getWriter().write(
//                    "trade_no:" + trade_no + "<br/>out_trade_no:" + out_trade_no + "<br/>total_amount:" + total_amount);
//        } else {
//            response.getWriter().write("验签失败");
//        }
//        response.getWriter().flush();
//        response.getWriter().close();
//
//    }

    @ApiOperation(value = "支付异步回调", notes = "支付宝")
    @RequestMapping(value = "/notifyUrl", method = RequestMethod.POST)
    public void notifyUrl(HttpServletRequest request, HttpServletResponse response)
            throws AlipayApiException, IOException {
        System.out.println("#################################异步回调######################################");

        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        System.out.println(params);
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipay_public_key, charset, sign_type); // 调用SDK验证签名

        // ——请在这里编写您的程序（以下代码仅作参考）——

        /*
         * 实际验证过程建议商户务必添加以下校验： 1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
         * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）， 3、校验通知中的seller_id（或者seller_email)
         * 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
         * 4、验证app_id是否为该商户本身。
         */
        if (signVerified) {// 验证成功
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            System.out.println("商户订单号="+out_trade_no);
            System.out.println("支付宝交易号="+trade_no);
            System.out.println("交易状态="+trade_status);
            if (trade_status.equals("TRADE_FINISHED")) {
                // 判断该笔订单是否在商户网站中已经做过处理
                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                // 如果有做过处理，不执行商户的业务程序

                // 注意：
                // 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                // 判断该笔订单是否在商户网站中已经做过处理
                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                // 如果有做过处理，不执行商户的业务程序

                // 注意：
                // 付款完成后，支付宝系统发送该交易状态通知
            }

            System.out.println("异步回调验证成功");
            response.getWriter().write("success");

        } else {// 验证失败
            System.out.println("异步回调验证失败");
            response.getWriter().write("fail");

            // 调试用，写文本函数记录程序运行情况是否正常
            // String sWord = AlipaySignature.getSignCheckContentV1(params);
            // AlipayConfig.logResult(sWord);
        }
        response.getWriter().flush();
        response.getWriter().close();
    }







    public static void main(String[] args) {
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key, format, charset,
                alipay_public_key, sign_type); // 获得初始化的AlipayClient


    }
}
