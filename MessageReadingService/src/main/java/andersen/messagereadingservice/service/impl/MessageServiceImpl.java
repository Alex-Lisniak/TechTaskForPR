package andersen.messagereadingservice.service.impl;

import andersen.messagereadingservice.dao.MessageDao;
import andersen.messagereadingservice.service.MessageService;
import andersen.messagereadingservice.validation.AuthKeyValidator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    @Value("${custom.key.jwt.token}")
    private String keyForToken;
    private final AuthKeyValidator authKeyValidator;
    private final MessageDao messageDao;
    @Override
    public void addMessage(String jwt) {
        Jwt jwtToken = Jwts.parserBuilder()
                .setSigningKey(Base64.getUrlDecoder().decode(keyForToken))
                .build()
                .parseClaimsJws(jwt);
        Claims claims = (Claims)jwtToken.getBody();
        String authKey = (String) claims.get("key");
        if (isJwtTokenValid(authKey)) {
            String messageText = (String)claims.get("message");
            messageDao.saveMessage(messageText);
        }
    }
    private boolean isJwtTokenValid(String key) {
        return authKeyValidator.validate(UUID.fromString(key));
    }
}
