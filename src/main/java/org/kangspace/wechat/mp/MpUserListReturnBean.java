package org.kangspace.wechat.mp;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.kangspace.wechat.bean.WeChatReturnBean;

import java.util.List;
/**
 * <pre>
 * 获取关注着列表返回bean
 * </pre>
 *
 * @author kango2gler@gmail.com
 * @since 2020/11/9 11:49
 */
public class MpUserListReturnBean extends WeChatReturnBean {
    public static Integer MAX_FETCH_COUNT = 10000;
    /**
     * 关注该公众账号的总用户数
     */
    private Integer total;
    /**
     * 拉取的OPENID个数，最大值为10000
     */
    private Integer count;
    /**
     * 列表数据，OPENID的列表
     */
    private UserListData data;
    /**
     * 	拉取列表的最后一个用户的OPENID
     */
    @JsonProperty("next_openid")
    private String nextOpenId;

    /**
     * 用户列表数据对象
     */
    public static class UserListData{
        @JsonProperty("openid")
        private List<String> openId;

        public List<String> getOpenId() {
            return openId;
        }

        public void setOpenId(List<String> openId) {
            this.openId = openId;
        }
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public UserListData getData() {
        return data;
    }

    public void setData(UserListData data) {
        this.data = data;
    }

    public String getNextOpenId() {
        return nextOpenId;
    }

    public void setNextOpenId(String nextOpenId) {
        this.nextOpenId = nextOpenId;
    }

    @Override
    public String toString() {
        return "MpUserListReturnBean{" +
                "total=" + total +
                ", count=" + count +
                ", data=" + data +
                ", nextOpenId='" + nextOpenId + '\'' +
                "} " + super.toString();
    }
}
