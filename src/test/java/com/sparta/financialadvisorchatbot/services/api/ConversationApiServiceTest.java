package com.sparta.financialadvisorchatbot.services.api;

import com.sparta.financialadvisorchatbot.entities.ConversationHistory;
import com.sparta.financialadvisorchatbot.entities.ConversationHistoryId;
import com.sparta.financialadvisorchatbot.entities.ConversationId;
import com.sparta.financialadvisorchatbot.exceptions.GenericNotFoundError;
import com.sparta.financialadvisorchatbot.repositories.ConversationHistoryRepository;
import com.sparta.financialadvisorchatbot.repositories.ConversationIdRepository;
import com.sparta.financialadvisorchatbot.service.api.ConversationApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockPageContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConversationApiServiceTest {

    @Mock
    ConversationIdRepository conversationIdRepository;

    @Mock
    ConversationHistoryRepository conversationHistoryRepository;

    @InjectMocks
    private ConversationApiService conversationApiService;

    private List<ConversationHistory> entireConversation;
    private Page<ConversationId> allConversations;

    private ConversationHistory conversationHistory1;
    private ConversationHistory conversationHistory2;
    private ConversationHistory conversationHistory3;
    private ConversationHistory conversationHistory4;
    private ConversationHistory conversationHistory5;
    private ConversationHistory conversationHistory6;

    private ConversationHistoryId conversationHistoryId1;
    private ConversationHistoryId conversationHistoryId2;
    private ConversationHistoryId conversationHistoryId3;
    private ConversationHistoryId conversationHistoryId4;
    private ConversationHistoryId conversationHistoryId5;
    private ConversationHistoryId conversationHistoryId6;

    private ConversationId conversationId1;
    private ConversationId conversationId2;


    @BeforeEach
    void setUp() {

        conversationHistory1 = new ConversationHistory();
        conversationHistory2 = new ConversationHistory();
        conversationHistory3 = new ConversationHistory();
        conversationHistory4 = new ConversationHistory();
        conversationHistory5 = new ConversationHistory();
        conversationHistory6 = new ConversationHistory();

        conversationHistoryId1 = new ConversationHistoryId();
        conversationHistoryId2 = new ConversationHistoryId();
        conversationHistoryId3 = new ConversationHistoryId();
        conversationHistoryId4 = new ConversationHistoryId();
        conversationHistoryId5 = new ConversationHistoryId();
        conversationHistoryId6 = new ConversationHistoryId();

        conversationId1 = new ConversationId();
        conversationId2 = new ConversationId();
        conversationId1.setId(1);
        conversationId2.setId(2);

        conversationHistoryId1.setConversationId(conversationId1.getId());
        conversationHistoryId1.setCreatedAt(LocalDateTime.of(2023,11,11,20,30,35));

        conversationHistoryId2.setConversationId(conversationId1.getId());
        conversationHistoryId2.setCreatedAt(LocalDateTime.of(2023,11,11,20,31,35));

        conversationHistoryId3.setConversationId(conversationId1.getId());
        conversationHistoryId3.setCreatedAt(LocalDateTime.of(2023,11,11,20,32,35));

        conversationHistoryId4.setConversationId(conversationId1.getId());
        conversationHistoryId4.setCreatedAt(LocalDateTime.of(2023,11,11,20,33,35));

        conversationHistoryId5.setConversationId(conversationId2.getId());
        conversationHistoryId5.setCreatedAt(LocalDateTime.of(2023,10,11,20,30,35));

        conversationHistoryId6.setConversationId(conversationId2.getId());
        conversationHistoryId6.setCreatedAt(LocalDateTime.of(2023,10,11,20,31,35));

        conversationHistory1.setInput("hello");
        conversationHistory1.setResponse("Welcome to the app, how can I help?");
        conversationHistory1.setConversation(conversationId1);
        conversationHistory1.setId(conversationHistoryId1);

        conversationHistory2.setInput("how do I save money");
        conversationHistory2.setResponse("By putting money in a savings account");
        conversationHistory2.setConversation(conversationId1);
        conversationHistory2.setId(conversationHistoryId2);

        conversationHistory3.setInput("what is a credit card?");
        conversationHistory3.setResponse("A credit card is ...");
        conversationHistory3.setConversation(conversationId1);
        conversationHistory3.setId(conversationHistoryId3);

        conversationHistory4.setInput("goodbye");
        conversationHistory4.setResponse("Thank you for choosing SG chatbot");
        conversationHistory4.setConversation(conversationId1);
        conversationHistory4.setId(conversationHistoryId4);

        conversationHistory5.setInput("hello");
        conversationHistory5.setResponse("Welcome to the app, how can I help?");
        conversationHistory5.setConversation(conversationId2);
        conversationHistory5.setId(conversationHistoryId5);

        conversationHistory6.setInput("goodbye");
        conversationHistory6.setResponse("Thank you for choosing SG chatbot");
        conversationHistory6.setConversation(conversationId2);
        conversationHistory6.setId(conversationHistoryId6);

        entireConversation = new ArrayList<>(List.of(conversationHistory5,conversationHistory6));
        Pageable pageable = PageRequest.of(0,10);
        allConversations = new PageImpl<>(Arrays.asList(conversationId1,conversationId2),pageable,2);

    }
    @Test
    void testGetSingleRequestResponseReturnsSingleRequestResponse() {
        when(conversationHistoryRepository.findById(conversationHistoryId1)).thenReturn(Optional.of(conversationHistory1));
        ConversationHistory testHistory =  conversationApiService.getSingleRequestResponse(conversationHistoryId1);
        assertEquals(conversationHistory1, testHistory);
        assertEquals(conversationHistoryId1, testHistory.getId());
        assertEquals(conversationId1, testHistory.getConversation());
        assertEquals("hello", testHistory.getInput());
        assertEquals("Welcome to the app, how can I help?", testHistory.getResponse());
    }
    @Test
    void testGetSingleRequestResponseThrowsNotFoundExceptionIfNotFound(){
        String expected = "Unable to find request/response";
        when(conversationHistoryRepository.findById(conversationHistoryId1)).thenReturn(Optional.empty());
        GenericNotFoundError thrown = assertThrows(GenericNotFoundError.class, () -> {
            conversationApiService.getSingleRequestResponse(conversationHistoryId1);
        });
        assertEquals(expected, thrown.getMessage());
    }
    @Test
    void testGetEntireConversationHistoryByIdReturnsEntireConversationHistory() {
        when(conversationHistoryRepository.findByConversation_Id(conversationId2.getId())).thenReturn(entireConversation);
        List<ConversationHistory> entireConversationTest = conversationApiService.getEntireConversationHistoryByConversationId(conversationId2.getId());

        assertEquals(entireConversation, entireConversationTest);
        assertEquals(conversationHistoryId5, entireConversationTest.get(0).getId());
        assertEquals(conversationHistoryId6, entireConversationTest.get(1).getId());
        assertEquals(conversationId2, entireConversationTest.get(1).getConversation());

    }
    @Test
    void testGetEntireConversationHistoryByIdThrowsNotFoundExceptionIfNotFound(){
        String expected = "No conversation found with id: 1";
        when(conversationHistoryRepository.findByConversation_Id(conversationId1.getId())).thenReturn(List.of());
        GenericNotFoundError thrown = assertThrows(GenericNotFoundError.class, () -> {
            conversationApiService.getEntireConversationHistoryByConversationId(conversationId1.getId());
        });
        assertEquals(expected, thrown.getMessage());
    }
    @Test
    void testGetAllConversationsReturnsAllConversations() {
        when(conversationIdRepository.findAll(Pageable.ofSize(10))).thenReturn(allConversations);
        Page pageTest = conversationApiService.getAllConversations(0,10);
        assertEquals(conversationId1,pageTest.getContent().getFirst());
        assertEquals(2,pageTest.getContent().size());
    }
    @Test
    void testGetAllConversationsThrowsNotFoundExceptionIfNotFound(){
        String expected = "No conversations found!";
        when(conversationIdRepository.findAll(Pageable.ofSize(10))).thenReturn(Page.empty());
        GenericNotFoundError thrown = assertThrows(GenericNotFoundError.class, () -> {
            conversationApiService.getAllConversations(0,10);
        });
        assertEquals(expected, thrown.getMessage());
    }
}
