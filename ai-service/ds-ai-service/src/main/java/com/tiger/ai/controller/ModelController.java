package com.tiger.ai.controller;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;

@RestController
@RequestMapping("/model")
public class ModelController {


    @Autowired
    private OpenAiChatModel openAiChatModel;


    @RequestMapping("/list")
    public void model() {

        String content = openAiChatModel.call("列出模型");
        System.out.println(content);


    }

}
