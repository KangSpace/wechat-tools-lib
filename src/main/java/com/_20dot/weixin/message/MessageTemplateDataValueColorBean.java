package com._20dot.weixin.message;

/**
 * @author kango2gler@gmail.com
 * @desc 模版数据 data实体中 key所对应的值,value,color
 * @date 2017/2/13 17:00
 */
public class MessageTemplateDataValueColorBean {
    /**
     * 值
     */
    private String value;
    /**
     * 颜色
     */
    private String color;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
