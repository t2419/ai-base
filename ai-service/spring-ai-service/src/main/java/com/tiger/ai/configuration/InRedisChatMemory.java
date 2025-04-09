package com.tiger.ai.configuration;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.model.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
public class InRedisChatMemory implements ChatMemory {


    private static final String CHAT_MEMORY_KEY = "chat_talking_id:";


    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void add(String conversationId, List<Message> messages) {
        conversationId = CHAT_MEMORY_KEY + conversationId;
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> list = messages.stream().map(message -> {
            try {
                return objectMapper.writeValueAsString(message);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
//            return JSONObject.toJSONString(message);
        }).toList();
        redisTemplate.opsForList().rightPushAll(conversationId, list);
        System.out.println("redis存储：" + messages);
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        ObjectMapper objectMapper = new ObjectMapper();
        conversationId = CHAT_MEMORY_KEY + conversationId;
        List<String> messageList = redisTemplate.opsForList().range(conversationId, 0, -1);
        return messageList != null ? messageList.stream().map(message ->

                {
                    Message msg = null;
                    Message parsedObject = JSONObject.parseObject(message, Message.class);

                    Map map = JSONObject.parseObject(message, Map.class);
                    switch (parsedObject.getMessageType())
                        {

                            case USER:
                             msg =  new UserMessage(parsedObject.getText(), (Collection<Media>) map.get("media"), parsedObject.getMetadata());
                            break;

                            case ASSISTANT:
                             msg =  new AssistantMessage(parsedObject.getText(),  parsedObject.getMetadata(), (List<AssistantMessage.ToolCall>) map.get("toolCalls"), (List<Media>) map.get("media"));
                            break;

                  }

                    return msg;
                }

        ).skip(Math.max(0, messageList.size() - lastN)).toList() : List.of();

    }

    @Override
    public void clear(String conversationId) {
        conversationId = CHAT_MEMORY_KEY + conversationId;
        redisTemplate.delete(conversationId);
//        this.conversationHistory.remove(conversationId);
    }
}
