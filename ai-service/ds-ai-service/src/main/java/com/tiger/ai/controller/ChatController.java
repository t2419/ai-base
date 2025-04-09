package com.tiger.ai.controller;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {


    @Autowired
    private ChatClient chatClient;



    @RequestMapping("/chat")
    public void chat()
    {


        String content = chatClient.prompt("你是谁").call().content();

        System.out.println(content);


    }


}
