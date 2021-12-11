package org.kangspace.wework.mini;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.kangspace.wechat.bean.WeChatReturnBean;

/**
 * 企业微信通过小程序JsCode临时登录凭证响应Bean
 * @author kango2gler@gmail.com
 * @since  1.0.1
 * @since 2021/10/10 20:38
 */
public class Code2SessionReturnBean extends WeChatReturnBean {
    /**
     * 	用户所属企业的corpid
     */
    @JsonProperty("corpid")
    private String corpId;

    /**
     * 	用户在企业内的UserID，对应管理端的帐号，企业内唯一。注意：如果该企业没有关联该小程序，则此处返回加密的userid
     */
    @JsonProperty("userid")
    private String userId;

    /**
     * 	会话密钥
     */
    @JsonProperty("session_key")
    private String sessionKey;

    /**
     * 	设备ID
     */
    @JsonProperty("deviceid")
    private String deviceId;

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
