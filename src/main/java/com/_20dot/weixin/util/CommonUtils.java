package com._20dot.weixin.util;

import net.sf.json.JSONObject;

/**
 * @author kango2gler@gmail.com
 * @desc 公共工具类
 * @date 2017/2/13 17:32
 */
public class CommonUtils {
    /**
     * 是否为json字符串
     * @param str
     * @return
     */
    public static Boolean isJSON(String str){
        try {
            JSONObject.fromObject(str);
            return true;
        } catch (Exception e) {
        }
        return false;
    }
    /**
     * 去除emoji表情字符
     * @return
     */
    public static String replaceEmojiChar(String str){
        return replaceEmojiChar(str,"");
    }
    /**
     * 替换emoji表情字符
     * @param str 需要处理的字符串
     * @param slipStr emoji表情替换字符
     * @return
     */
    public static String replaceEmojiChar(String str,String slipStr){
        return str.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
    }
}
