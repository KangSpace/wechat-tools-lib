package com._20dot.weixin.util.http;

import com._20dot.weixin.config.WXConfig;
import net.sf.json.JSONObject;
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
        private static Logger logger = Logger.getLogger(WXConfig.class.getName());

        private int defaultByteSize = 256;
        private String contentType;
        private String url;
        private Map<String,String> requestHeader;

        public MyHttpClient(){}

        public MyHttpClient(String url){
            this.url = url;
        }

        public MyHttpClient(String url, Map<String,String> requestHeader) {
            this.url = url;
            this.requestHeader = requestHeader;
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
        public String post(String url,Map<String,String> params,Map<String,String> requestHeader){
            if(params!=null)
                logger.info("POST PARAM :"+params.toString());
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
        public String post(String url,Map<String,String> params){
            return post(url,params,requestHeader);
        }


        @Override
        public String post(String url, String jsonParam, Map<String, String> headerMap) {
            if(jsonParam != null) {
                Map<String,String> paramMap = (Map<String,String>) JSONObject.toBean(JSONObject.fromObject(jsonParam), (new HashMap<String,String>()).getClass());
                return post(url, paramMap,headerMap);
            }
            return post(url, new HashMap<String, String>(), headerMap);
        }

        @Override
        public String post(String url, String jsonParam) {
            return post(url, jsonParam, requestHeader);
        }

        @Override
        public String post(String jsonParam){
            return post(this.url,jsonParam);
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
        public String get(String url,Map<String,String> requestHeader){
            String responseStr=null;
            HttpClient client = new HttpClient();
            GetMethod getMethod = (GetMethod)getHttpMethod(url,GET,null,requestHeader);
            try{
                responseStr = executeHttpMethod(client,getMethod);
            }catch(HttpException e){
                logger.log(Level.SEVERE,e.getMessage(),e);
            }catch(IOException e){
                logger.log(Level.SEVERE,e.getMessage(),e);
            }
            logger.info("Get url:"+url+" responseStr:"+responseStr);
            return responseStr;
        }

        @Override
        public String get(String url){
            return get(url,requestHeader);
        }
        @Override
        public String get(){
            return get(this.url);
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
        public HttpMethod getHttpMethod(String url,String type,Map<String,String> params,Map<String,String> requestHeader){
            HttpMethod method =null;
            if(POST.equals(type)){
                method = new PostMethod(url);
            }else if(GET.equals(type)){
                method = new GetMethod(url);
            }
            method.setRequestHeader("Content-Type",CONTENTTYPE_APPLICATION_URLENCODED_UTF8);
            if(method instanceof PostMethod && params!=null && params.size()>0){
                if(contentType != null && contentType.matches(".*(?i)json.*")){
                    method.setRequestHeader("Content-Type",CONTENTTYPE_APPLICATION_JSON_UTF8);
                    try{
                        RequestEntity re = new StringRequestEntity(JSONObject.fromObject(params).toString(),CONTENTTYPE_APPLICATION_JSON,CHARSET_UTF8);
                        ((PostMethod)method).setRequestEntity(re);
                    }catch(UnsupportedEncodingException e){
                        logger.log(Level.SEVERE,e.getMessage(),e);
                    }
                }else{
                    Set<String> keySet = params.keySet();
                    Iterator<String> keyIt = keySet.iterator();
                    while(keyIt.hasNext()){
                        String key = keyIt.next();
                        ((PostMethod)method).addParameter(key,String.valueOf(params.get(key)));
                    }
                }
            }
            if(requestHeader!=null && requestHeader.size()>0){
                Set<String> keySet = requestHeader.keySet();
                Iterator<String> keyIt = keySet.iterator();
                while(keyIt.hasNext()){
                    String key = keyIt.next();
                    method.addRequestHeader(key,String.valueOf(requestHeader.get(key)));
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
        public String executeHttpMethod(HttpClient client,HttpMethod method) throws IOException{
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
