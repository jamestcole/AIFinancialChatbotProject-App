package com.sparta.financialadvisorchatbot.models;

public class ConversationState {
    private String lastQuestion;
    private String lastResponse;
    private boolean awaitingClarification;

    public ConversationState() {
        this.awaitingClarification = false;
    }

    public String getLastQuestion() {
        return lastQuestion;
    }

    public void setLastQuestion(String lastQuestion) {
        this.lastQuestion = lastQuestion;
    }

    public String getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(String lastResponse) {
        this.lastResponse = lastResponse;
    }

    public boolean isAwaitingClarification() {
        return awaitingClarification;
    }

    public void setAwaitingClarification(boolean awaitingClarification) {
        this.awaitingClarification = awaitingClarification;
    }

}
