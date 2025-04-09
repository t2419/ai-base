package com.tiger.ai;


import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestUnit {


    @Autowired
    private ChatClient client;

    @Test
    public void test()
    {
        String content = client.prompt("你是谁").call().content();

        String content1 = client.prompt().user("你是谁").call().content();
        System.out.println(content);
    }

}
