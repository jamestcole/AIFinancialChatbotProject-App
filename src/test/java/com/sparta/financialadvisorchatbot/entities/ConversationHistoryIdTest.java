package com.sparta.financialadvisorchatbot.entities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ConversationHistoryIdTest {

    ConversationHistoryId conversationHistoryId;

    @BeforeEach void setUp() {
        conversationHistoryId = new ConversationHistoryId();
        conversationHistoryId.setConversationId(1);
        conversationHistoryId.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void testGetConversationHistoryId() {
        Integer expected = 1;
        Integer actual = conversationHistoryId.getConversationId();
        assertEquals(expected,actual);
    }
    @Test
    void testGetCreatedAt() {
        LocalDateTime expected = LocalDateTime.now();
        LocalDateTime actual = conversationHistoryId.getCreatedAt();
        assertEquals(expected, actual);
    }

    @Test
    void testEqualsReturnsTrueIfObjectsAreEqual() {
        ConversationHistoryId other = conversationHistoryId;
        boolean result = conversationHistoryId.equals(other);
        assertTrue(result);
    }

    @Test
    void testEqualsReturnsFalseIfObjectsAreNotEqual() {
        ConversationHistoryId other = new ConversationHistoryId();
        boolean result = conversationHistoryId.equals(other);
        assertFalse(result);

    }

    @Test
    void testEqualsReturnsFalseIfObjectIsNull(){
        ConversationHistoryId other = null;
        boolean result = conversationHistoryId.equals(other);
        assertFalse(result);
    }

    @Test
    void testHashCodeReturnsHashCodeOfObject() {
        int expected = Objects.hash(conversationHistoryId.getConversationId(), conversationHistoryId.getCreatedAt());
        int actual = conversationHistoryId.hashCode();
        assertEquals(expected, actual);
    }
}

