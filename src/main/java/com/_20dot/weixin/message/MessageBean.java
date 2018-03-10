package com._20dot.weixin.message;

/**
 * @author kango2gler@gmail.com
 * @desc 消息实体
 * @date 2017/2/13 16:58
 */
public class MessageBean {
    /**
     * 目标用户,openId
     */
    private String touser;
    /**
     * 模版id
     */
    private String template_id;
    /**
     * 模板跳转链接,可为null
     */
    private String url;
    /**
     * 模版数据
     */
    private MessageTemplateDataBean data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MessageTemplateDataBean getData() {
        return data;
    }

    public void setData(MessageTemplateDataBean data) {
        this.data = data;
    }
}
