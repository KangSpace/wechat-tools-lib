package org.kangspace.wechat.message;

/**
 * @author kango2gler@gmail.com
 *  4条数据显示类模版
 * @since 2017/2/13 17:14
 */
public class FourDataMessageTemplateDataBean extends NotificationMessageTemplateDataBean {
    private MessageTemplateDataValueColorBean keyword4;
    public MessageTemplateDataValueColorBean getKeyword4() {
        return keyword4;
    }

    public void setKeyword4(MessageTemplateDataValueColorBean keyword4) {
        this.keyword4 = keyword4;
    }

    public FourDataMessageTemplateDataBean(MessageTemplateDataValueColorBean keyword4) {
        this.keyword4 = keyword4;
    }

    public FourDataMessageTemplateDataBean(MessageTemplateDataValueColorBean keyword1, MessageTemplateDataValueColorBean keyword2, MessageTemplateDataValueColorBean keyword3, MessageTemplateDataValueColorBean keyword4) {
        super(keyword1, keyword2, keyword3);
        this.keyword4 = keyword4;
    }

    public FourDataMessageTemplateDataBean(MessageTemplateDataValueColorBean first, MessageTemplateDataValueColorBean remark, MessageTemplateDataValueColorBean keyword1, MessageTemplateDataValueColorBean keyword2, MessageTemplateDataValueColorBean keyword3, MessageTemplateDataValueColorBean keyword4) {
        super(first, remark, keyword1, keyword2, keyword3);
        this.keyword4 = keyword4;
    }

    @Override
    public String toString() {
        return "FourDataMessageTemplateDataBean{" +
                "keyword4=" + keyword4 +
                "} " + super.toString();
    }
}
