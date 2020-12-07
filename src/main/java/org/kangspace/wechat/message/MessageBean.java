package org.kangspace.wechat.message;

import com.fasterxml.jackson.annotation.JsonProperty;

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
     * 模版id(template_id)
     */
    @JsonProperty("template_id")
    private String templateId;
    /**
     * 模板跳转链接,可为null
     */
    private String url;

    private Miniprogram miniprogram;
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

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
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

    public Miniprogram getMiniprogram() {
        return miniprogram;
    }

    public void setMiniprogram(Miniprogram miniprogram) {
        this.miniprogram = miniprogram;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "touser='" + touser + '\'' +
                ", templateId='" + templateId + '\'' +
                ", url='" + url + '\'' +
                ", miniprogram=" + miniprogram +
                ", data=" + data +
                '}';
    }

    /**
     * 小程序参数
     */
    public static class Miniprogram{
        private String appid;
        private String pagepath;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPagepath() {
            return pagepath;
        }

        public void setPagepath(String pagepath) {
            this.pagepath = pagepath;
        }

        public Miniprogram() {
        }

        public Miniprogram(String appid, String pagepath) {
            this.appid = appid;
            this.pagepath = pagepath;
        }

        @Override
        public String toString() {
            return "Miniprogram{" +
                    "appid='" + appid + '\'' +
                    ", pagepath='" + pagepath + '\'' +
                    '}';
        }
    }
}
