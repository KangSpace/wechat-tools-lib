# wechat-tools-lib

### 介绍(Introduction)
该项目集成封装微信公众号相关接口,并提供工具类访问接口。

### 项目实现功能：  
* 集成微信网页授权接口及网页授权获取用户信息
* 集成微信公众号获取Token及Token缓存
* 集成微信公众号拉取用户列表及用户信息
* 集成微信公众号发送模版消息
* 集成微信公众号JS签名

### API列表：
<table style="table-layout:fixed;text-align:left;">
    <thead>
        <tr><td>功能</td><td>类</td><td>方法</td><td>用法</td></tr>
    </thead>
    <tbody style="">
        <tr>
            <td rowspan="2">重定向到网页授权地址</td>
            <td rowspan="2">
                <small>org.kangspace.wechat.oauth2.</small><br>
                <code>OAuth2InterfaceAccess</code>
            </td>
            <td>
                <small>void </small>
                <code>redirectToAuthorizeUrl</code><br>
                <small><code>(String appId, String redirectURI, String param, HttpServletRequest request, HttpServletResponse response)</code>
                </code></small>
            </td>
            <td>
                <small>
                <code>oAth2InterfaceAccess.redirectToAuthorizeUrl(appId,url,state,request,response)</code>
                </small>
            </td>
        </tr>
        <tr>
            <td>
                <small>void </small>
                <code>redirectToAuthorizeUrl</code><br>
                <small>
                <code>(String appId,String redirectURI, String param, WeChatConfig.OAth2Scope scope, HttpServletRequest request, HttpServletResponse response)</code>
                </small>
            </td>
            </td>    
            <td><small>
                <code>oAth2InterfaceAccess.redirectToAuthorizeUrl(appId,redirectURI,param, WeChatConfig.OAth2Scope.SNSAPI_BASE,request,response)</code>
                </small>
            </td>
        </tr>
        <tr>
            <td>网页授权获取ACCESS_TOKEN</td>
            <td><small>org.kangspace.wechat.oauth2.</small><br>
                <code>OAuth2InterfaceAccess</code></td>
            <td><small>OAuth2AccessTokenReturnBean </small><code>getAccessToken(String code)</code></td>
            <td><small><code>oAth2InterfaceAccess.getAccessToken("codexxx")</code></small></td>
        </tr>
        <tr>
            <td>网页授权获取用户信息</td>
            <td><small>org.kangspace.wechat.oauth2.</small><br>
                <code>OAuth2InterfaceAccess</code></td>
            <td><small>OAuth2UserInfoReturnBean</small><code>getUserInfo(String accessToken,String openId,Lang lang)</code></td>
            <td><small><code>oAth2InterfaceAccess.getUserInfo(accessToken,openId, Lang.ZH_CN)</code></small></td>
        </tr>
        <tr>
            <td>获取微信公众号ACCESS_TOKEN</td>
            <td><small>org.kangspace.wechat.mp.</small><br>
                <code>MpInterfaceAccess</code></td>
            <td><small>AccessTokenReturnBean</small><code>getAccessToken(String appId)</code></td>
            <td><small><code>new MpInterfaceAccess().getAccessToken(appId)</code></small></td>
        </tr>
        <tr>
            <td rowspan=2>缓存获取微信公众号ACCESS_TOKEN(RedisTemplate)</td>
            <td><small>org.kangspace.wechat.cache.</small><br>
                <code>WeChatAccessTokenCache</code></td>
            <td><small>String</small><code>get(String appId,String key)</code></td>
            <td><small><code>new WeChatAccessTokenCache().getAccessToken(appId,"access_token")</code></small></td>
        </tr>
        <tr>
            <td><small>org.kangspace.wechat.cache.</small><br>
                <code>AbstractWeChatCacheOperator</code></td>
            <td><small>T</small><code>get(String appId,String key)</code></td>
            <td>实现抽象方法以控制缓存的操作</td>
        </tr>
        <tr>
            <td>获取微信公众号用户信息</td>
            <td><small>org.kangspace.wechat.mp.</small><br>
                <code>MpInterfaceAccess</code></td>
            <td><small>MpUserBasicInfoReturnBean</small><code>getUserInfo(String accessToken,String openId)</code></td>
            <td><small><code>new MpInterfaceAccess().getUserInfo("accessToken","openId")</code></small></td>
        </tr>
        <tr>
            <td>批量获取微信公众号关注用户列表</td>
            <td><small>org.kangspace.wechat.mp.</small><br>
                <code>MpInterfaceAccess</code></td>
            <td><small>MpUserListReturnBean</small><code>getUserList(String accessToken, String nextOpenId)</code></td>
            <td><small><code>new MpInterfaceAccess().getUserList("accessToken","nextOpenId")</code></small></td>
        </tr>
        <tr>
            <td>批量获取微信公众号关注用户列表详情</td>
            <td><small>org.kangspace.wechat.mp.</small><br>
                <code>MpInterfaceAccess</code></td>
            <td><small></small><code>getMpUserListBatch(String appId, String accessToken, String nextOpenId, Consumer<List<MpUserBasicInfoReturnBean>> consumer, Function<String,String> retrieveTokenFn)</code></td>
            <td><small><code>new MpInterfaceAccess().interfaceAccess.getMpUserListBatch(appId, accessToken, nextOpenId,
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
                                                                     })</code></small></td>
        </tr>
        <tr>
            <td rowspan=3>发送模版消息</td>
            <td><small>org.kangspace.wechat.message.</small><br>
                <code>MessageTemplateSender</code></td>
            <td><small>WeChatReturnBean </small><code>send(MessageBean messageBean, String accessToken)</code></td>
            <td><small><code>WeChatReturnBean returnBean = sender.send(messageBean, "accessToken");</code></small></td>
        </tr>
        <tr>
            <td><small>org.kangspace.wechat.message.batch.</small><br>
                <code>SendHelper</code></td>
            <td><small>ForkJoinTask<Boolean> </small><code>send(String appId, SendObject obj, Function<String,String> reGetToken)</code></td>
            <td><small><code>SendHelper.send(appId,message,(t)-> wxAccessTokenCache.refreshByAppId(appId))</code></small></td>
        </tr>
        <tr>
            <td><small>org.kangspace.wechat.message.factory</small><br>
                <code>MessageFactory.ThreeKeyMessageFactory</code></td>
            <td><small>SendHelper.SendObject </small><code>get(String appId, String accessToken, String templateId, String openId, String key1Val, String key2Val, MessageBean.Miniprogram miniprogram)</code></td>
            <td> -- </td>
        </tr>
    </tbody>
</table>

### 快速开始：
1. 项目环境:
   <code>Maven,JDK1.7+,UTF-8</code><br>
   <code>需在项目classes下配置wechat-config.properties,参考</code>[resources/wechat-config-example.properties](src/main/resources/wechat-config-example.properties)
2. 使用"API列表"测试接口调用
