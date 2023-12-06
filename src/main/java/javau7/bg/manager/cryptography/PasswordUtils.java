package javau7.bg.manager.cryptography;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
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

    public static byte[] deriveKey(char[] password, byte[] salt){
        try{
            int iterations = 10000;
            int keyLength = 256;
            PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterations, keyLength);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return keyFactory.generateSecret(keySpec).getEncoded();
        } catch (Exception e) {
            throw new RuntimeException("Error deriving key", e);
        }

    }
}
