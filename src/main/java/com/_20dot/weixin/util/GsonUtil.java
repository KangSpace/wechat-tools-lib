package com._20dot.weixin.util;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author kango2gler@gmail.com
 * @desc JSON处理工具类
 *
        <dependency>
        <groupId>net.sf.json-lib</groupId>
        <artifactId>json-lib</artifactId>
        <version>2.2.3</version>
        </dependency>
        </!-- GSON -->
        </!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
        <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.8.0</version>
        </dependency>
 * @date 2017/4/1 15:01
 */
public class GsonUtil {

    /**
     * 转换无泛型对象
     * @param json json字符串
     * @param clazz toBean class
     * @Author kango2gler@gmail.com
     * @Date 2017/4/1 15:15
     * @return
     */
    public static <T> T toBean(String json,Class<T> clazz){
        return new Gson().fromJson(json,clazz);
    }

    /**
     * 转换有泛型对象
     * @param json json字符串
     * @param rawClass 原始对象 class
     * @param actualTypes 泛型类型 class
     * @Author kango2gler@gmail.com
     * @Date 2017/4/1 15:18
     * @return
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
