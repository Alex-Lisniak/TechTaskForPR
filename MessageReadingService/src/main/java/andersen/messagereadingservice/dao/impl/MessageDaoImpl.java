package andersen.messagereadingservice.dao.impl;


import andersen.messagereadingservice.dao.MessageDao;
import andersen.messagereadingservice.exception.MessageInitializationEmptyTextException;
import andersen.messagereadingservice.model.Message;
import andersen.messagereadingservice.repository.MessageRepository;
import andersen.messagereadingservice.validation.MessageValidator;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class MessageDaoImpl implements MessageDao {

    private final MessageRepository messageRepository;
    private final MessageValidator messageValidator;
    private static final Logger LOG = LogManager.getLogger(MessageDaoImpl.class);


    @Override
    @Transactional
    public void saveMessage(String messageText) {
        if (StringUtils.hasText(messageText)) {
            if (messageValidator.validateMessage(messageText)) {
                Message message = new Message(UUID.randomUUID(), messageText);
                saveMsgByRep(message);
                // messageRepository.save(message);
                LOG.info("Message successfully created in H2 database");
            }
            else {
                LOG.info("Message has forbidden word, invalid");
            }
        } else throw new MessageInitializationEmptyTextException("Passed text to save message shouldn`t be empty");
    }
    public void saveMsgByRep(Message msg){
        messageRepository.save(msg);
    }
}
