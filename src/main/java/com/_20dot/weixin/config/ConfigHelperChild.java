package com._20dot.weixin.config;

import com._20dot.weixin.util.ConfigHelper;

import java.util.logging.Logger;

/**
 * @author kango2gler@gmail.com
 * @desc wx-error-code.properties配置文件帮助类
 * @date 2017/2/10 17:32
 */
public class ConfigHelperChild extends ConfigHelper {
    private static Logger logger = Logger.getLogger(ConfigHelperChild.class.getName());

    private static ConfigHelperChild ConfigHelperChild;
    /**
     * 配置文件名称
     * wx-error-code.properties
     */
    private static final String PROPERTIES_FILE_NAME = "wx-error-code";

    private ConfigHelperChild(String propertiesFileName) {
        super(propertiesFileName);
    }

    public static ConfigHelperChild getHelper(){
        return  ConfigHelperChild;
    }

    static{
        //检查配置文件
        ConfigHelperChild = new ConfigHelperChild(PROPERTIES_FILE_NAME);
    }

    /**
     * run
     * @Author kango2gler@gmail.com
     * @Date 2017/5/4 11:06
     * @return
     */
    public static void main(String[] args) {
        System.out.println(1+","+ConfigHelperChild.getHelper().getValueNoException("0-"));
        System.out.println(ConfigHelperChild.getHelper().getValue("0-"));
    }
}
