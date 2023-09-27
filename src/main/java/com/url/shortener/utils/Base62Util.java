package com.url.shortener.utils;

public final class Base62Util {

    private static final String CODE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = 62;

    public static String encodeFromNumber(long number) {
        char[] codeCharacters = CODE.toCharArray();
        StringBuilder encodedKey = new StringBuilder();

        do {
            int index = (int)(number % BASE);
            encodedKey.append(codeCharacters[index]);
            number /= BASE;
        } while (number > 0);

        return encodedKey.toString();
    }
}
