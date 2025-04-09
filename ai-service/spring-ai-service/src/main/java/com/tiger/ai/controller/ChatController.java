package com.tiger.ai.controller;


import com.tiger.ai.common.ResultMsg;
import com.tiger.ai.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

@RestController
@RequestMapping("/chat")
public class ChatController {


    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ChatService chatService;

    @RequestMapping(value = "/chat/{userId}/{conversationId}", produces = "text/html;charset=utf-8")
    public String chat(String question, @PathVariable("userId") String userId, @PathVariable("conversationId") String conversationId) {

        String content = chatClient.prompt().user(question).call().content();

        return content;
    }


    @RequestMapping(value = "/stream/{userId}/{conversationId}", produces = "text/html;charset=utf-8")
    public Flux<String> chatForStream(String question, @PathVariable("userId") String userId, @PathVariable("conversationId") String conversationId) {

        return chatClient.prompt().user(question)
                .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, userId + ":" + conversationId))
                .stream().content();

    }


    @RequestMapping(value = "/chatHistory/{userId}/{conversationId}")
    public ResultMsg chatHistory(@PathVariable("userId") String userId, @PathVariable("conversationId") String conversationId) {
        List history = chatService.chatHistory(userId,conversationId);
        return ResultMsg.SUCCESS(history);

    }

    @RequestMapping(value = "/userHistory/{userId}")
    public ResultMsg userHistory(@PathVariable("userId") String userId) {
        List history = chatService.userHistory(userId);
        return ResultMsg.SUCCESS(history);

    }
}
