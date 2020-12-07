package org.kangspace.wechat.bean;

/**
 * @author kango2gler@gmail.com
 * @desc 微信接口返回消息bean
 * @date 2017/2/13 17:21
 */
public class WeChatReturnBean {
    /**
     * 默认成功代码
     */
    public static Integer DEFAULT_SUCCESS_ERROR_CODE = new Integer(0);
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
        return "WeChatReturnBean{" +
                "errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                ", msgid='" + msgid + '\'' +
                '}';
    }

    public boolean isSuccess() {
        return isSuccess(this);
    }

    public static boolean isSuccess(WeChatReturnBean returnBean) {
        return returnBean != null && (returnBean.getErrcode() == null || WeChatReturnBean.DEFAULT_SUCCESS_ERROR_CODE.equals(returnBean.getErrcode()));
    }

    /**
     * access_token是否过期
     * @param returnBean
     * @return
     */
    public static boolean isAccessTokenInvalid(WeChatReturnBean returnBean){
        return returnBean != null && (returnBean.getErrcode() == 40001 || returnBean.getErrcode() == 40014 || returnBean.getErrcode() == 42001);
    }
}
