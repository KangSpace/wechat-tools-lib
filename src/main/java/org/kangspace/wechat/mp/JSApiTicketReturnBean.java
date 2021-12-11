package org.kangspace.wechat.mp;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.kangspace.wechat.bean.WeChatReturnBean;

/**
 * @author kango2gler@gmail.com
 *  JSApiTicketBean
 * @since 2017/2/14 10:04
 */
public class JSApiTicketReturnBean extends WeChatReturnBean {
    private String ticket;
    /**
     * 超时时间
     */
    @JsonProperty("expires_in")
    private Integer expiresIn;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return "JSApiTicketReturnBean{" +
                "ticket='" + ticket + '\'' +
                ", expires_in=" + expiresIn +
                "} " + super.toString();
    }
}
