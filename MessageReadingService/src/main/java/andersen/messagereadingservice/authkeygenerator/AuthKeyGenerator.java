package andersen.messagereadingservice.authkeygenerator;

import andersen.messagereadingservice.model.AuthKey;
import andersen.messagereadingservice.repository.AuthKeyRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@AllArgsConstructor
public class AuthKeyGenerator {
    private final AuthKeyRepository authKeyRepository;

    @Scheduled(cron = "0 0/3 * * * *")
    public void recreateKey(){
        authKeyRepository.deleteAll();
        AuthKey authKey = new AuthKey(UUID.randomUUID() , new Date().getTime() + 180_000);
        authKeyRepository.save(authKey);
    }
}
