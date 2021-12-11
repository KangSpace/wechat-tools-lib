package org.kangspace.wechat.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author kango2gler@gmail.com
 *  公共工具类
 * @since 2017/2/13 17:32
 */
public class CommonUtils {
    /**
     * 是否为json字符串
     * @param str
     * @return
     */
    public static Boolean isJSON(String str){
        try {
            return new ObjectMapper().readTree(str)!=null;
        } catch (Exception e) {}
        return false;
    }
}
