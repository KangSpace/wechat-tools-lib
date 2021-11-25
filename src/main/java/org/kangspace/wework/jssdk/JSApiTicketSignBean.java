package org.kangspace.wework.jssdk;

/**
 * @author kango2gler@gmail.com
 * @desc JSApiTicketSignBean
 * @date 2021/11/24 23:52:48
 */
public class JSApiTicketSignBean {
    private String corpId;
    private String appId;
    private String agentId;
    private String jsApiTicket;
    /**
     * 随机字符串 Sha1Util.getNonceStr()
     */
    private String nonceStr;
    /**
     * new Date().getTime()
      */
    private String timestamp;
    private String url;
    private String signature;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getJsApiTicket() {
        return jsApiTicket;
    }

    public void setJsApiTicket(String jsApiTicket) {
        this.jsApiTicket = jsApiTicket;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @Override
    public String toString() {
        return "JSApiTicketSignBean{" +
                "corpId='" + corpId + '\'' +
                ", appId='" + appId + '\'' +
                ", agentId='" + agentId + '\'' +
                ", jsApiTicket='" + jsApiTicket + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", url='" + url + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
