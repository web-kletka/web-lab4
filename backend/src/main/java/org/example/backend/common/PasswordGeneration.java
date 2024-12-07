package org.example.backend.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

public class PasswordGeneration {
    public static String generatePassword(String password, String salt) {
        return generateHASH(password + salt, "SHA-1");
    }

    public static boolean checkPassword(String realPassword, String password, String salt) {
        return realPassword.equals(generatePassword(password, salt));
    }

    private static String generateHASH(String str, String type) {
        String sha1 = null;
        try {
            MessageDigest md = MessageDigest.getInstance(type);
            sha1 = new String(md.digest(str.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);

        }
        return sha1;
    }

    public static String generateSalt() {
        byte[] salt = new byte[5];
        Random random = new Random();
        random.nextBytes(salt);

        return Base64.getEncoder().encodeToString(salt);
    }

}
