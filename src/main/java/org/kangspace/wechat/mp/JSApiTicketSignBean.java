package org.kangspace.wechat.mp;

/**
 * @author kango2gler@gmail.com
 * @desc JSApiTicketSignBean
 * @date 2017/2/14 10:04
 */
public class JSApiTicketSignBean {
    private String appId;
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
    private String sign;

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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "JSApiTicketSignBean{" +
                "appId='" + appId + '\'' +
                ", jsApiTicket='" + jsApiTicket + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", url='" + url + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
