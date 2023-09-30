package com.url.shortener.utils;

import java.net.URL;
import java.net.URLConnection;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UrlConnectionUtil {

    public static boolean isValidUrl(String address) {
        try {
            URL url = new URL(address);
            URLConnection connection = url.openConnection();
            connection.connect();

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
