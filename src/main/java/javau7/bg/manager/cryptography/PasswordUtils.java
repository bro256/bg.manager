package javau7.bg.manager.cryptography;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {
    public static byte[] generateSaltFromUsername(String username) {
        try {
            MessageDigest digest = MessageDigest.getInstance(("SHA-256"));
            byte[] hashBytes = digest.digest(username.getBytes(StandardCharsets.UTF_8));

            // Taking 16 bytes (128 bits) as salt
            byte[] salt = new byte[16];
            System.arraycopy(hashBytes, 0, salt, 0, 16);

            return salt;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating salt from username", e);
        }
    }
}
