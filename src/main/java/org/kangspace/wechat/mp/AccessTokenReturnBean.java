package org.kangspace.wechat.mp;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.kangspace.wechat.bean.WeChatReturnBean;


/**
 * @author kango2gler@gmail.com
 * @desc accessTokenBean
 * @date 2017/2/14 10:04
 */
public class AccessTokenReturnBean extends WeChatReturnBean {
    @JsonProperty("access_token")
    private String accessToken;
    /**
     * 超时时间
     */
    @JsonProperty("expires_in")
    private Integer expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return "AccessTokenReturnBean{" +
                "access_token='" + accessToken + '\'' +
                ", expires_in=" + expiresIn +
                "} " + super.toString();
    }
}
