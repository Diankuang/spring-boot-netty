package com.springbootnetty.util;

import io.netty.channel.ChannelId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SingletonMap {

    private static  Map<String,ChannelId> map;

    public SingletonMap() {
    }

    public static synchronized Map<String,ChannelId> getInstance(){
        if(map == null){
            map = new ConcurrentHashMap<String,ChannelId>();
        }
        return map;
    }
}
