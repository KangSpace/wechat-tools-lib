package org.kangspace.wechat.message;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.kangspace.wechat.ConfigTest;
import org.kangspace.wechat.bean.WeChatReturnBean;
import org.kangspace.wechat.config.WeChatConfig;
import org.kangspace.wechat.mp.MpInterfaceAccessTest;

/**
 * @author kango2gler@gmail.com
 *  消息测试类
 * @since 2017/2/14 9:41
 */
@RunWith(JUnit4.class)
public class MessageTest {

     /**
     * 发送模版消息
     * 需先获取accessToken {@link MpInterfaceAccessTest}
     * @author kango2gler@gmail.com
     * @since 2017/2/14 10:29
     *
     */
    @Test
    public void sendMsg(){
        String templateId = WeChatConfig.MP_MESSAGE_TEMPLATE_IDS;
        String openId = "asdasda";
        String url = "http://127.0.0.1://?openId="+openId;
        MessageTemplateSender sender = new MessageTemplateSender();
        MessageBean messageBean = new MessageBean();
        messageBean.setTemplateId(templateId);
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
//        keyword3.setValue("我的登录时间");
        keyword3.setValue("我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间\n" +
                "我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间" +
                "我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间" +
                "我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间" +
                "我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间" +
                "我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间" +
                "我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间" +
                "我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间" +
                "我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间" +
                "我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间" +
                "我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间" +
                "我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间我的登录时间");
        keyword3.setColor(color);
        notifyBean.setKeyword3(keyword3);
        messageBean.setData(notifyBean);

        WeChatReturnBean returnBean = sender.send(messageBean, ConfigTest.ACCESS_TOKEN);
        System.out.println(returnBean);
    }
}
