package com.tiger.ai.service;

import java.util.List;

public interface ChatService {


    public List chatHistory(String userId,String conversationId);
    public List userHistory(String userId);

}
