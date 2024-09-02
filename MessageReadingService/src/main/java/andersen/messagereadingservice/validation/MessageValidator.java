package andersen.messagereadingservice.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageValidator {
    public boolean validateMessage(String text) {
        return !text.contains("wedding");
    }
}
