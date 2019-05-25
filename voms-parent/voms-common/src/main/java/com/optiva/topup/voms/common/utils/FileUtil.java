package com.optiva.topup.voms.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.bouncycastle.bcpg.SymmetricKeyAlgorithmTags;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.operator.bc.BcPGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyKeyEncryptionMethodGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class FileUtil {

  @Autowired
  private PgpKeyUtil pgpKeyUtil;

  public File createFile(String filePathName, String fileName) {
    File file = new File(filePathName + fileName);
    try {
      FileUtils.touch(file);
    } catch (IOException ex) {
      throw new FileCreationException(ex);
    }
    FileUtils.deleteQuietly(file);
    return file;
  }

  public File openFile(String filePathName, String fileName) {
    File file = new File(filePathName + fileName);
    try {
      FileUtils.touch(file);
    } catch (IOException e) {
      throw new FileOpenException(e);
    }
    return file;
  }

  public InputStream readResourceInputStream(String resourceFileName) {
    try {
      return new ClassPathResource(resourceFileName).getInputStream();
    } catch (IOException e) {
      throw new ReadResourceFileException(e);
    }
  }

  public void writeFile(File file, byte[] data) {
    try {
      FileUtils.writeByteArrayToFile(file, data);
    } catch (IOException e) {
      throw new WriteFileException(e);
    }
  }

  public File deleteFile(String filePathName, String fileName) {
    File file = new File(filePathName + fileName);
    FileUtils.deleteQuietly(file);
    return file;
  }

  public File createEncryptedFile(byte[] encryptionKey, ByteArrayOutputStream content, File outputFile) {
    try {

      ByteArrayOutputStream bos = new ByteArrayOutputStream();

      PGPCompressedDataGenerator comp = new PGPCompressedDataGenerator(PGPCompressedDataGenerator.ZLIB);
      OutputStream cos = comp.open(bos);

      PGPLiteralDataGenerator lit = new PGPLiteralDataGenerator();
      OutputStream los = lit.open(cos, PGPLiteralData.BINARY, "", content.toByteArray().length, new Date());
      los.write(content.toByteArray());
      los.close();
      cos.close();

      BcPGPDataEncryptorBuilder bcPgpDataEncryptorBuilder = new BcPGPDataEncryptorBuilder(
          SymmetricKeyAlgorithmTags.AES_128).setWithIntegrityPacket(true);

      PGPEncryptedDataGenerator enc = new PGPEncryptedDataGenerator(bcPgpDataEncryptorBuilder);
      PGPPublicKey pgpPublicKey = pgpKeyUtil.getPublicKey(encryptionKey);
      enc.addMethod(new BcPublicKeyKeyEncryptionMethodGenerator(pgpPublicKey));

      byte[] outBytes = bos.toByteArray();
      bos.close();

      ByteArrayOutputStream out = new ByteArrayOutputStream();
      OutputStream eos = enc.open(out, outBytes.length);
      eos.write(outBytes);
      eos.close();

      FileUtils.writeByteArrayToFile(outputFile, out.toByteArray());
    } catch (IOException | PGPException ex) {
      throw new EncryptedFileCreationException(ex);
    }
    return outputFile;
  }

  public String getHeaderDelimiter() {
    return ";";
  }

  public String getLineDelimiter() {
    return ",";
  }

  public String getEndOfLineDelimiter() {
    return "\n";
  }

  public String getFileEncode() {
    return "UTF-8";
  }

}
