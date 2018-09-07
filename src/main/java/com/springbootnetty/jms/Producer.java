package com.springbootnetty.jms;

import com.springbootnetty.util.GlobalUserUtil;
import com.springbootnetty.util.SingletonMap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Producer {

    @Autowired
    private JmsTemplate jmsTemplate;


    public void textMessage() {
        jmsTemplate.convertAndSend("jms.topic.TcpToHttp", "通知开启实时传事务");
    }

    @JmsListener(destination = "jms.topic.HttpToTcp")
    public void getTest(String text) {
        String[] split = text.split(":");
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
        System.out.println(text);
    }

}
