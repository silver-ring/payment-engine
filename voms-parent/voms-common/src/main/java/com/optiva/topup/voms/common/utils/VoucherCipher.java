package com.optiva.topup.voms.common.utils;

import com.google.common.primitives.Longs;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;
import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:voucher.properties")
public class VoucherCipher {

  private static final int KEY_LENGTH = 24;
  private static final String DESEDE_ECB_NO_PADDING = "DESede/ECB/NoPadding";
  private static final String DE_SEDE = "DESede";

  private Cipher decryptCipher;
  private Cipher encryptCipher;

  @Value("${voucher.encryption.key}")
  private String encryptionKey;

  @PostConstruct
  public void init() {
    init(encryptionKey);
  }

  private void init(String encryptionKey) {
    try {
      Key secretKey = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), 0, KEY_LENGTH,
          DE_SEDE);
      decryptCipher = Cipher.getInstance(DESEDE_ECB_NO_PADDING);
      decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);
      encryptCipher = Cipher.getInstance(DESEDE_ECB_NO_PADDING);
      encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);
    } catch (GeneralSecurityException ex) {
      throw new VoucherEncryptionInitializationException(ex);
    }
  }

  /**
   * Takes a long input and encrypts it, returning the encrypted long.
   *
   * @param input the long to be encrypted
   * @return encrypted long
   * @throws IllegalStateException if the cipher wasn't initialized first
   */
  public long encrypt(long input) {
    byte[] inputBytes = Longs.toByteArray(input);
    byte[] encryptedBytes = encrypt(inputBytes);
    ByteBuffer byteBuffer = ByteBuffer.wrap(encryptedBytes);
    return byteBuffer.getLong();
  }

  public String encrypt(String input) {
    byte[] newInput = input.getBytes(StandardCharsets.UTF_8);
    return new String(encrypt(newInput), StandardCharsets.UTF_8);
  }

  private byte[] encrypt(byte[] rawData) {
    return getBytes(rawData, encryptCipher);
  }

  /**
   * Takes an encrypted long and decrypts it, returning a decrypted long.
   *
   * @param input the encrypted long
   * @return the decrypted long
   * @throws IllegalStateException if the cipher wasn't initialized first
   */
  public long decrypt(long input) {
    byte[] inputBytes = Longs.toByteArray(input);
    byte[] decryptedBytes = decrypt(inputBytes);
    ByteBuffer byteBuffer = ByteBuffer.wrap(decryptedBytes);
    return byteBuffer.getLong();
  }

  public String decrypt(String input) {
    byte[] newInput = input.getBytes(StandardCharsets.UTF_8);
    return new String(decrypt(newInput), StandardCharsets.UTF_8);
  }

  private byte[] decrypt(byte[] rawData) {
    return getBytes(rawData, decryptCipher);
  }

  private byte[] getBytes(byte[] rawData, Cipher decryptCipher) {
    byte[] decryptedData;
    try {
      decryptedData = decryptCipher.doFinal(rawData);
    } catch (BadPaddingException | IllegalBlockSizeException ex) {
      reinit();
      throw new VoucherEncryptionException(ex);
    }
    return decryptedData;
  }

  /**
   * In case the encryptCipher or decrypted throws an exception, the cipher is initialized again because it
   * might be corrupted with the same key and algorithm it was initialized the first time.
   */
  private void reinit() {
    init();
  }

}
