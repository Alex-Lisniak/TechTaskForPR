package com.example.messagesendingservice.controller;

import com.example.messagesendingservice.logging.ResponseBodyInterceptor;
import com.example.messagesendingservice.service.impl.LoggingServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(MessageSendingController.class)
//@EnableAutoConfiguration
//@Import(MessageSendingController.class)
@AutoConfigureMockMvc
@SpringBootTest
public class MessageSendingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LoggingServiceImpl loggingService;
    @MockBean
    private ResponseBodyInterceptor responseBodyInterceptor;
    private final UUID testKey = UUID.randomUUID();

    @Test
    public void sendMessagesWithCorrectTokenTest() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/send/message-with-key/" + testKey))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(StringUtils.EMPTY));
    }
}
