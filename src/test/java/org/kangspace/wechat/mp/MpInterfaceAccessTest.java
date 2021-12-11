package org.kangspace.wechat.mp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.kangspace.wechat.bean.WeChatReturnBean;
import org.kangspace.wechat.config.WeChatConfig;
import org.kangspace.wechat.util.jdk18.Consumer;
import org.kangspace.wechat.util.jdk18.Function;

import java.util.List;

/**
 * @author kango2gler@gmail.com
 *  公众号接口访问测试
 * @since 2017/2/13 16:57
 */
@RunWith(JUnit4.class)
public class MpInterfaceAccessTest {

    private static MpInterfaceAccess interfaceAccess ;

    @Before
    public void init(){
        interfaceAccess = new MpInterfaceAccess();
    }

    /**
     * 获取accessToken
     * @author kango2gler@gmail.com
     * @since 2017/2/14 15:36
     */
    @Test
    public void getMpAccessToken(){
        AccessTokenReturnBean accessToken = interfaceAccess.getAccessToken();
        System.out.println(accessToken);
    }
    /**
     * 获取JSApiTicket
     * @author kango2gler@gmail.com
     * @since 2017/2/14 15:35
     */
    @Test
    public void getMpJSApiTicket(){
        String appId = WeChatConfig.getDefaultAppIdSecret().getAppId();
        String accessToken = getAccessToken(appId,false);
        JSApiTicketReturnBean jsApiTicket = interfaceAccess.getJsApiTicket(accessToken);
        System.out.println(jsApiTicket);
    }

    /**
     * 获取JSAPI加密串
     */
    @Test
    public void getMpJSApiSign() throws Exception {
        JSApiTicketSignBean bean = new JSApiTicketSignBean();
        bean.setUrl("http%3A%2F%2Ftest.com%2Ftemp%2Flogin.html%3Fcode%3Dasd");//"http://127.0.0.1");
        bean.setNonceStr("46D045FF5190F6EA93739DA6C0AA19BC");//Sha1Util.getNonceStr()
        bean.setJsApiTicket("sM4AOVdWfPE4DxkXGEs8VP8yTMRRpToQnfTVK_xQnQIWGrEoOnVWOc6u_yigu0A6JVE1iLj-kLQ8ZT6gIGcdvQ");//getMpJSApiTicket();
        bean.setTimestamp("1487929457497");//new Date().getTime()+""
        bean = MpInterfaceAccess.getJsApiSign(bean);

        System.out.println(bean);
    }

    /**
     * 微信returnBean 测试
     */
    @Test
    public void returnBeanTest(){
        System.out.println(new WeChatReturnBean());
    }

    private String accessToken;

    /**
     * 获取access_token,可刷新
     * @param appId appId
     * @param refresh refresh
     * @return String
     */
    public String getAccessToken(String appId,boolean refresh){
        //首测时,取消注释,获取最新access_token
        accessToken = "39_khEk7Wgbw9m-7q_FpMU7PL1F0VvdCu9Gu_FO9UUWSdrL5sHEkCazFrgXjzTmXVcRYDhuOQvK1q6tmNFzt5O0m8aZLfNn9KgwZSZWn-ZomHOeYdQ90BqMefcP8PUT50MD1OPdEFwy6Umo5ezjITCfAFAVGP";
        if (refresh) {
            accessToken = interfaceAccess.getAccessToken(appId).getAccessToken();
        }
        return accessToken;
    }

    /**
     * 测试从微信获取关注用户列表
     */
    @Test
    public void getUserListTest() {
        String appId = WeChatConfig.APP_ID;
        accessToken = getAccessToken(appId, false);
        System.out.println("accessToken:"+accessToken);
        //第二个用户
        String nextOpenId = null;//"oOIaHty4SmHFGVoL2MzIvnE5Qy28";
        System.out.println("APP_ID:"+ appId+",关注用户列表为:");
        final Integer[] cntNum = {0};
        interfaceAccess.getMpUserListBatch(appId, accessToken, nextOpenId,
                new Consumer<List<MpUserBasicInfoReturnBean>>() {
                    @Override
                    public void accept(List<MpUserBasicInfoReturnBean> mpUserInfos) {
                        for (MpUserBasicInfoReturnBean t : mpUserInfos) {
                            System.out.println("\t" + t.toString());
                            cntNum[0]++;
                        }
                        System.out.println();
                    }
                }, new Function<String, String>() {
                    @Override
                    public String apply(String t) {
                        return getAccessToken(t, true);
                    }
                });
        System.out.println("APPID:"+ appId+",关注用户数:"+ cntNum[0]);
    }
}
