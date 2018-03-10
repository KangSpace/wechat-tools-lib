package com._20dot.weixin.message;

import com._20dot.weixin.config.WXConfig;
import com._20dot.weixin.util.WXReturnBean;
import com._20dot.weixin.util.http.MyAbstractHttp;
import com._20dot.weixin.util.http.MyHttpUtil;
import com._20dot.weixin.util.WXUtil;
import net.sf.json.JSONObject;

import java.util.logging.Logger;

/**
 * @author kango2gler@gmail.com
 * @desc 模版消息发送类
 * @date 2017/2/13 17:20
 */
public class MessageTemplateSender {
    private static Logger logger = Logger.getLogger(WXConfig.class.getName());
    /**
     * 发送模版消息
     * 需提前刷新accessToken
     * @param messageBean 模版内容 templateId在该对象中
     * @param accessToken accessToken
     * @Author kango2gler@gmail.com
     * @Date 2017/2/13 17:20
     * @return
     */
    public WXReturnBean send(MessageBean messageBean, String accessToken){
        MyAbstractHttp client = MyHttpUtil.getClient(WXConfig.getMpMessageTemplateSendUrl(accessToken));
        String postData = JSONObject.fromObject(messageBean).toString();
        WXReturnBean rb = WXUtil.asReturnBean(client.post(postData),WXReturnBean.class);
        if(rb!=null)
            logger.info(rb.toString());
        return rb;
    }
}
