package com.uet.busmanagement.config;

import java.util.HashMap;
import java.util.Map;

public class cors {
    private static final Map<String, String> corsHeaders = new HashMap<>();

    static {
        // Industry standard standard CORS setup
        corsHeaders.put("Access-Control-Allow-Origin", Env.get("ALLOWED_ORIGIN") != null ? Env.get("ALLOWED_ORIGIN") : "*");
        corsHeaders.put("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        corsHeaders.put("Access-Control-Allow-Headers", "Content-Type, Authorization");
        corsHeaders.put("Access-Control-Allow-Credentials", "true");
    }

    public static Map<String, String> getCorsHeaders() {
        return corsHeaders;
    }
}