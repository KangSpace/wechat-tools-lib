package org.kangspace.wechat.bean;

/**
 * @author kango2gler@gmail.com
 *  语言版本
 * @since 2017/6/6 16:15
 */
public enum Lang {
    /**
     * 简体中文
     */
    ZH_CN("zh_CN"),
    ZH_TW("zh_TW"),
    EN("en");

    Lang(String val){
        this.val = val;
    }
    private String val;

    @Override
    public String toString() {
        return this.val;
    }
}
