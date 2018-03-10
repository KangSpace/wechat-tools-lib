/**
 * File: PushHelper
 * DATE: 2016/12/21
 * Created by kango2gler@gmail.com
 */
package com._20dot.weixin.message.batchmsg;

import com._20dot.weixin.message.MessageTemplateSender;
import com._20dot.weixin.util.WXReturnBean;
import com._20dot.weixin.message.MessageBean;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @desc 批量发送模版消息封装
 *
 * @date 2016/12/21 18:39
 * @author kango2gler@gmail.com
 *
 */
public class SendHelper {
    private static Logger logger = Logger.getLogger(SendHelper.class.getName());

    /**
     *
     * @description 发送模版消息任务
     * @param obj 推送对象
     * @author xuefeng.kang
     * @param obj
     */
    public static void send(final SendObject obj) {
        if(obj == null){
            logger.log(Level.SEVERE,"微信模版消息发送，错误信息:obj is null");
            return ;
        }
        try {
            SendExecutorServices.getSendExecutorServices().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        logger.info("微信模版消息发送:推送信息:"+obj);
                        if(obj!=null){
                            //do something...
                            MessageTemplateSender sender = new MessageTemplateSender();
                            /*
                            String templateId = WXConfig.MP_MESSAGE_TEMPLATE_IDS;
                            String openId = "asdasdasdasdas";
                            String url = "http://127.0.0.1://?openId="+openId;
                            MessageBean messageBean = new MessageBean();
                            messageBean.setTemplate_id(templateId);
                            messageBean.setTouser(openId);
                            //messageBean.setUrl(url);
                            String color = "#173177";
                            NotificationMessageTemplateDataBean notifyBean = new NotificationMessageTemplateDataBean();
                            MessageTemplateDataValueColorBean first = new MessageTemplateDataValueColorBean();
                            first.setValue("模版消息推送测试,first字段内容,不可点击查看");
                            first.setColor(color);
                            notifyBean.setFirst(first);
                            MessageTemplateDataValueColorBean remark = new MessageTemplateDataValueColorBean();
                            remark.setValue("模版消息推送成功\n\n点击查看");
                            remark.setColor(color);
                            notifyBean.setRemark(remark);
                            MessageTemplateDataValueColorBean keyword1 = new MessageTemplateDataValueColorBean();
                            keyword1.setValue("我的登录名");
                            keyword1.setColor(color);
                            notifyBean.setKeyword1(keyword1);
                            MessageTemplateDataValueColorBean keyword2 = new MessageTemplateDataValueColorBean();
                            keyword2.setValue("我的城市");
                            keyword2.setColor(color);
                            notifyBean.setKeyword2(keyword2);
                            MessageTemplateDataValueColorBean keyword3 = new MessageTemplateDataValueColorBean();
                            keyword3.setValue("我的登录时间");
                            keyword3.setColor(color);
                            notifyBean.setKeyword3(keyword3);
                            messageBean.setData(notifyBean);*/

                            WXReturnBean returnBean = sender.send(obj.getMessageBean(),obj.getAccessToken());
                            if(returnBean == null || (returnBean.getErrcode() != null && !new Integer(0).equals(returnBean.getErrcode()))) {
                                logger.log(Level.SEVERE,"微信模版消息发送错误,错误信息:"+returnBean);
                            }
                        }
                    } catch (Exception e) {
                        logger.log(Level.SEVERE,"微信模版消息发送:错误信息:"+e.getMessage(),e);
                    }
                }
            });
        } catch (Exception e) {
            logger.log(Level.SEVERE,"微信模版消息发送:错误信息:"+e.getMessage(),e);
        }
    }

    /**
     * 消息发送对象实体
     * @Author kango2gler@gmail.com
     * @Date 2017/2/21 17:01
     * @return
     */
    public static class SendObject{
        private String accessToken;
        private MessageBean messageBean;
        private String templateId;
        private String openId;

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

        public SendObject() {
        }

        public SendObject(String accessToken, MessageBean messageBean, String templateId, String openId) {
            this.accessToken = accessToken;
            this.messageBean = messageBean;
            this.templateId = templateId;
            this.openId = openId;
        }

        @Override
        public String toString() {
            return "SendObject{" +
                    "accessToken='" + accessToken + '\'' +
                    ", messageBean=" + messageBean +
                    ", templateId='" + templateId + '\'' +
                    ", openId='" + openId + '\'' +
                    '}';
        }
    }
}
