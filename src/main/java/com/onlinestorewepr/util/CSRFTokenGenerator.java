package com.onlinestorewepr.util;

import java.util.UUID;

public class CSRFTokenGenerator {
    private static final String TOKEN_PREFIX = "csrf_token_";
    public static String generateToken() {
        return TOKEN_PREFIX + UUID.randomUUID();
    }
}

