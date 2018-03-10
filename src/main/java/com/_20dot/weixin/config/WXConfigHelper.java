package com._20dot.weixin.config;

import java.util.ResourceBundle;

/**
 * @author kango2gler@gmail.com
 * @desc 微信配置文件帮助类
 * @date 2017/2/10 17:32
 */
public class WXConfigHelper{

    /**
     * 微信配置文件名称
     */
    private static final String PROPERTIES_FILE_NAME = "wx-config";

    private static ResourceBundle bundle = null;

    /**
     * 初始化
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 15:27
     * @return
     */
    private static void init() {
        try{
            bundle = ResourceBundle.getBundle(PROPERTIES_FILE_NAME);
        }catch (Exception e){
            throw new RuntimeException("wx-config.properties  not found ,must provide wx-config.properties under 'classes' to call this package! -_-!!!",e);
        }
    }

    public static String getValue(String key) {
        if(bundle == null) {
            init();
        }
        Object value = null;
        if(bundle.containsKey(key)){
            value = bundle.getObject(key);
        }else{
            init();
        }

        if(value == null) {
            value = bundle.getObject(key);
        }
        return value == null ? null : value.toString().trim();
    }

    /**
     * 通过key获取微信配置文件信息
     * @param key
     * @Author kango2gler@gmail.com
     * @Date 2017/2/10 17:34
     * @return value
     */
    public static String value(String key){
        String val = null;
        try {
            val = getValue(key);
        }catch (Exception e){
            throw new RuntimeException("key:"+key+" not found ,must provide key:"+key+" in wx-config.properties! -_-!!!",e);
        }
        return val;
    }

    static{
        //检查微信配置文件
        init();
    }

    public static void main(String[] args) {
        System.out.printf( WXConfig.APP_ID);
        System.out.printf(""+ WXConfigHelper.value("APP_ID"));
    }
}
