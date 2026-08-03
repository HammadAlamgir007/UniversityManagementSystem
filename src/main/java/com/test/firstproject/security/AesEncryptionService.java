package com.test.firstproject.security;

import io.jsonwebtoken.io.Decoders;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class AesEncryptionService {

    @Value("${aes.secret}")
    private String secret;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {

        byte[] keyBytes =
                Decoders.BASE64.decode(secret);

        secretKey =
                new SecretKeySpec(
                        keyBytes,
                        "AES"
                );
    }

    public String encrypt(String text) {

        try {

            Cipher cipher =
                    Cipher.getInstance("AES/GCM/NoPadding");

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    secretKey
            );

            byte[] encrypted =
                    cipher.doFinal(
                            text.getBytes(
                                    StandardCharsets.UTF_8
                            )
                    );

            return Base64
                    .getEncoder()
                    .encodeToString(encrypted);

        }

        catch (Exception ex) {

            throw new RuntimeException(
                    "AES Encryption Failed",
                    ex
            );

        }

    }

    public String decrypt(String encryptedText) {

        try {

            Cipher cipher =
                    Cipher.getInstance("AES/GCM/NoPadding");

            cipher.init(
                    Cipher.DECRYPT_MODE,
                    secretKey
            );

            byte[] decoded =
                    Base64.getDecoder()
                            .decode(encryptedText);

            byte[] decrypted =
                    cipher.doFinal(decoded);

            return new String(
                    decrypted,
                    StandardCharsets.UTF_8
            );

        }

        catch (Exception ex) {

            throw new RuntimeException(
                    "AES Decryption Failed",
                    ex
            );

        }

    }

}