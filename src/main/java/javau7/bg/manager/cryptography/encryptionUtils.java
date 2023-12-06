package javau7.bg.manager.cryptography;


import java.security.SecureRandom;


public class encryptionUtils {


    private static byte[] generateRandomBytes(int size) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[size];
        secureRandom.nextBytes(bytes);
        return bytes;
    }
}
