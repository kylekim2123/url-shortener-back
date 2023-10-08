package com.url.shortener.service.encoder;

import org.springframework.stereotype.Component;

@Component
public class Base62UrlEncoder implements UrlEncoder {

    private static final String CODE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = 62;
    private static final long INITIAL_PADDING = 1_000_000L;

    @Override
    public String encodeFromNumber(long number) {
        char[] codeCharacters = CODE.toCharArray();
        StringBuilder encodedKey = new StringBuilder();
        number += INITIAL_PADDING;

        do {
            int index = (int)(number % BASE);
            encodedKey.append(codeCharacters[index]);
            number /= BASE;
        } while (number > 0);

        return encodedKey.toString();
    }
}
