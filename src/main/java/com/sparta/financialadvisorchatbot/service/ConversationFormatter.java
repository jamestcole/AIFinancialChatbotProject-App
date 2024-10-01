package com.sparta.financialadvisorchatbot.service;

import com.sparta.financialadvisorchatbot.entities.ConversationHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationFormatter {
    public String formatConversation(List<ConversationHistory> conversationHistory) {
        StringBuilder formattedConversation = new StringBuilder();

        for (ConversationHistory entry : conversationHistory) {
            String userInput = entry.getInput();
            String botResponse = entry.getResponse();

            formattedConversation.append("User: ").append(userInput).append("\n");
            formattedConversation.append("Bot: ").append(botResponse).append("\n");
        }

        return formattedConversation.toString();
    }

}
