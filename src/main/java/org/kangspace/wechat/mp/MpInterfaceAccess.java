package org.kangspace.wechat.mp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.kangspace.wechat.bean.WeChatCapableRetrieveToken;
import org.kangspace.wechat.bean.WeChatReturnBean;
import org.kangspace.wechat.config.WeChatConfig;
import org.kangspace.wechat.util.AccessTokenInvalidException;
import org.kangspace.wechat.util.WeChatUtil;
import org.kangspace.wechat.util.encryption.Sha1Util;
import org.kangspace.wechat.util.http.MyAbstractHttp;
import org.kangspace.wechat.util.http.MyHttpUtil;
import org.kangspace.wechat.util.jdk18.Consumer;
import org.kangspace.wechat.util.jdk18.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author kango2gler@gmail.com
 * @desc 公众号接口访问
 * @date 2017/2/14 9:59
 */
public class MpInterfaceAccess extends WeChatCapableRetrieveToken {
    private static Logger logger = Logger.getLogger(MpInterfaceAccess.class.getName());

    /**
     * 获取公众号AccessToken
     * 该参数需缓存
     * @author kango2gler@gmail.com
     * @date 2017/2/14 10:07
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
     * @date 2017/2/14 10:07
     * @return
     * @see #getAccessToken()
     */
    public AccessTokenReturnBean getAccessToken(String appId){
        MyAbstractHttp client = MyHttpUtil.getClient(WeChatConfig.getMpAccessTokenUrl(appId));
        AccessTokenReturnBean rb = WeChatUtil.asReturnBean(client.get(),AccessTokenReturnBean.class);
        if (rb != null) {
            logger.info(rb.toString());
        }
        return rb;
    }

    /**
     * 获取用户列表信息
     * @param accessToken
     * @param nextOpenId
     * @return
     */
    public MpUserListReturnBean getUserList(String accessToken, String nextOpenId) {
        MyAbstractHttp client = MyHttpUtil.getClient(WeChatConfig.getMpUserList(accessToken,nextOpenId));
        MpUserListReturnBean rb = WeChatUtil.asReturnBean(client.get(),MpUserListReturnBean.class);
        if (rb != null) {
            logger.info(rb.toString());
        }
        return rb;
    }

    /**
     * 获取用户基本信息
     * @param accessToken
     * @param openId 当前用户openId
     * @return
     */
    public MpUserBasicInfoReturnBean getUserInfo(String accessToken,String openId) {
        MyAbstractHttp client = MyHttpUtil.getClient(WeChatConfig.getMpUserInfo(accessToken,openId));
        MpUserBasicInfoReturnBean rb = WeChatUtil.asReturnBean(client.get(),MpUserBasicInfoReturnBean.class);
        if (rb != null) {
            logger.info(rb.toString());
        }
        return rb;
    }

    /**
     * 批量获取用户基本信息(每次最多100条)
     * @param accessToken
     * @param params openIds
     * @return
     */
    public MpUserBasicInfoBatchReturnBean getMpUserInfoBatch(String accessToken,MpUserInfoBatchParamDTO params) {
        MyAbstractHttp client = MyHttpUtil.getClient(WeChatConfig.getMpUserInfoBatch(accessToken));
        String param;
        try {
            param = new ObjectMapper().writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(params + "," + e.getMessage(), e);
        }
        MpUserBasicInfoBatchReturnBean rb = WeChatUtil.asReturnBean(client.post(param),MpUserBasicInfoBatchReturnBean.class);
        if (rb != null) {
            logger.info(rb.toString());
        }
        return rb;
    }



    /**
     * 批量获取用户列表信息
     * @param accessToken
     * @param nextOpenId
     * @param consumer 用户基础信息消费者
     * @return
     */
    public void getMpUserListBatch(String appId, String accessToken, String nextOpenId, Consumer<List<MpUserBasicInfoReturnBean>> consumer, Function<String,String> retrieveTokenFn) {
        getMpUserListBatch(appId,accessToken,nextOpenId,consumer,retrieveTokenFn,MpUserInfoBatchParamDTO.MAX_BATCH_SIZE);
    }

    /**
     * 批量获取用户列表信息
     * @param appId
     * @param accessToken
     * @param nextOpenId
     * @param consumer
     * @param retrieveTokenFn
     * @param batchSize
     * @see #getMpUserListBatch(String, String, String, Consumer, Function)
     */
    public void getMpUserListBatch(final String appId, String accessToken, final String nextOpenId, final Consumer<List<MpUserBasicInfoReturnBean>> consumer, Function<String,String> retrieveTokenFn, Integer batchSize) {
        MpUserListReturnBean userList = getUserList(accessToken, nextOpenId);
        if (!WeChatReturnBean.isSuccess(userList)) {
            logger.log(Level.SEVERE,"获取用户列表信息失败:" + userList);
            if (WeChatReturnBean.isAccessTokenInvalid(userList)) {
                if (retrieveTokenFn == null) {
                    throw new AccessTokenInvalidException(userList);
                }
                //消息发送失败后,重新获取access_token,重试一次
                logger.log(Level.SEVERE,"获取用户列表信息失败Retry,微信AccessToken过期,重新请求!: appId:"+appId+",nextOpenId:"+nextOpenId);
                retrieveTokenRetry(appId, userList, retrieveTokenFn,new Function<String,String>(){
                    @Override
                    public String apply(String newAccessToken) {
                        getMpUserListBatch(appId, newAccessToken, nextOpenId, consumer, null);
                        return null;
                    }
                });
                logger.log(Level.SEVERE,"获取用户列表信息失败Retry,微信AccessToken过期,重新请求结束!: appId:"+appId+",nextOpenId:"+nextOpenId);
                return ;
            }
            return ;
        }
        if (userList == null || userList.getData() == null || CollectionUtils.isEmpty(userList.getData().getOpenId())) {
            logger.warning("获取用户列表信息-获取信息为空,参数:accessToken:" + accessToken + ",nextOpenId:" + nextOpenId);
            return ;
        }
        //拉取数据
        List<String> openIds = userList.getData().getOpenId();
        MpUserInfoBatchParamDTO batchParamDTO;
        List<MpUserInfoBatchParamDTO.UserOpenIdParam> userOpenIdParams = new ArrayList<>();
        for (int i = 0, len, nexti = batchSize; i < (len = openIds.size()); i += batchSize, nexti = i + batchSize) {
            int toIdx = nexti >= len ? len : nexti;
            for (String t : openIds.subList(i, toIdx)) {
                userOpenIdParams.add(new MpUserInfoBatchParamDTO.UserOpenIdParam(t));
            }
            batchParamDTO = new MpUserInfoBatchParamDTO(userOpenIdParams);
            try {
                MpUserBasicInfoBatchReturnBean userInfoBatch = getMpUserInfoBatch(accessToken, batchParamDTO);
                if (WeChatReturnBean.isSuccess(userInfoBatch)) {
                    consumer.accept(userInfoBatch.getUserInfoList());
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE,"获取用户列表信息-批量获取用户基本信息错误,openIds="+userOpenIdParams+",错误信息:"+e.getMessage(),e);
            }
        }
        //查询下一页数据,当count和total相等时,也会返回next_open_id值
        if (userList.getNextOpenId() != null && userList.getCount()<userList.getTotal()) {
            getMpUserListBatch(appId,accessToken,userList.getNextOpenId(),consumer,retrieveTokenFn);
        }
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
     * @author kango2gler@gmail.com
     * @date 2017/1/8 23:31
     * @return
     */
    public JSApiTicketReturnBean getJsApiTicket(String accessToken){
        String url = WeChatConfig.getMpJsSdkTicketUrl(accessToken);
        MyAbstractHttp client = MyHttpUtil.getClient(url);
        JSApiTicketReturnBean rb = WeChatUtil.asReturnBean(client.get(),JSApiTicketReturnBean.class);
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
         其中的Url需encodeURIComponent
     </pre>
     * @param
     * @author kango2gler@gmail.com
     * @date 2017/1/8 23:48
     * @return
     */
    public static JSApiTicketSignBean getJsApiSign(JSApiTicketSignBean signBean) throws Exception {
        String url = signBean.getUrl();
        if (url.contains("#")) {
            url = url.substring(0, url.indexOf("#"));
        }
        //签名
        SortedMap<String, String> signParams = new TreeMap<String, String>();
        signParams.put("jsapi_ticket", signBean.getJsApiTicket());
        signParams.put("timestamp", signBean.getTimestamp());
        signParams.put("noncestr", signBean.getNonceStr());
        signParams.put("url", url);
        String sign = Sha1Util.createSHA1Sign(signParams);
        signBean.setSign(sign);
        return signBean;
    }


}
