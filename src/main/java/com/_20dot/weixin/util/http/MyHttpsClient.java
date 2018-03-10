package com._20dot.weixin.util.http;

import com._20dot.weixin.config.WXConfig;
import com._20dot.weixin.util.CommonUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author kango2gler@gmail.com
 * @desc https帮助类
 * @date 2017/2/13 17:35
 */
public class MyHttpsClient implements MyAbstractHttp {
    private static Logger logger = Logger.getLogger(WXConfig.class.getName());

    private int defaultByteSize = 256;
    private String contentType = "JSON";
    private String url;
    private Map<String, String> requestHeader;

    public MyHttpsClient() {
    }

    public MyHttpsClient(String url) {
        this.url = url;
    }

    public MyHttpsClient(String _url, Map<String, String> requestHeader) {
        this.url = url;
        this.requestHeader = requestHeader;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setDefaultByteSize(int defaultByteSize) {
        this.defaultByteSize = defaultByteSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(Map<String, String> requestHeader) {
        this.requestHeader = requestHeader;
    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）{@link HttpMethod}
     * @param outputStr     提交的数据,POST时有效 JSON字符串
     * @param headerMap     http请求头
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public String httpsRequest(String requestUrl, HttpMethod requestMethod, String outputStr, Map<String, String> headerMap) {
        StringBuffer buffer = new StringBuffer();
        HttpsURLConnection httpsUrlConn = null;
        InputStream inputStream = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            httpsUrlConn = (HttpsURLConnection) url.openConnection();
            httpsUrlConn.setSSLSocketFactory(ssf);

            httpsUrlConn.setDoOutput(true);
            httpsUrlConn.setDoInput(true);
            httpsUrlConn.setUseCaches(false);

            //设置header
            MyHttpUtil.addRequestHeader(headerMap, httpsUrlConn);

            // 设置请求方式（GET/POST）
            httpsUrlConn.setRequestMethod(requestMethod.toString());
            httpsUrlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (POST.equals(requestMethod) &&
                    null != outputStr) {// 当有数据需要提交时
                if (CommonUtils.isJSON(outputStr))
                    httpsUrlConn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                OutputStream outputStream = httpsUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            httpsUrlConn.connect();
            // 将返回的输入流转换成字符串
            inputStream = httpsUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
        } catch (ConnectException ce) {
            logger.log(Level.SEVERE, requestUrl + " server connection timed out.", ce);
        } catch (Exception e) {
            logger.log(Level.SEVERE, requestUrl + " https request error:{}", e);
        } finally {
            if (httpsUrlConn != null)
                httpsUrlConn.disconnect();
            // 释放资源
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.log(Level.SEVERE, e.getMessage(), e);
                }
        }
        String sb = buffer.toString();
        logger.info(requestMethod.toString() + " url:" + requestUrl + " responseStr:" + sb);
        return sb;
    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）{@link HttpMethod}
     * @param outputStr     提交的数据,POST时有效 JSON字符串
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public String httpsRequest(String requestUrl, HttpMethod requestMethod, String outputStr) {
        return httpsRequest(requestUrl, requestMethod, outputStr, requestHeader);
    }

    @Override
    public String post(String url, String jsonParam, Map<String, String> headerMap) {
        return httpsRequest(url, HttpMethod.POST, jsonParam, headerMap);
    }

    @Override
    public String post(String url, String jsonParam) {
        return post(url, jsonParam, requestHeader);
    }

    @Override
    public String post(String jsonParam) {
        return post(url, jsonParam);
    }

    @Override
    public String get(String url, Map<String, String> headerMap) {
        return httpsRequest(url, HttpMethod.GET, null, headerMap);
    }

    @Override
    public String get(String url) {
        return get(url, requestHeader);
    }

    @Override
    public String get() {
        return get(url);
    }
}
