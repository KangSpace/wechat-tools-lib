package org.kangspace.wework.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.kangspace.wechat.bean.WeChatReturnBean;

/**
 * 企业微信通过小程序JsCode临时登录凭证响应Bean
 * @author kango2gler@gmail.com
 * @since  1.0.1
 * @since 2021/10/10 20:38
 */
public class UserInfoReturnBean extends WeChatReturnBean {

    /**
     * 	用户在企业内的UserID，对应管理端的帐号，企业内唯一
     */
    @JsonProperty("UserId")
    private String userId;

    /**
     * 	设备ID
     */
    @JsonProperty("DeviceId")
    private String deviceId;
    /**
     * 非企业成员的标识，对当前企业唯一。不超过64字节
     */
    @JsonProperty("OpenId")
    private String openId;

    /**
     * 外部联系人id，当且仅当用户是企业的客户，且跟进人在应用的可见范围内时返回。如果是第三方应用调用，针对同一个客户，同一个服务商不同应用获取到的id相同
     */
    @JsonProperty("external_userid")
    private String externalUserid;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getExternalUserid() {
        return externalUserid;
    }

    public void setExternalUserid(String externalUserid) {
        this.externalUserid = externalUserid;
    }
}
