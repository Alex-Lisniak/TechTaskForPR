package andersen.messagereadingservice.validation;

import andersen.messagereadingservice.exception.KeyIsNotValidException;
import andersen.messagereadingservice.model.AuthKey;
import andersen.messagereadingservice.repository.AuthKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthKeyValidator {

    private final AuthKeyRepository authKeyRepository;

    public boolean validate(UUID keyId){
        AuthKey authKey = authKeyRepository.findById(keyId).orElseThrow( () -> new KeyIsNotValidException("No key with that id found!! "));
        return authKey.getExpTime() > new Date().getTime();
    }
}
