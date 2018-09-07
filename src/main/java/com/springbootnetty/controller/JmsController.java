package com.springbootnetty.controller;

import com.alibaba.fastjson.JSONObject;
import com.springbootnetty.jms.Producer;
import com.springbootnetty.util.GlobalUserUtil;
import com.springbootnetty.util.SingletonMap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/jms")
public class JmsController {

    @Autowired
    Producer producer;

    @RequestMapping("/test")
    public String test(String cmd){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            ChannelGroup channels = GlobalUserUtil.channels;
            Map<String, ChannelId> map = SingletonMap.getInstance();
            ChannelId channelId = map.get("tcp");
            Channel channel = channels.find(channelId);
            ByteBuf buffer = channel.alloc().buffer();
            buffer.writeBytes(cmd.getBytes());
            ChannelFuture channelFuture = channel.writeAndFlush(buffer);
            System.out.println(channelFuture);
            jsonObject.put("data",channelFuture);
            jsonObject.put("code","0");
            jsonObject.put("msg","成功");
        } catch (Exception e) {
            jsonObject.put("code","-9001");
            jsonObject.put("msg","系统异常");
        }
        return jsonObject.toString();
    }
}
