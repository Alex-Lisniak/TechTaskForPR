package com.example.messagesendingservice.message;


import com.example.messagesendingservice.message.util.MessageGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessageGeneratorUnitTest {

    @Test
    public void generateMessageTest() {
        String testedMessage = MessageGenerator.generateRandomMessage();
        assertTrue(!testedMessage.isEmpty());
        assertTrue(testedMessage.startsWith("Message is :"));
    }
}
