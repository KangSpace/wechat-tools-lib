package com._20dot.weixin;

import com._20dot.weixin.mp.AccessTokenReturnBean;
import com._20dot.weixin.mp.JSApiTicketReturnBean;
import com._20dot.weixin.mp.JSApiTicketSignBean;
import com._20dot.weixin.mp.MpInterfaceAccess;
import com._20dot.weixin.util.WXReturnBean;
import org.junit.Before;
import org.junit.Test;

/**
 * @author kango2gler@gmail.com
 * @desc 公众号接口访问测试
 * @date 2017/2/13 16:57
 */
public class MpInterfaceAccessTest {

    private static MpInterfaceAccess interfaceAccess ;

    @Before
    public void init(){
        interfaceAccess = new MpInterfaceAccess();
    }

    /**
     * 获取accessToken
     * @Author kango2gler@gmail.com
     * @Date 2017/2/14 15:36
     */
    @Test
    public void getMpAccessToken(){
        AccessTokenReturnBean accessToken = interfaceAccess.getAccessToken();
        System.out.println(accessToken);
    }
    /**
     * 获取JSApiTicket
     * @Author kango2gler@gmail.com
     * @Date 2017/2/14 15:35
     */
    @Test
    public void getMpJSApiTicket(){
        JSApiTicketReturnBean jsApiTicket = interfaceAccess.getJsApiTicket(ConfigTest.ACCESS_TOKEN);
        System.out.println(jsApiTicket);
    }

    /**
     * 获取JSAPI加密串
     */
    @Test
    public void getMpJSApiSign() throws Exception {
        JSApiTicketSignBean bean = new JSApiTicketSignBean();
        bean.setUrl("http%3A%2F%2Ftest.com%2Fdyw-childt%2Flogin.html%3Fcode%3Dasd");//"http://127.0.0.1");
        bean.setNonceStr("46D045FF5190F6EA93739DA6C0AA19BC");//Sha1Util.getNonceStr()
        bean.setJsApiTicket("sM4AOVdWfPE4DxkXGEs8VP8yTMRRpToQnfTVK_xQnQIWGrEoOnVWOc6u_yigu0A6JVE1iLj-kLQ8ZT6gIGcdvQ");//ConfigTest.JSAPI_TICKET
        bean.setTimestamp("1487929457497");//new Date().getTime()+""
        bean = MpInterfaceAccess.getJsApiSign(bean);

        System.out.println(bean);
    }
    @Test
    public void returnBeanTest(){
        System.out.println(new WXReturnBean());
    }
}
