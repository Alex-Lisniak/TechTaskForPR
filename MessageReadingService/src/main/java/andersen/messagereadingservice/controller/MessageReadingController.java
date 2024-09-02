package andersen.messagereadingservice.controller;

import andersen.messagereadingservice.service.MessageService;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/read")
public class MessageReadingController {

    private static final Logger LOG = LogManager.getLogger(MessageReadingController.class);
    private final MessageService messageService;


    @GetMapping(value = "/delivered-message")
    public ResponseEntity readDeliveredMessage (@RequestHeader String jwtToken) {
        LOG.info("Adding message with jwt");
        messageService.addMessage(jwtToken);
        return new ResponseEntity(HttpStatus.OK);
    }

}
