package org.kangspace.wework;

import org.kangspace.wechat.bean.WeChatCapableRetrieveToken;
import org.kangspace.wechat.config.WeChatConfig;
import org.kangspace.wechat.mp.AccessTokenReturnBean;
import org.kangspace.wechat.util.WeChatUtil;
import org.kangspace.wechat.util.http.MyAbstractHttp;
import org.kangspace.wechat.util.http.MyHttpUtil;
import org.kangspace.wework.mini.Code2SessionReturnBean;

import java.util.logging.Logger;

/**
 * @author kango2gler@gmail.com
 * @desc 公众号接口访问
 * @date 2021/08/28 16:17:20
 */
public class WeWorkInterfaceAccess extends WeChatCapableRetrieveToken {
    private static Logger logger = Logger.getLogger(WeWorkInterfaceAccess.class.getName());

    /**
     * 获取公众号AccessToken
     * 该参数需缓存
     * @author kango2gler@gmail.com
     * @date 2021/08/28 16:17:20
     * @return
     * @see #getAccessToken(String)
     */
    public AccessTokenReturnBean getAccessToken(){
        return getAccessToken(WeChatConfig.getDefaultAppIdSecret().getAppId());
    }
    /**
     * 获取公众号AccessToken
     * 该参数需缓存
     * @author kango2gler@gmail.com
     * @date 2021/08/28 16:17:20
     * @return
     * @see #getAccessToken()
     */
    public AccessTokenReturnBean getAccessToken(String appId){
        MyAbstractHttp client = MyHttpUtil.getClient(WeChatConfig.getWeWorkAccessTokenUrl(appId));
        AccessTokenReturnBean rb = WeChatUtil.asReturnBean(client.get(),AccessTokenReturnBean.class);
        if (rb != null) {
            logger.info(rb.toString());
        }
        return rb;
    }

    /**
     * 企业微信通过小程序JsCode临时登录凭证校验
     * @param accessToken
     * @param code
     */
    public Code2SessionReturnBean code2Session(String accessToken,String code) {
        MyAbstractHttp client = MyHttpUtil.getClient(WeChatConfig.getWeWorkMiniProgramCode2SessionUrl(accessToken,code));
        Code2SessionReturnBean rb = WeChatUtil.asReturnBean(client.get(),Code2SessionReturnBean.class);
        if (rb != null) {
            logger.info(rb.toString());
        }
        return rb;
    }

    /**
     * 获取访问用户身份(根据code获取成员信息)
     * @param accessToken
     * @param code
     */
    public UserInfoReturnBean userGetUserInfo(String accessToken,String code) {
        MyAbstractHttp client = MyHttpUtil.getClient(WeChatConfig.getWeWorkUserGetUserInfoUrl(accessToken,code));
        UserInfoReturnBean rb = WeChatUtil.asReturnBean(client.get(),UserInfoReturnBean.class);
        if (rb != null) {
            logger.info(rb.toString());
        }
        return rb;
    }
}
