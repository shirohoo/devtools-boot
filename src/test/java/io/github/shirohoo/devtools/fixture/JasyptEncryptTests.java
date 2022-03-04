package io.github.shirohoo.devtools.fixture;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

class JasyptEncryptTests {
    @Test
    void encrypt() throws Exception {
        String password = "";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(password);

        String plainText = "";
        String encryptedText = encryptor.encrypt(plainText);
        System.out.println(encryptedText);
    }
}
