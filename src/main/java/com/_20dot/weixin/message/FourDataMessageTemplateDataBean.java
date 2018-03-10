package com._20dot.weixin.message;

/**
 * @author kango2gler@gmail.com
 * @desc 4条数据显示类模版
 * @date 2017/2/13 17:14
 */
public class FourDataMessageTemplateDataBean extends MessageTemplateDataBean {
    private MessageTemplateDataValueColorBean keyword1;
    private MessageTemplateDataValueColorBean keyword2;
    private MessageTemplateDataValueColorBean keyword3;
    private MessageTemplateDataValueColorBean keyword4;

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

    public MessageTemplateDataValueColorBean getKeyword4() {
        return keyword4;
    }

    public void setKeyword4(MessageTemplateDataValueColorBean keyword4) {
        this.keyword4 = keyword4;
    }
}
