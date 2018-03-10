package com._20dot.weixin.mp;

import com._20dot.weixin.util.WXReturnBean;

/**
 * @author kango2gler@gmail.com
 * @desc JSApiTicketBean
 * @date 2017/2/14 10:04
 */
public class JSApiTicketReturnBean extends WXReturnBean {
    private String ticket;
    /**
     * 超时时间
     */
    private Integer expires_in;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public String toString() {
        return "JSApiTicketReturnBean{" +
                "ticket='" + ticket + '\'' +
                ", expires_in=" + expires_in +
                "} " + super.toString();
    }
}
