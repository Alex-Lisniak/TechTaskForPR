package com.example.messagesendingservice.controller;

import com.example.messagesendingservice.message.util.MessageGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/send")
public class MessageSendingController {

    private final Logger logger = LoggerFactory.getLogger(MessageSendingController.class);

    @Value("${amount.of.messages.to.generate}")
    private Integer amountOfMessages;
    @Value("${custom.reading.messages.service.uri}")
    private String MESSAGE_READING_SERVICE_URI;

    @GetMapping(value = "/message-with-key/{key}")
    public ResponseEntity sendMessagesWithToken(@PathVariable String key) {
        try {
            sendRequests(key);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResponseEntity.badRequest().body(StringUtils.EMPTY);
    }
    private String buildJwtToken(UUID key) {
        String message = MessageGenerator.generateRandomMessage();
        return Jwts.builder()
                .claim("key", key)
                .claim("message", message)
                .signWith(
                        SignatureAlgorithm.HS256,
                        Base64.getUrlDecoder().decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E="))
                .compact();
    }
    private String prepareRequestAndGetResponse(String key) throws URISyntaxException, ExecutionException, InterruptedException {
        String jwtToken = buildJwtToken(UUID.fromString(key));
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        HttpRequest sendRequest = HttpRequest.newBuilder()
                .uri(new URI(MESSAGE_READING_SERVICE_URI))
                .header("jwtToken", jwtToken)
                .build();

        return client.sendAsync(sendRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .get();
    }
    private void sendRequests (String key) throws URISyntaxException, ExecutionException, InterruptedException {
        for (int i = 0; i < amountOfMessages; i++) {
            prepareRequestAndGetResponse(key);
            Thread.sleep(6000L);
        }
    }
}

