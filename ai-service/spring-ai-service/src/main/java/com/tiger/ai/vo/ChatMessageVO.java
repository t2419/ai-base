package com.tiger.ai.vo;

import org.springframework.ai.chat.messages.Message;

public class ChatMessageVO {


    private String role;
    private String content;

    public String getRole() {
        return role;
    }

    public ChatMessageVO setRole(String role) {
        this.role = role;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ChatMessageVO setContent(String content) {
        this.content = content;
        return this;
    }

    public ChatMessageVO(Message message) {
        switch (message.getMessageType()) {
            case USER:
                this.role = "user";
                break;
            case ASSISTANT:
                this.role = "assistant";
                break;
            case SYSTEM:
                this.role = "system";
                break;
                case TOOL:
                this.role = "tool";
                break;
            default:
                this.role = "unknown";
                break;
        }

        this.content = message.getText();
    }
}
