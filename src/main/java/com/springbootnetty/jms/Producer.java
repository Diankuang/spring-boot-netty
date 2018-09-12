package com.springbootnetty.jms;

import com.springbootnetty.util.GlobalUserUtil;
import com.springbootnetty.util.SingletonMap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.Map;

@Component
public class Producer {

    @Autowired
    private JmsTemplate jmsTemplate;


    public void textMessage() {
        jmsTemplate.convertAndSend("jms.topic.TcpToHttp", "通知开启实时传事务");
    }

    @JmsListener(destination = "jms.topic.HttpToTcp")
    public void getTest(Object text) {

//        ActiveMQMapMessage  am = (ActiveMQMapMessage) text;
//        sendReplyMessage("success", am.getJMSReplyTo(),am.getCorrelationId());

        try {
            ActiveMQTextMessage am = (ActiveMQTextMessage) text;
            String amText = am.getText();
            System.out.println(am);
            System.out.println(amText);
            String[] split = amText.split("=");
            String macaddr = split[0];
            String cm = split[1];
            Map<String, ChannelId> map = SingletonMap.getInstance();
            ChannelId channelId = map.get(macaddr);
            if(channelId!=null){
                ChannelGroup channels = GlobalUserUtil.channels;
                Channel channel = channels.find(channelId);
                ByteBuf buffer = channel.alloc().buffer();
                buffer.writeBytes(cm.getBytes());
                channel.writeAndFlush(buffer);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
//        sendReplyMessage("success", am.getJMSReplyTo(),am.getCorrelationId());

    }

    public void sendReplyMessage(String msg, Destination replyTo, String correlationID) {
        try {
            jmsTemplate.send
                    (replyTo, session -> {
                        TextMessage creator = session.createTextMessage();
                        creator.setText(msg);
                        creator.setJMSCorrelationID(correlationID);
                        return creator;
                    });
        } catch (JmsException e) {
            e.printStackTrace();
        }
    }

}
