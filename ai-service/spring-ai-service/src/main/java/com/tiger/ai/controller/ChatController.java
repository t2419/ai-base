package com.tiger.ai.controller;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

@RestController
@RequestMapping("/chat")
public class ChatController {


    @Autowired
    private ChatClient chatClient;

    @RequestMapping(value = "/chat", produces = "text/html;charset=utf-8")
    public String chat(String question) {

        String content = chatClient.prompt().user(question).call().content();

        return content;
    }


    @RequestMapping(value = "/stream", produces = "text/html;charset=utf-8")
    public Flux<String> chatForStream(String question,String chatId) {

        return chatClient.prompt().user(question)
                .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY,chatId))
                .stream().content();

    }
}
