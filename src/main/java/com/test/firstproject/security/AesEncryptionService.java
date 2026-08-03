package com.test.firstproject.security;

import io.jsonwebtoken.io.Decoders;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class AesEncryptionService {

    private static final String TRANSFORMATION = "AES/GCM/NoPadding";


    private static final int IV_LENGTH_BYTES = 12;

    private static final int TAG_LENGTH_BITS = 128;

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


            byte[] iv = new byte[IV_LENGTH_BYTES];
            new SecureRandom().nextBytes(iv);

            Cipher cipher =
                    Cipher.getInstance(TRANSFORMATION);

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    secretKey,
                    new GCMParameterSpec(TAG_LENGTH_BITS, iv)
            );

            byte[] encrypted =
                    cipher.doFinal(
                            text.getBytes(
                                    StandardCharsets.UTF_8
                            )
                    );


            ByteBuffer buffer =
                    ByteBuffer.allocate(IV_LENGTH_BYTES + encrypted.length);
            buffer.put(iv);
            buffer.put(encrypted);

            return Base64
                    .getEncoder()
                    .encodeToString(buffer.array());

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

            byte[] decoded =
                    Base64.getDecoder()
                            .decode(encryptedText);

            ByteBuffer buffer = ByteBuffer.wrap(decoded);

            byte[] iv = new byte[IV_LENGTH_BYTES];
            buffer.get(iv);

            byte[] cipherBytes = new byte[buffer.remaining()];
            buffer.get(cipherBytes);

            Cipher cipher =
                    Cipher.getInstance(TRANSFORMATION);

            cipher.init(
                    Cipher.DECRYPT_MODE,
                    secretKey,
                    new GCMParameterSpec(TAG_LENGTH_BITS, iv)
            );

            byte[] decrypted =
                    cipher.doFinal(cipherBytes);

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