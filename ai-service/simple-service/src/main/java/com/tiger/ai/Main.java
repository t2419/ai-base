package com.tiger.ai;

import dev.langchain4j.model.openai.OpenAiChatModel;

public class Main {


    public static void main(String[] args) {

        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("https://api.deepseek.com")
                .apiKey("sk-197a595ae4204cb4aebc1599ae8cb675")
                .modelName("deepseek-chat")
                .build();
        String answer = model.chat("Say 'Hello World'");
        model.chat();
        System.out.println(answer); // Hello World


    }
}
