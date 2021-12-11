package org.kangspace.wework.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.kangspace.wechat.bean.WeChatReturnBean;

/**
 * @author kango2gler@gmail.com
 * @version 1.0
 * @since 2021/08/26 22:41
 */
public class WeWorkMessageReturnBean extends WeChatReturnBean {
    /**
     * 	不合法的userid，不区分大小写，统一转为小写
     */
    @JsonProperty("invaliduser")
    private String invalidUser;
    /**
     * 	不合法的partyid
     */
    @JsonProperty("invalidparty")
    private String invalidParty;
    /**
     * 	不合法的标签id
     */
    @JsonProperty("invalidtag")
    private String invalidTag;
    /**
     * 	仅消息类型为“按钮交互型”，“投票选择型”和“多项选择型”的模板卡片消息返回，应用可使用response_code调用更新模版卡片消息接口，24小时内有效，且只能使用一次
     */
    @JsonProperty("response_code")
    private String responseCode;

    public String getInvalidUser() {
        return invalidUser;
    }

    public void setInvalidUser(String invalidUser) {
        this.invalidUser = invalidUser;
    }

    public String getInvalidParty() {
        return invalidParty;
    }

    public void setInvalidParty(String invalidParty) {
        this.invalidParty = invalidParty;
    }

    public String getInvalidTag() {
        return invalidTag;
    }

    public void setInvalidTag(String invalidTag) {
        this.invalidTag = invalidTag;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
}
