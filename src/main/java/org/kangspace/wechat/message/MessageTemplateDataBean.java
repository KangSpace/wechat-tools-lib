package org.kangspace.wechat.message;

/**
 * @author kango2gler@gmail.com
 *  模版数据 data实体
 * @since 2017/2/13 17:00
 */
public class MessageTemplateDataBean {
    /**
     * 头信息
     */
    private MessageTemplateDataValueColorBean first;
    /**
     * 脚信息
     */
    private MessageTemplateDataValueColorBean remark;

    public MessageTemplateDataValueColorBean getFirst() {
        return first;
    }

    public void setFirst(MessageTemplateDataValueColorBean first) {
        this.first = first;
    }

    public MessageTemplateDataValueColorBean getRemark() {
        return remark;
    }

    public void setRemark(MessageTemplateDataValueColorBean remark) {
        this.remark = remark;
    }

    public MessageTemplateDataBean() {
    }

    public MessageTemplateDataBean(MessageTemplateDataValueColorBean first, MessageTemplateDataValueColorBean remark) {
        this.first = first;
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "MessageTemplateDataBean{" +
                "first=" + first +
                ", remark=" + remark +
                '}';
    }
}
