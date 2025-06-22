package com.example.cnafs.util;

import com.example.cnafs.repository.model.AdminEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Value("${jwt.secret:default-test-secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms:3600000}")
    private long jwtExpirationMs;

    public String generateToken(AdminEntity adminEntity) {
        try {
            long now = System.currentTimeMillis();
            long exp = now + jwtExpirationMs;

            Map<String, Object> header = new HashMap<>();
            header.put("alg", "HS256");
            header.put("typ", "JWT");

            Map<String, Object> payload = new HashMap<>();
            payload.put("sub", adminEntity.getId().toString());
            payload.put("username", adminEntity.getUsername());
            payload.put("iat", now / 1000);
            payload.put("exp", exp / 1000);

            String encodedHeader = base64UrlEncode(OBJECT_MAPPER.writeValueAsBytes(header));
            String encodedPayload = base64UrlEncode(OBJECT_MAPPER.writeValueAsBytes(payload));

            String signature = hmacSha256(encodedHeader + "." + encodedPayload, jwtSecret);

            return encodedHeader + "." + encodedPayload + "." + signature;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate JWT", e);
        }
    }

    public Map<String, Object> parseToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid JWT format");
            }

            String encodedHeader = parts[0];
            String encodedPayload = parts[1];
            String receivedSignature = parts[2];

            String expectedSignature = hmacSha256(encodedHeader + "." + encodedPayload, jwtSecret);
            if (!expectedSignature.equals(receivedSignature)) {
                throw new SecurityException("Invalid JWT signature");
            }

            byte[] decodedPayload = Base64.getUrlDecoder().decode(encodedPayload);
            return OBJECT_MAPPER.readValue(decodedPayload, Map.class);

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JWT", e);
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            Map<String, Object> payload = parseToken(token);

            Object expObj = payload.get("exp");
            if (expObj == null) {
                throw new IllegalArgumentException("Token missing expiration claim");
            }

            long expiration;
            if (expObj instanceof Integer) {
                expiration = ((Integer) expObj).longValue();
            } else if (expObj instanceof Long) {
                expiration = (Long) expObj;
            } else {
                throw new IllegalArgumentException("Invalid expiration claim format");
            }

            long expirationMs = expiration * 1000;
            long currentTimeMs = System.currentTimeMillis();

            return currentTimeMs > expirationMs;

        } catch (Exception e) {
            return false;
        }
    }

    private String base64UrlEncode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hmacSha256(String data, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        mac.init(keySpec);
        return base64UrlEncode(mac.doFinal(data.getBytes()));
    }

}
