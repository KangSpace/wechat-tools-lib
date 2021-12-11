package org.kangspace.wechat.mp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.kangspace.wechat.bean.WeChatReturnBean;
import org.kangspace.wechat.util.JacksonParser;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 获取关注着列表返回bean
 * </pre>
 *
 * @author kango2gler@gmail.com
 * @since 2020/11/9 11:49
 */
public class MpUserBasicInfoReturnBean extends WeChatReturnBean {

    /**
     * <pre>
     * 用户是否订阅该公众号标识，值为0时，
     * 代表此用户没有关注该公众号，拉取不到其余信息
     * </pre>
     */
    private int subscribe;
    /**
     * 用户的标识，对当前公众号唯一
     */
    @JsonProperty("openid")
    private String openId;
    /**
     * 用户的昵称
     */
    private String nickname;
    /**
     * 用户的性别，值为1时是男性，
     * 值为2时是女性，值为0时是未知
     */
    private int sex;
    /**
     * 用户的语言，简体中文为zh_CN
     */
    private String language;
    /**
     * 用户所在城市
     */
    private String city;
    /**
     * 用户所在省份
     */
    private String province;
    /**
     * 用户所在国家
     */
    private String country;
    /**
     * <pre>
     * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），
     * 用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
     * </pre>
     */
    @JsonProperty("headimgurl")
    private String headImgUrl;
    /**
     * <pre>
     * 用户关注时间，为时间戳(unix time)。如果用户曾多次关注，则取最后关注时间
     * </pre>
     */
    @JsonProperty("subscribe_time")
    @JsonDeserialize(using = JacksonParser.UnixTimestampDeserializer.class)
    private Date subscribeTime;
    /**
     * <pre>
     * 只有在用户将公众号绑定到微信开放平台帐号后，
     * 才会出现该字段。
     * </pre>
     */
    @JsonProperty("unionid")
    private String unionId;
    /**
     * <pre>
     * 公众号运营者对粉丝的备注，
     * 公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
     * </pre>
     */
    private String remark;
    /**
     * 用户所在的分组ID（兼容旧的用户分组接口）
     */
    @JsonProperty("groupid")
    private int groupId;
    /**
     * <pre>
     * 返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENE_PROFILE_LINK 图文页内名称点击，
     * ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_WECHAT_ADVERTISEMENT 微信广告，ADD_SCENE_OTHERS 其他
     * </pre>
     */
    @JsonProperty("subscribe_scene")
    private String subscribeScene;
    /**
     * 二维码扫码场景（开发者自定义）
     */
    @JsonProperty("qr_scene")
    private int qrScene;
    /**
     * 二维码扫码场景描述（开发者自定义）
     */
    @JsonProperty("qr_scene_str")
    private String qrSceneStr;
    /**
     * 用户被打上的标签ID列表
     */
    @JsonProperty("tagid_list")
    private List<Integer> tagidList;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Date getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getSubscribeScene() {
        return subscribeScene;
    }

    public void setSubscribeScene(String subscribeScene) {
        this.subscribeScene = subscribeScene;
    }

    public int getQrScene() {
        return qrScene;
    }

    public void setQrScene(int qrScene) {
        this.qrScene = qrScene;
    }

    public String getQrSceneStr() {
        return qrSceneStr;
    }

    public void setQrSceneStr(String qrSceneStr) {
        this.qrSceneStr = qrSceneStr;
    }

    public List<Integer> getTagidList() {
        return tagidList;
    }

    public void setTagidList(List<Integer> tagidList) {
        this.tagidList = tagidList;
    }

    @Override
    public String toString() {
        return "MpUserBasicInfoReturnBean{" +
                "subscribe=" + subscribe +
                ", openId='" + openId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", language='" + language + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", subscribeTime=" + subscribeTime +
                ", unionId='" + unionId + '\'' +
                ", remark='" + remark + '\'' +
                ", groupId=" + groupId +
                ", subscribeScene='" + subscribeScene + '\'' +
                ", qrScene=" + qrScene +
                ", qrSceneStr='" + qrSceneStr + '\'' +
                ", tagidList=" + tagidList +
                "} " + super.toString();
    }
}
