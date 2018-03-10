package com._20dot.weixin.mp;

import com._20dot.weixin.config.WXConfig;
import com._20dot.weixin.util.CodecUtil;
import com._20dot.weixin.util.Sha1Util;
import com._20dot.weixin.util.WXUtil;
import com._20dot.weixin.util.http.MyAbstractHttp;
import com._20dot.weixin.util.http.MyHttpUtil;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * @author kango2gler@gmail.com
 * @desc 公众号接口访问
 * @date 2017/2/14 9:59
 */
public class MpInterfaceAccess {
    private static Logger logger = Logger.getLogger(WXConfig.class.getName());

    /**
     * 获取公众号AccessToken
     * 该参数需缓存
     * @Author kango2gler@gmail.com
     * @Date 2017/2/14 10:07
     * @return
     */
    public AccessTokenReturnBean getAccessToken(){
        MyAbstractHttp client = MyHttpUtil.getClient(WXConfig.getMpAccesstokenUrl());
        AccessTokenReturnBean rb = WXUtil.asReturnBean(client.get(),AccessTokenReturnBean.class);
        if(rb!=null)
            logger.info(rb.toString());
        return rb;
    }


    /**
     * <pre>
     * 获取jsApiTicket
     * 1.参考以下文档获取access_token（有效期7200秒，开发者必须在自己的服务全局缓存access_token）：../15/54ce45d8d30b6bf6758f68d2e95bc627.html
     * 2.用第一步拿到的access_token 采用http GET方式请求获得jsapi_ticket（有效期7200秒，开发者必须在自己的服务全局缓存jsapi_ticket）：https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
     * 返回(成功):{
         "errcode":0,
         "errmsg":"ok",
         "ticket":"bxLdikRXVbTPdHSM05e5u5sUoXNKd8-41ZO3MhKoyN5OfkWITDGgnr2fwJ0m9E8NYzWKVZvdVtaUgWvsdshFKA",
         "expires_in":7200
         }
         3.获得jsapi_ticket之后，就可以生成JS-SDK权限验证的签名了。
         有效时间2小时,所以需缓存2小时
     </pre>
     * @param
     * @Author kango2gler@gmail.com
     * @Date 2017/1/8 23:31
     * @return
     */
    public JSApiTicketReturnBean getJsApiTicket(String accessToken){
        String url = WXConfig.getMpJsSdkTicketUrl(accessToken);
        MyAbstractHttp client = MyHttpUtil.getClient(url);
        JSApiTicketReturnBean rb = WXUtil.asReturnBean(client.get(),JSApiTicketReturnBean.class);
        return rb;
    }
    /**
     * <pre>
     * JsApi权限验证获取sign
     * 获得jsapi_ticket之后，就可以生成JS-SDK权限验证的签名了。
         签名算法
         签名生成规则如下：参与签名的字段包括noncestr（随机字符串）, 有效的jsapi_ticket, timestamp（时间戳）, url（当前网页的URL，不包含#及其后面部分） 。对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）后，使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串string1。这里需要注意的是所有参数名均为小写字符。对string1作sha1加密，字段名和字段值都采用原始值，不进行URL 转义。
         即signature=sha1(string1)。 示例：
         noncestr=Wm3WZYTPz0wzccnW
         jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg
         timestamp=1414587457
         url=http://mp.weixin.qq.com?params=value
         步骤1. 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）后，使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串string1：
         jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg&noncestr=Wm3WZYTPz0wzccnW&timestamp=1414587457&url=http://mp.weixin.qq.com?params=value
         步骤2. 对string1进行sha1签名，得到signature：
         0f9de62fce790f9a083d5c99e95740ceb90c27ed

         String jsApiTicket,String url,String noncestr,Long timestamp
         其中前端发给后台的Url需encodeURIComponent()
         后台发送给微信接口的Url需URLDecoder.decode(),即完整的正常URL
     </pre>
     * @param
     * @Author kango2gler@gmail.com
     * @Date 2017/1/8 23:48
     * @return
     */
    public static JSApiTicketSignBean getJsApiSign(JSApiTicketSignBean signBean) throws Exception {
        String url = signBean.getUrl();
        if(url == null)
            url = "";
        url = CodecUtil.urlDecode(url);
        if(url.contains("#"))
            url = url.substring(0,url.indexOf("#"));
        //签名
        SortedMap<String, String> signParams = new TreeMap<String, String>();
        signParams.put("jsapi_ticket", signBean.getJsApiTicket());
        //单位 秒(s)
        signParams.put("timestamp", Long.parseLong(signBean.getTimestamp())/1000+"");
        signParams.put("noncestr", signBean.getNonceStr());
        signParams.put("url", url);
        String sign = Sha1Util.createSHA1Sign(signParams);
        signBean.setTimestamp(signParams.get("timestamp"));
        signBean.setSign(sign);
        return signBean;
    }


}
