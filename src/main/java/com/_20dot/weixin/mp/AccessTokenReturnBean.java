package com._20dot.weixin.mp;

import com._20dot.weixin.util.WXReturnBean;

/**
 * @author kango2gler@gmail.com
 * @desc accessTokenBean
 * @date 2017/2/14 10:04
 */
public class AccessTokenReturnBean extends WXReturnBean{
    private String access_token;
    /**
     * 超时时间
     */
    private Integer expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public String toString() {
        return "AccessTokenReturnBean{" +
                "access_token='" + access_token + '\'' +
                ", expires_in=" + expires_in +
                "} " + super.toString();
    }
}
