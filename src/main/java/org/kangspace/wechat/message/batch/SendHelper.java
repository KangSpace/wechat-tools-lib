/**
 * File: PushHelper
 * DATE: 2016/12/21
 * Created by kango2gler@gmail.com
 */
package org.kangspace.wechat.message.batch;

import org.kangspace.wechat.bean.WeChatReturnBean;
import org.kangspace.wechat.message.MessageBean;
import org.kangspace.wechat.message.MessageTemplateSender;
import org.kangspace.wechat.util.AccessTokenInvalidException;
import org.kangspace.wechat.util.jdk18.Function;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author kango2gler@gmail.com
 * @desc 批量发送模版消息封装
 * @date 2016/12/21 18:39
 */
public class SendHelper {
    static Logger logger = Logger.getLogger(SendHelper.class.getName());

    /**
     * 发送消息
     * @param obj 推送对象
     * @param obj
     * @description 发送模版消息任务
     * @author xuefeng.kang
     * @see #send(String, SendObject, org.kangspace.wechat.util.jdk18.Function)
     * @return 是否成功执行消息发送操作,不表示消息最终成功或失败
     */
    public static <T> ForkJoinTask<Boolean> send(final String appId,final SendObject obj) {
        return send(appId,obj,null);
    }

    /**
     * 发送消息
     * @param obj 推送对象
     * @param reGetToken 当token失效时刷新token的操作,输入appId
     * @description 发送模版消息任务
     * @author xuefeng.kang
     * @return 是否成功执行消息发送操作,不表示消息最终成功或失败
     */
    public static <T> ForkJoinTask<Boolean> send(final String appId, final SendObject obj, final Function<String,String> reGetToken) {
        if (obj == null) {
            logger.severe("微信模版消息发送错误:错误信息:obj is null");
            return null;
        }
        try {
            RecursiveTask<Boolean> task = new RecursiveTask<Boolean>() {
                @Override
                protected Boolean compute() {
                    try {
                        logger.info("微信模版消息发送:推送信息:" + obj);
                        if (obj != null) {
                            //do something...
                            MessageTemplateSender sender = new MessageTemplateSender();
                            WeChatReturnBean returnBean = sender.send(obj.getMessageBean(), obj.getAccessToken());
                            if (WeChatReturnBean.isSuccess(returnBean)) {
                                logger.info("微信模版消息发送成功:推送信息:" + obj);
                                return Boolean.TRUE;
                            } else {
                                //token超时需抛出异常,重新获取token
                                //需考虑token失效后,重新处理未发送任务
                                logger.severe("微信模版消息发送错误:推送信息:" + obj + ",错误信息:" + returnBean);
                                if (WeChatReturnBean.isAccessTokenInvalid(returnBean)) {
                                    if (reGetToken == null) {
                                        throw new AccessTokenInvalidException(returnBean);
                                    }
                                    //消息发送失败后,重新获取access_token,重试一次
                                    logger.warning("微信模版消息发送错误Retry:推送信息:" + obj + ",微信AccessToken过期,重新发送消息开始!");
                                    String newAccessToken = reGetToken.apply(obj.getAppId()!=null?obj.getAppId():appId);
                                    obj.setAccessToken(newAccessToken);
                                    ForkJoinTask<Boolean> retrySend= send(appId,obj);
                                    Boolean retryResult = retrySend.get();
                                    logger.warning("微信模版消息发送错误Retry:推送信息:" + obj + ",微信AccessToken过期,重新发送消息结束!,发送结果:"+retryResult);
                                    return retryResult;
                                }
                            }
                        }
                    } catch (Exception e) {
                        logger.log(Level.WARNING,"微信模版消息发送错误:推送信息:" + obj + ",错误信息:" + e.getMessage(), e);
                    }
                    return Boolean.FALSE;
                }
            };
            taskExecute(task);
            return task;
        } catch (Exception e) {
            logger.log(Level.SEVERE,"微信模版消息发送错误:推送信息:" + obj + ",错误信息:" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 任务执行操作
     * @param task
     */
    private static void taskExecute(ForkJoinTask task) {
        ((ForkJoinPool)SendExecutorServices.getSendExecutorServices()).execute(task);
    }

    /**
     * 消息发送对象实体
     *
     * @Author kango2gler@gmail.com
     * @Date 2017/2/21 17:01
     */
    public static class SendObject {
        private String accessToken;
        private MessageBean messageBean;
        private String templateId;
        private String openId;
        private String appId;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public MessageBean getMessageBean() {
            return messageBean;
        }

        public void setMessageBean(MessageBean messageBean) {
            this.messageBean = messageBean;
        }

        public String getTemplateId() {
            return templateId;
        }

        public void setTemplateId(String templateId) {
            this.templateId = templateId;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public SendObject() {
        }

        public SendObject(String accessToken, MessageBean messageBean, String templateId, String openId) {
            this.accessToken = accessToken;
            this.messageBean = messageBean;
            this.templateId = templateId;
            this.openId = openId;
        }

        public SendObject(String accessToken, MessageBean messageBean, String templateId, String openId, String appId) {
            this.accessToken = accessToken;
            this.messageBean = messageBean;
            this.templateId = templateId;
            this.openId = openId;
            this.appId = appId;
        }

        @Override
        public String toString() {
            return "SendObject{" +
                    "accessToken='" + accessToken + '\'' +
                    ", messageBean=" + messageBean +
                    ", templateId='" + templateId + '\'' +
                    ", openId='" + openId + '\'' +
                    ", appId='" + appId + '\'' +
                    '}';
        }
    }
}
