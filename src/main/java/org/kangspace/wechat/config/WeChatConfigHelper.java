package org.kangspace.wechat.config;

import java.util.ResourceBundle;

/**
 * @author kango2gler@gmail.com
 * @desc 微信配置文件帮助类
 * @date 2017/2/10 17:32
 */
public class WeChatConfigHelper {

    /**
     * 微信配置文件名称
     */
    private static final String PROPERTIES_FILE_NAME = "wechat-config";
    private static final Object INIT_LOCK = new Object();

    private static ResourceBundle bundle = null;

    /**
     * 初始化
     * @author kango2gler@gmail.com
     * @date 2017/2/13 15:27
     * @return
     */
    private static void init() {
        if (bundle == null) {
            synchronized (INIT_LOCK) {
                if (bundle == null) {
                    try{
                        bundle = ResourceBundle.getBundle(PROPERTIES_FILE_NAME);
                    }catch (Exception e){
                        throw new RuntimeException(PROPERTIES_FILE_NAME+".properties  not found ,must provide "+PROPERTIES_FILE_NAME+".properties under 'classes' to call this package! -_-!!!",e);
                    }
                }
            }
        }
    }

    public static String getValue(String key) {
        if(bundle == null) {
            init();
        }
        Object value = null;
        if(bundle.containsKey(key)){
            value = bundleGetObject(key);
        }else{
            init();
        }
        return value == null ? null : value.toString().trim();
    }

    private static Object bundleGetObject(String key){
        try {
            return bundle.getObject(key);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 通过key获取微信配置文件信息
     * @param key
     * @author kango2gler@gmail.com
     * @date 2017/2/10 17:34
     * @return value
     */
    public static String value(String key){
        String val = null;
        try {
            val = getValue(key);
        }catch (Exception e){
            throw new RuntimeException("key:"+key+" not found ,must provide key:"+key+" in "+PROPERTIES_FILE_NAME+".properties! -_-!!!",e);
        }
        return val;
    }

    static{
        //检查微信配置文件
        init();
    }
}
