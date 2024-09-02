package com.example.messagesendingservice.message.util;

import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Random;

@Component
public final class MessageGenerator {

    private static final String [][] arrayOfVariants =  {
            {"Welcome", "Glad to see you", "Appreciate you come", "Its nice that you are here"},
            {" in my wedding", " on my defend", " at my assessment", " at this nice day"},
            {" as you can see", " perhaps somebody told ya", " it is well-known that"},
            {" by breaking the rules you can get punishment", " assessment is a complicated process", " weddings are forbidden"}};

    public static String generateRandomMessage() {
        StringBuilder messageBuilder = new StringBuilder("Message is : ");
        Random random = new Random();
        Arrays.stream(arrayOfVariants).forEach(variants -> {
            int currentSizeOfVariants = variants.length;
            messageBuilder.append(variants[random.nextInt(currentSizeOfVariants)]);
        });
        return messageBuilder.toString();
    }
}
