package org.kangspace.wechat.util.http;

import org.kangspace.wechat.util.CommonUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author kango2gler@gmail.com
 *  https帮助类
 * @since 2017/2/13 17:35
 */
public class MyHttpsClient implements MyAbstractHttp{
    private static Logger logger = Logger.getLogger(MyHttpsClient.class.getName());
    public final static String GET = "GET";
    public final static String POST = "POST";
    public final static String PUT = "PUT";
    public final static String DELETE = "DELETE";

    public final static String CHARSET_UTF8 = "UTF-8";
    public final static String CHARSET_GBK = "GBK";

    public String contentType = "JSON";

    public final static String CONTENTTYPE_APPLICATION_JSON = "application/json";
    public final static String CONTENTTYPE_APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";
    public final static String CONTENTTYPE_TEXT_HTML_UTF8 = "text/html;charset=UTF-8";

    public int defaultByteSize = 256;
    private String _url;

    public MyHttpsClient() {}
    public MyHttpsClient(String _url) {
        this._url = _url;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setDefaultByteSize(int defaultByteSize) {
        this.defaultByteSize = defaultByteSize;
    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）{@link HttpMethod}
     * @param outputStr     提交的数据,POST时有效 JSON字符串
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static String httpsRequest(String requestUrl, HttpMethod requestMethod, String outputStr) {
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
            httpsUrlConn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String str, SSLSession session) {
                    return true;
                }
            });
            httpsUrlConn.setDoOutput(true);
            httpsUrlConn.setDoInput(true);
            httpsUrlConn.setUseCaches(false);
            String httpMethod = requestMethod.toString();
            // 设置请求方式（GET/POST）
            httpsUrlConn.setRequestMethod(httpMethod);
            httpsUrlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (POST.equals(httpMethod) && outputStr != null ) {// 当有数据需要提交时
                if (CommonUtils.isJSON(outputStr)) {
                    httpsUrlConn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                }
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
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
        } catch (ConnectException ce) {
            logger.log(Level.SEVERE,requestUrl+" server connection timed out.", ce);
        } catch (Exception e) {
            logger.log(Level.SEVERE,requestUrl+" https request error:{}", e);
        } finally {
            if (httpsUrlConn != null)
                httpsUrlConn.disconnect();
            // 释放资源
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.log(Level.SEVERE,e.getMessage(), e);
                }
            inputStream = null;
        }
        String sb = buffer.toString();
        logger.info(requestMethod.toString()+" url:"+requestUrl+" responseStr:"+sb);
        return sb;
    }

    @Override
    public String post(String url, String jsonParam) {
        return httpsRequest(url,HttpMethod.POST,jsonParam);
    }

    @Override
    public String post(String jsonParam) {
        return post(_url,jsonParam);
    }

    @Override
    public String get(String url) {
        return httpsRequest(url,HttpMethod.GET,null);
    }
    @Override
    public String get() {
        return get(_url);
    }
}
