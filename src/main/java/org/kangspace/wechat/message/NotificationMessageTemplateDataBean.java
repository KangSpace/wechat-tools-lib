package org.kangspace.wechat.message;

/**
 * @author kango2gler@gmail.com
 * @desc 通知类消息模版,3条数据显示
 * @date 2017/2/13 17:14
 */
public class NotificationMessageTemplateDataBean extends MessageTemplateDataBean {
    private MessageTemplateDataValueColorBean keyword1;
    private MessageTemplateDataValueColorBean keyword2;
    private MessageTemplateDataValueColorBean keyword3;

    public MessageTemplateDataValueColorBean getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(MessageTemplateDataValueColorBean keyword1) {
        this.keyword1 = keyword1;
    }

    public MessageTemplateDataValueColorBean getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(MessageTemplateDataValueColorBean keyword2) {
        this.keyword2 = keyword2;
    }

    public MessageTemplateDataValueColorBean getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(MessageTemplateDataValueColorBean keyword3) {
        this.keyword3 = keyword3;
    }

    public NotificationMessageTemplateDataBean() {
    }

    public NotificationMessageTemplateDataBean(MessageTemplateDataValueColorBean keyword1, MessageTemplateDataValueColorBean keyword2,
                                               MessageTemplateDataValueColorBean keyword3) {
        this.keyword1 = keyword1;
        this.keyword2 = keyword2;
        this.keyword3 = keyword3;
    }

    public NotificationMessageTemplateDataBean(MessageTemplateDataValueColorBean first, MessageTemplateDataValueColorBean remark,
                                               MessageTemplateDataValueColorBean keyword1, MessageTemplateDataValueColorBean keyword2, MessageTemplateDataValueColorBean keyword3) {
        super(first, remark);
        this.keyword1 = keyword1;
        this.keyword2 = keyword2;
        this.keyword3 = keyword3;
    }

    @Override
    public String toString() {
        return "NotificationMessageTemplateDataBean{" +
                "keyword1=" + keyword1 +
                ", keyword2=" + keyword2 +
                ", keyword3=" + keyword3 +
                "} " + super.toString();
    }
}
