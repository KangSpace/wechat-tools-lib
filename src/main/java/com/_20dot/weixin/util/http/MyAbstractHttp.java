package com._20dot.weixin.util.http;

import java.util.Map;

/**
 * @author kango2gler@gmail.com
 * @desc http接口类
 * @date 2017/2/13 17:45
 */
public interface MyAbstractHttp{
    String GET = "GET";
    String POST = "POST";
    String PUT = "PUT";
    String DELETE = "DELETE";

    String CHARSET_UTF8 = "UTF-8";
    String CHARSET_GBK = "GBK";

    String CONTENTTYPE_APPLICATION_JSON = "application/json";
    String CONTENTTYPE_APPLICATION_URLENCODED_UTF8 = "application/x-www-form-urlencoded; charset=UTF-8";
    String CONTENTTYPE_APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";
    String CONTENTTYPE_TEXT_HTML_UTF8 = "text/html;charset=UTF-8";

    /**
     * POST 请求
     * @param url 请求链接
     * @param jsonParam  提交的数据,POST时有效 JSON字符串
     * @param headerMap  请求头参数设置
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 18:16
     * @return
     */
    String post(String url, String jsonParam, Map<String, String> headerMap);
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
     * POST 请求 ,URL 来自于 MyAbstractHttp.getClient(URL)
     * @param jsonParam  提交的数据,POST时有效 JSON字符串
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 18:16
     * @return
     */
    String post(String jsonParam);
    /**
     * GET 请求
     * @param url
     * @param headerMap  请求头参数设置
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 18:12
     * @return
     */
    String get(String url, Map<String, String> headerMap);
    /**
     * GET 请求
     * @param url
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 18:12
     * @return
     */
    String get(String url);
    /**
     * GET 请求 ,URL 来自于 MyAbstractHttp.getClient(URL)
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 18:16
     * @return
     */
    String get();
}
