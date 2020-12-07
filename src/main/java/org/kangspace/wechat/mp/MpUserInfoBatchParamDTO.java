package org.kangspace.wechat.mp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * <pre>
 * 批量获取用户信息输入参数
 * </pre>
 *
 * @author kango2gler@gmail.com
 * @date 2020/11/9 14:13
 */
public class MpUserInfoBatchParamDTO {
    /**
     * 每次最大数据条数
     */
    public static Integer MAX_BATCH_SIZE =100;

    @JsonProperty("user_list")
    private List<UserOpenIdParam> userList;

    public static class UserOpenIdParam {
        /**
         * 默认语言
         */
        public static String DEFAULT_LANG = "zh_CN";

        @JsonProperty("openid")
        private String openId;
        private String lang = DEFAULT_LANG;

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public UserOpenIdParam() {
        }

        public UserOpenIdParam(String openId) {
            this.openId = openId;
        }

        public UserOpenIdParam(String openId, String lang) {
            this.openId = openId;
            this.lang = lang;
        }

        @Override
        public String toString() {
            return "UserOpenIdParam{" +
                    "openId='" + openId + '\'' +
                    ", lang='" + lang + '\'' +
                    '}';
        }
    }

    public List<UserOpenIdParam> getUserList() {
        return userList;
    }

    public void setUserList(List<UserOpenIdParam> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "MpUserInfoBatchParamDTO{" +
                "userList=" + userList +
                '}';
    }

    public MpUserInfoBatchParamDTO() {
    }

    public MpUserInfoBatchParamDTO(List<UserOpenIdParam> userList) {
        this.userList = userList;
    }
}
