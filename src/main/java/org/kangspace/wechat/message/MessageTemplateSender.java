package org.kangspace.wechat.message;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kangspace.wechat.bean.WeChatReturnBean;
import org.kangspace.wechat.config.WeChatConfig;
import org.kangspace.wechat.util.WeChatUtil;
import org.kangspace.wechat.util.http.MyAbstractHttp;
import org.kangspace.wechat.util.http.MyHttpUtil;

import java.util.logging.Logger;

/**
 * @author kango2gler@gmail.com
 *  模版消息发送类
 * @since 2017/2/13 17:20
 */
public class MessageTemplateSender {
    private static Logger logger = Logger.getLogger(WeChatConfig.class.getName());
    /**
     * 发送模版消息
     * 需提前刷新accessToken
     * @param messageBean 模版内容 templateId在该对象中
     * @param accessToken accessToken
     * @author kango2gler@gmail.com
     * @since 2017/2/13 17:20
     * @return WeChatReturnBean
     */
    public WeChatReturnBean send(MessageBean messageBean, String accessToken){
        MyAbstractHttp client = MyHttpUtil.getClient(WeChatConfig.getMpMessageTemplateSendUrl(accessToken));
        ObjectMapper jsonMapper = new ObjectMapper();
        String postData;
        try {
            postData = jsonMapper.writeValueAsString(messageBean);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
        logger.info("WeChatReturnBean send post data:"+postData);
        WeChatReturnBean rb = WeChatUtil.asReturnBean(client.post(postData),WeChatReturnBean.class);
        if (rb != null) {
            logger.info(rb.toString());
        }
        return rb;
    }
}
