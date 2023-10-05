package com.url.shortener.utils.encoding;

import org.springframework.stereotype.Component;

@Component
public class Base62Util implements EncodingUtil {

    private static final String CODE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = 62;
    private static final Long INITIAL_PADDING = 1_000_000L;

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
