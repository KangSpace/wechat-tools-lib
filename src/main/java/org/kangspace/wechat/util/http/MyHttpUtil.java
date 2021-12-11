package org.kangspace.wechat.util.http;

/**
 * @author kango2gler@gmail.com
 *  http帮助类
 * @since 2017/2/13 17:41
 */
public class MyHttpUtil {
    public static String HTTP_PROTOCOL = "https";
    /**
     * 获取一个http或https client
     * 自动识别http或https
     * @param
     * @author kango2gler@gmail.com
     * @since 2017/2/13 17:42
     * @return MyAbstractHttp
     */
    public static MyAbstractHttp getClient(String url){
        if (url.startsWith(HTTP_PROTOCOL) || url.startsWith(HTTP_PROTOCOL.toUpperCase())) {
            return new MyHttpsClient(url);
        }
        return new MyHttpClient(url);
    }
}
