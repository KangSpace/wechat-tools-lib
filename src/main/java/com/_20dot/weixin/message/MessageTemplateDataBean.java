package com._20dot.weixin.message;

/**
 * @author kango2gler@gmail.com
 * @desc 模版数据 data实体
 * @date 2017/2/13 17:00
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
}
