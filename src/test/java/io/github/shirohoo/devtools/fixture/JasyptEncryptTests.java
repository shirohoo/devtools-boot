package io.github.shirohoo.devtools.fixture;

import static org.assertj.core.api.Assertions.assertThatCode;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

class JasyptEncryptTests {
    @Test
    void encrypt() throws Exception {
        assertThatCode(() -> {

            String password = "enter password";
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            encryptor.setAlgorithm("PBEWithMD5AndDES");
            encryptor.setPassword(password);

            String plainText = "enter target";
            String encryptedText = encryptor.encrypt(plainText);
            System.out.println(encryptedText);

        }).doesNotThrowAnyException();
    }
}
