package org.kangspace.wechat.config;

import org.kangspace.wechat.util.ConfigHelper;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author kango2gler@gmail.com
 * @desc 微信配置文件帮助类
 * @date 2017/2/10 17:32
 */
public class WeChatApiUrlConfigHelper {

    static Logger logger = Logger.getLogger(WeChatErrorConfigHelper.class.getName());

    /**
     * 配置文件名称
     * wx-error-code.properties
     */
    private static final String PROPERTIES_FILE_NAME = "wechat-api-urls";

    private static ConfigHelper helper = null;

    private static final Object INIT_LOCK = new Object();

    public static ConfigHelper getHelper() {
        return helper;
    }

    /**
     * 初始化
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 15:27
     * @return
     */
    private static void init() {
        if (helper == null) {
            synchronized (INIT_LOCK) {
                if (helper == null) {
                    helper = new ConfigHelper(PROPERTIES_FILE_NAME);
                }
            }
        }
    }

    public static String getNotNullValue(String key) {
        return helper.getValue(key);
    }
    /**
     * 获取配置值,若配置不存在不抛出异常
     * @param key
     * @Author kango2gler@gmail.com
     * @Date 2017/5/4 10:45
     * @return
     */
    public static String getValue(String key) {
        try {
            return helper.getValue(key);
        } catch (Exception e) {
            logger.log(Level.WARNING,e.getMessage(),e);
        }
        return null;
    }

    static{
        //检查配置文件
        init();
    }
}
