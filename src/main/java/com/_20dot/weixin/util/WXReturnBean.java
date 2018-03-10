package com._20dot.weixin.util;

import com._20dot.weixin.config.WXErrorConfigHelper;

/**
 * @author kango2gler@gmail.com
 * @desc 微信接口返回消息bean
 * @date 2017/2/13 17:21
 */
public class WXReturnBean {
    /**
     * errcode 不存在或为0时,表示接口调用成功
     */
    private Integer errcode;
    private String errmsg;
    private String msgid;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    @Override
    public String toString() {
        return "WXReturnBean{" +
                "errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                ", msgid='" + msgid + '\'' +
                '}'+" [[err-code-msg = "+ WXErrorConfigHelper.getValue(errcode+"")+"]]";

    }
}
