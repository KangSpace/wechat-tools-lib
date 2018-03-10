package com._20dot.weixin.util.http;

import java.net.HttpURLConnection;
import java.util.*;

/**
 * @author kango2gler@gmail.com
 * @desc http帮助类
 * @date 2017/2/13 17:41
 */
public class MyHttpUtil {

    /**
     * 获取一个http或https client
     * 自动识别http或https
     * @param
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 17:42
     * @return
     */
    public static MyAbstractHttp getClient(String url){
        if(url.startsWith("http") || url.startsWith("HTTP"))
            return new MyHttpClient(url);
        return new MyHttpsClient(url);
    }

    /**
     * 添加requestHeader
     * @param requestHeader
     * @Author kango2gler@gmail.com
     * @Date 2017/6/9 13:47
     * @return
     */
    public static void addRequestHeader(Map<String ,String> requestHeader, HttpURLConnection urlConnection){
        if (requestHeader != null && requestHeader.size() > 0) {
            Set<String> keySet = requestHeader.keySet();
            Iterator<String> keyIt = keySet.iterator();
            while (keyIt.hasNext()) {
                String key = keyIt.next();
                List<String> list = new ArrayList<String>();
                list.add(requestHeader.get(key));
                urlConnection.getHeaderFields().put(key, list);
            }
        }
    }
}
