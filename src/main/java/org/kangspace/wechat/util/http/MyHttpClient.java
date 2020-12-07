package org.kangspace.wechat.util.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author kango2gler@gmail.com
 * @desc Http帮助类
 * @date 2017/2/13 17:35
 */
public class MyHttpClient implements MyAbstractHttp {
        private static Logger logger = Logger.getLogger(MyHttpClient.class.getName());
        public final static String GET="GET";
        public final static String POST="POST";
        public final static String PUT="PUT";
        public final static String DELETE="DELETE";

        public final static String CHARSET_UTF8="UTF-8";
        public final static String CHARSET_GBK="GBK";

        public String contentType="JSON";

        public final static String CONTENTTYPE_APPLICATION_JSON = "application/json";
        public final static String CONTENTTYPE_APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";
        public final static String CONTENTTYPE_TEXT_HTML_UTF8 = "text/html;charset=UTF-8";

        public int defaultByteSize=256;

        private String _url;
        public MyHttpClient(){}

        public MyHttpClient(String _url){
            this._url = _url;
        }

        public void setContentType(String contentType){
            this.contentType = contentType;
        }

        public void setDefaultByteSize(int defaultByteSize){
            this.defaultByteSize = defaultByteSize;
        }


        /**
         *
         * <pre>
         * 方法说明：HttpClient Post请求
         * 开发时间：2015年3月16日上午11:05:43
         * 开发人员：Kang
         * @param url	请求URL
         * @param params	请求参数
         * @param requestHeader	请求头
         * @return
         * </pre>
         */
        public String post(String url, Map<String, String> params, Map<String, String> requestHeader){
            if (params != null) {
                logger.info(params.toString());
            }
            String responseStr=null;
            HttpClient client = new HttpClient();
            PostMethod postMethod = (PostMethod)getHttpMethod(url,POST,params,requestHeader);
            try{
                responseStr = executeHttpMethod(client,postMethod);
            }catch(HttpException e){
                logger.log(Level.SEVERE,e.getMessage(),e);
            }catch(IOException e){
                logger.log(Level.SEVERE,e.getMessage(),e);
            }
            logger.info("Post url:"+url+" responseStr:"+responseStr);
            return responseStr;
        }

        /**
         *
         * <pre>
         * 方法说明：HttpClient Post请求
         * 开发时间：2015年3月16日上午11:32:25
         * 开发人员：Kang
         * @param url	请求URL
         * @param params	请求参数
         * @return
         * </pre>
         */
        public String post(String url, Map<String, String> params){
            return post(url,params,null);
        }

        @Override
        public String post(String jsonParam){
            return post(this._url,jsonParam);
        }

        @Override
        public String post(String url, String jsonParam) {
            Map<String, String> paramMap = null;
            if(jsonParam != null) {
                try {
                    paramMap = (Map<String, String>) new ObjectMapper().readValue(jsonParam, HashMap.class);
                } catch (IOException e) {
                    new IllegalArgumentException(e.getMessage(), e);
                }
            }
            return post(url, paramMap);
        }
        /**
         *
         * <pre>
         * 方法说明：HttpClient Get请求
         * 开发时间：2015年3月16日上午11:27:00
         * 开发人员：Kang
         * @param url	请求URL
         * @param requestHeader	请求头
         * @return
         * </pre>
         */
        public String get(String url, Map<String, String> requestHeader){
            String responseStr=null;
            HttpClient client = new HttpClient();
            GetMethod getMethod = (GetMethod)getHttpMethod(url,GET,null,requestHeader);
            try{
                responseStr = executeHttpMethod(client,getMethod);
            }catch(IOException e){
                logger.log(Level.SEVERE,e.getMessage(),e);
            }
            logger.info("Get url:"+url+" responseStr:"+responseStr);
            return responseStr;
        }

        @Override
        public String get(String url){
            return get(url,null);
        }
        @Override
        public String get(){
            return get(this._url);
        }

        /**
         *
         * <pre>
         * 方法说明：获取Method对象[PostMethod,GETMethod...]
         * 开发时间：2015年3月16日上午10:57:45
         * 开发人员：Kang
         * @param url 请求URL
         * @param type	Mehod 类型
         * @param params  请求数据(Post时使用)
         * @param requestHeader 请求头
         * @return Method对象
         * </pre>
         */
        public HttpMethod getHttpMethod(String url, String type, Map<String, String> params, Map<String, String> requestHeader){
            HttpMethod method =null;
            if(POST.equals(type)){
                method = new PostMethod(url);
            }else if(GET.equals(type)){
                method = new GetMethod(url);
            }
            if(method instanceof PostMethod && params!=null && params.size()>0){
                method.addRequestHeader("ContentType",CONTENTTYPE_APPLICATION_JSON_UTF8);
                if("JSON".equalsIgnoreCase(contentType)){
                    try{
                        RequestEntity re = new StringRequestEntity(new ObjectMapper().writeValueAsString(params),CONTENTTYPE_APPLICATION_JSON,CHARSET_UTF8);
                        ((PostMethod)method).setRequestEntity(re);
                    }catch(UnsupportedEncodingException | JsonProcessingException e){
                        e.printStackTrace();
                    }
                }else{
                    Set<String> keySet = params.keySet();
                    Iterator<String> keyIt = keySet.iterator();
                    while(keyIt.hasNext()){
                        String key = keyIt.next();
                        ((PostMethod)method).addParameter(key, String.valueOf(params.get(key)));
                    }
                }
            }
            if(requestHeader!=null && requestHeader.size()>0){
                Set<String> keySet = requestHeader.keySet();
                Iterator<String> keyIt = keySet.iterator();
                while(keyIt.hasNext()){
                    String key = keyIt.next();
                    method.addRequestHeader(key, String.valueOf(requestHeader.get(key)));
                }
            }
            return method;
        }

        /**
         *
         * <pre>
         * 方法说明：执行Method请求
         * 开发时间：2015年3月16日上午11:04:50
         * 开发人员：Kang
         * @param client  HttpClient对象
         * @param method  Method
         * @return	返回结果
         * @throws HttpException
         * @throws IOException
         * </pre>
         */
        public String executeHttpMethod(HttpClient client, HttpMethod method) throws IOException {
            int responseCode = client.executeMethod(method);
            if(HttpStatus.SC_OK != responseCode)
                throw new HttpException("HTTP "+method.getName()+" Request Failed with Error code : "
                        + responseCode + new String(method.getResponseBody(),CHARSET_UTF8));
        /*
        InputStream is =  method.getResponseBodyAsStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b= new byte[defaultByteSize];
        int c=-1;
        while((c=is.read(b))!=-1){
            out.write(b,0,c);
        }
        out.close();
        */
            return new String(method.getResponseBody(),CHARSET_UTF8);
        }

}
