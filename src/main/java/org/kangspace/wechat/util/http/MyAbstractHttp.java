package org.kangspace.wechat.util.http;

/**
 * @author kango2gler@gmail.com
 * @desc http接口类
 * @date 2017/2/13 17:45
 */
public interface MyAbstractHttp{
    /**
     * POST 请求
     * @param url
     * @param jsonParam  提交的数据,POST时有效 JSON字符串
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 18:16
     * @return
     */
    String post(String url, String jsonParam);

    /**
     * POST 请求
     * @param jsonParam
     * @return
     */
    String post(String jsonParam);
    /**
     * GET 请求
     * @param url
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 18:12
     * @return
     */
    String get(String url);

    /**
     * GET 请求
     * @return
     */
    String get();
}
