package org.kangspace.wechat.util;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author kango2gler@gmail.com
 *  JSON处理工具类
 * @since 2017/4/1 15:01
 */
public class GsonUtil {

    /**
     * 转换无泛型对象
     * @param json json字符串
     * @param clazz toBean class
     * @author kango2gler@gmail.com
     * @since 2017/4/1 15:15
     * @return T
     */
    public static <T> T toBean(String json,Class<T> clazz){
        return new Gson().fromJson(json,clazz);
    }

    /**
     * 转换有泛型对象
     * @param json json字符串
     * @param rawClass 原始对象 class
     * @param actualTypes 泛型类型 class
     * @author kango2gler@gmail.com
     * @since 2017/4/1 15:18
     * @return T
     */
    public static <T> T toBean(String json,final Class<T> rawClass,final Type... actualTypes){
        return new Gson().fromJson(json, new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return actualTypes;
            }
            @Override
            public Type getRawType() {
                return rawClass;
            }
            @Override
            public Type getOwnerType() {
                return null;
            }
        });
    }
}
