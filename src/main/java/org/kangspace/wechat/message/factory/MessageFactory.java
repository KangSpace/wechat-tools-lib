package org.kangspace.wechat.message.factory;

import org.kangspace.wechat.message.FourDataMessageTemplateDataBean;
import org.kangspace.wechat.message.MessageBean;
import org.kangspace.wechat.message.MessageTemplateDataValueColorBean;
import org.kangspace.wechat.message.NotificationMessageTemplateDataBean;
import org.kangspace.wechat.message.batch.SendHelper;
/**
 * <pre>
 * 通知消息相关工厂类
 * </pre>
 *
 * @author kango2gler@gmail.com
 * @since 2020/11/12 17:54
 */
public interface MessageFactory {

    /**
     * 默认颜色
     */
    String DEFAULT_COLOR = "#173177";
    /**
     * 默认颜色
     */
    String BLACK_COLOR = "#000";

    /**
     * 3个key的通知消息工厂
     */
    class ThreeKeyMessageFactory implements MessageFactory {
        /**
         * first字段默认值
         */
        public static String DEFAULT_FIRST_VAL = "";
        /**
         * remark字段默认值
         */
        public static String DEFAULT_REMARK_VAL = "";
        /**
         * key3字段默认值
         */
        public static String DEFAULT_KEY3_VAL = "";

        public static SendHelper.SendObject get(String appId, String accessToken, String templateId, String openId, String key1Val, String key2Val, MessageBean.Miniprogram miniprogram) {
            return get(appId,accessToken, templateId, openId, DEFAULT_FIRST_VAL, DEFAULT_REMARK_VAL, DEFAULT_COLOR, key1Val, key2Val, DEFAULT_KEY3_VAL,null,miniprogram);
        }
        /**
         * 获取新的消息发送对象
         * @param accessToken accessToken
         * @param templateId templateId
         * @param openId openId
         * @param firstVal firstVal
         * @param remarkVal remarkVal
         * @param color color
         * @param key1Val key1Val
         * @param key2Val key2Val
         * @param key3Val key3Val
         * @return SendHelper.SendObject
         */
        public static SendHelper.SendObject get(String appId,String accessToken, String templateId, String openId,String firstVal,String remarkVal,String color,String key1Val,String key2Val,String key3Val,
                                                String url,MessageBean.Miniprogram miniprogram) {
            MessageBean messageBean = new MessageBean();
            messageBean.setTemplateId(templateId);
            messageBean.setTouser(openId);
            messageBean.setMiniprogram(miniprogram);
            messageBean.setUrl(url);
            messageBean.setData(new NotificationMessageTemplateDataBean(
                    new MessageTemplateDataValueColorBean(firstVal,BLACK_COLOR),
                    new MessageTemplateDataValueColorBean(remarkVal,BLACK_COLOR),
                    new MessageTemplateDataValueColorBean(key1Val,color),
                    new MessageTemplateDataValueColorBean(key2Val,color),
                    new MessageTemplateDataValueColorBean(key3Val,color)
            ));
            return new SendHelper.SendObject(accessToken, messageBean, templateId, openId,appId);
        }
    }

    /**
     * 4个key通知消息工厂
     */
    class FourKeyMessageFactory implements MessageFactory {
        /**
         * first字段默认值
         */
        public static String DEFAULT_FIRST_VAL = "";
        /**
         * remark字段默认值
         */
        public static String DEFAULT_REMARK_VAL = null;

        /**
         * 获取新的消息发送对象
         * @param appId
         * @param accessToken
         * @param templateId
         * @param openId
         * @param firstVal
         * @param remarkVal
         * @param key1Val
         * @param key2Val
         * @param key3Val
         * @param key4Val
         * @param url
         * @param miniprogram
         * @return
         */
        public static SendHelper.SendObject get(String appId,String accessToken, String templateId, String openId,String firstVal,String remarkVal,String key1Val,String key2Val,
                                                String key3Val,String key4Val,String url,MessageBean.Miniprogram miniprogram) {
            return get(appId,accessToken, templateId, openId,DEFAULT_COLOR, firstVal, remarkVal, key1Val, key2Val, key3Val,key4Val,url,miniprogram);
        }

        /**
         *  获取新的消息发送对象
         * @param appId
         * @param accessToken
         * @param templateId
         * @param openId
         * @param color
         * @param firstVal
         * @param key1Val
         * @param key2Val
         * @param key3Val
         * @param key4Val
         * @param url
         * @param miniprogram
         * @return
         */
        public static SendHelper.SendObject get(String appId,String accessToken, String templateId, String openId,String color,String firstVal,String remarkVal,String key1Val,
                                                String key2Val,String key3Val,String key4Val,String url,MessageBean.Miniprogram miniprogram) {
            MessageBean messageBean = new MessageBean();
            messageBean.setTemplateId(templateId);
            messageBean.setTouser(openId);
            messageBean.setUrl(url);
            messageBean.setMiniprogram(miniprogram);
            messageBean.setData(new FourDataMessageTemplateDataBean(
                    new MessageTemplateDataValueColorBean(firstVal),
                    new MessageTemplateDataValueColorBean(remarkVal,BLACK_COLOR),
                    new MessageTemplateDataValueColorBean(key1Val,color),
                    new MessageTemplateDataValueColorBean(key2Val,color),
                    new MessageTemplateDataValueColorBean(key3Val,color),
                    new MessageTemplateDataValueColorBean(key4Val,color)
            ));
            return new SendHelper.SendObject(accessToken, messageBean, templateId, openId,appId);
        }
    }
}
