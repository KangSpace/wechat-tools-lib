package org.kangspace.wechat.util;

import org.kangspace.wechat.bean.WeChatReturnBean;

;

/**
 * <pre>
 *
 * </pre>
 *
 * @author kango2gler@gmail.com
 * @since 2020/11/13 17:39
 */
public class AccessTokenInvalidException extends RuntimeException{
    private WeChatReturnBean returnBean;

    public AccessTokenInvalidException(WeChatReturnBean returnBean) {
        this.returnBean = returnBean;
    }

    public AccessTokenInvalidException(String message, WeChatReturnBean returnBean) {
        super(message);
        this.returnBean = returnBean;
    }

    public AccessTokenInvalidException(String message, Throwable cause, WeChatReturnBean returnBean) {
        super(message, cause);
        this.returnBean = returnBean;
    }

    public AccessTokenInvalidException(Throwable cause, WeChatReturnBean returnBean) {
        super(cause);
        this.returnBean = returnBean;
    }

    public AccessTokenInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, WeChatReturnBean returnBean) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.returnBean = returnBean;
    }

    public WeChatReturnBean getReturnBean() {
        return returnBean;
    }

    public void setReturnBean(WeChatReturnBean returnBean) {
        this.returnBean = returnBean;
    }
}
