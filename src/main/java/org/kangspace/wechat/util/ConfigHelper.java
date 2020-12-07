package org.kangspace.wechat.util;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author kango2gler@gmail.com
 * @desc mongo.properties配置文件帮助类
 * @date 2017/2/10 17:32
 */
public class ConfigHelper {
    private static Logger logger = Logger.getLogger(ConfigHelper.class.getName());
    /**
     * 配置文件名称
     * mongo.properties
     */
    private String propertiesFileName;

    private String getPropertiesFileName() {
        return propertiesFileName+".properties";
    }

    private ResourceBundle bundle = null;

    public ConfigHelper(String propertiesFileName){
        this.propertiesFileName = propertiesFileName;
        init();
    }

    /**
     * 获取配置信息
     * @Author kango2gler@gmail.com
     * @Date 2017/5/4 11:17
     * @return
     */
    public ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * 初始化
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 15:27
     * @return
     */
    private void init() {
        try{
            bundle = ResourceBundle.getBundle(propertiesFileName);
        }catch (Exception e){
            throw new RuntimeException(getPropertiesFileName()+"  not found ,must provide "+getPropertiesFileName()+" under 'classes' to call this package! -_-!!!",e);
        }
    }

    /**
     * <pre>
     * 通过key获取配置文件信息
     * 支持文件热加载
     * </pre>
     * @param key
     * @Author kango2gler@gmail.com
     * @Date 2017/2/10 17:34
     * @return value
     */
    public String getValue(String key){
        String val = null;
        try {
            if(!bundle.containsKey(key)) {
                init();
            }
            val = (String) bundle.getObject(key);
        }catch (Exception e){
            throw new RuntimeException("key:"+key+" not found ,must provide key:"+key+" in "+getPropertiesFileName()+"! -_-!!!",e);
        }
        return val;
    }

    /**
     * 通过key获取配置文件信息,若key不存在则不抛出异常
     * @Author kango2gler@gmail.com
     * @Date 2017/5/4 11:08
     * @return
     */
    public String getValueNoException(String key){
        try {
            return getValue(key);
        } catch (Exception e) {
            logger.log(Level.WARNING,"this message can be ignored:"+e.getMessage());
        }
        return null;
    }
}
