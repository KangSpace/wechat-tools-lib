package org.kangspace.wechat.mp;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.kangspace.wechat.bean.WeChatReturnBean;

import java.util.List;

;

/**
 * <pre>
 * 获取关注着列表返回bean
 * </pre>
 *
 * @author kango2gler@gmail.com
 * @date 2020/11/9 11:49
 */
public class MpUserBasicInfoBatchReturnBean extends WeChatReturnBean {
    @JsonProperty("user_info_list")
    private List<MpUserBasicInfoReturnBean> userInfoList;

    public List<MpUserBasicInfoReturnBean> getUserInfoList() {
        return userInfoList;
    }

    public void setUserInfoList(List<MpUserBasicInfoReturnBean> userInfoList) {
        this.userInfoList = userInfoList;
    }

    @Override
    public String toString() {
        return "MpUserBasicInfoBatchReturnBean{" +
                "userInfoList=" + userInfoList +
                "} " + super.toString();
    }
}
