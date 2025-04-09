package com.tiger.ai.service;


import com.alibaba.fastjson2.JSONObject;
import com.tiger.ai.common.RedisConstant;
import com.tiger.ai.vo.ChatMessageVO;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ChatServiceImpl implements ChatService {


    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public List chatHistory(String userId, String conversationId) {

        userId = RedisConstant.CHAT_TALKING_ID + userId + ":" + conversationId;

        List historyList = redisTemplate.opsForList().range(userId, 0, -1).stream().map(message -> {
            Message parseObject = JSONObject.parseObject(message.toString(), Message.class);
            return new ChatMessageVO(parseObject);
        }).toList();


        return historyList;
    }

    @Override
    public List userHistory(String userId) {


        Set<String> keyes = redisTemplate.keys(RedisConstant.CHAT_TALKING_ID +userId+":*");

        RedisSerializer keySerializer = redisTemplate.getKeySerializer();
        List<List<ChatMessageVO>> objects = new ArrayList<>();
        List historyData = null;
        if (0<keyes.size()){
            historyData =  redisTemplate.executePipelined((RedisCallback<Object>) connection -> {

                for (String key : keyes){
                     connection.listCommands().lRange(keySerializer.serialize(key), 0, -1);
                }

                return null;
            });
        }

        for (Object object : historyData) {
            List<String> list = (List<String>) object;
            List<ChatMessageVO> chatMessageVOS = list.stream().map(message -> {
                Message parseObject = JSONObject.parseObject(message, Message.class);
                return new ChatMessageVO(parseObject);
            }).toList();
            objects.add(chatMessageVOS);
        }


        return objects;
    }
}
