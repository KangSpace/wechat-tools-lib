package org.kangspace.wechat.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author kango2gler@gmail.com
 * @desc 微信相关Util
 * @date 2017/2/13 18:43
 */
public class WeChatUtil {

    /**
     * 将json字符串转换为 WeChatReturnBean
     * @param jsonStr
     * @author kango2gler@gmail.com
     * @date 2017/2/14 9:34
     * @return
     */
    public static <T> T asReturnBean(String jsonStr, Class<T> cls) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return (T) objectMapper.readValue(jsonStr, cls);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    /**
     * 是否为微信浏览器请求
     * @param request
     * @return
     */
    public static boolean isWeChatBroswer(HttpServletRequest request){
        String agent = request.getHeader("User-Agent");
        return agent != null && agent.contains("MicroMessenger");
    }
}
