package org.kangspace.wework.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kangspace.wechat.config.WeChatConfig;
import org.kangspace.wechat.util.WeChatUtil;
import org.kangspace.wechat.util.http.MyAbstractHttp;
import org.kangspace.wechat.util.http.MyHttpUtil;

import java.util.logging.Logger;

/**
 * @author kango2gler@gmail.com
 * @desc 企业微信接口访问
 * @date 2021/08/26 22:38:47
 */
public class WeWorkMessageSender {
    private static Logger logger = Logger.getLogger(WeWorkMessageSender.class.getName());
    /**
     * 发送模版消息
     * 需提前刷新accessToken
     * @param weWorkMessageBean 模版内容 templateId在该对象中
     * @param accessToken accessToken
     * @author kango2gler@gmail.com
     * @date 2017/2/13 17:20
     * @return
     */
    public WeWorkMessageReturnBean send(WeWorkMessageBean weWorkMessageBean, String accessToken){
        MyAbstractHttp client = MyHttpUtil.getClient(WeChatConfig.getWeWorkMessageSendUrl(accessToken));
        ObjectMapper jsonMapper = new ObjectMapper();
        String postData;
        try {
            postData = jsonMapper.writeValueAsString(weWorkMessageBean);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
        logger.info("WeWorkMessageSender send post data:"+postData);
        WeWorkMessageReturnBean rb = WeChatUtil.asReturnBean(client.post(postData),WeWorkMessageReturnBean.class);
        if (rb != null) {
            logger.info(rb.toString());
        }
        return rb;
    }
}
