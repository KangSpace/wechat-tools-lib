package com._20dot.weixin.util;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * @author kango2gler@gmail.com
 * @desc 微信相关Util
 * @date 2017/2/13 18:43
 */
public class WXUtil {

    /**
     * 将json字符串转换为 WXReturnBean
     * @param jsonStr
     * @Author kango2gler@gmail.com
     * @Date 2017/2/14 9:34
     * @return
     */
    public static <T> T asReturnBean(String jsonStr,Class<T> cls) {
        return (T) JSONObject.toBean(JSONObject.fromObject(jsonStr),cls);
    }

    /**
     * 是否为微信浏览器请求
     * @param request
     * @return
     */
    public static boolean isWeiXinBroswer(HttpServletRequest request){
        String agent = request.getHeader("User-Agent");
        return agent != null && agent.contains("MicroMessenger");
    }

    /**
     * 判断是否正常访问
     * @param errorCode 微信接口返回错误码
     * @Author kango2gler@gmail.com
     * @Date 2017/6/6 15:56
     * @return
     */
    public static boolean isWeiXinException(Integer errorCode) throws RuntimeException{
        if(errorCode == null || 0 == errorCode)
            return true;
        if(40003 == errorCode) {
            //未关注公众号
            throw new RuntimeException("NOT_VALID_OPENID");
        }else if(40163 == errorCode || 40029 == errorCode || 40001 == errorCode) {
            //网页过期
            throw new RuntimeException("ACCESS_EXPIRE");
        }else{
            //其他
            throw new RuntimeException(errorCode+"");
        }
    }
}
