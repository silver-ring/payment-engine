package com.optiva.topup.voms.common.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPUtil;
import org.springframework.stereotype.Component;

@Component
public class PgpKeyUtil {

  public PGPPublicKey getPublicKey(byte[] encryptionKey) {
    PGPPublicKeyRingCollection ringCollection;
    InputStream inputStream;
    try {
      inputStream = PGPUtil.getDecoderStream(new ByteArrayInputStream(encryptionKey));
      ringCollection = new PGPPublicKeyRingCollection(inputStream);
    } catch (PGPException | IOException ex) {
      throw new ReadPublicKeyException(ex);
    }

    Iterator<PGPPublicKeyRing> ringIterator = ringCollection.getKeyRings();
    while (ringIterator.hasNext()) {
      PGPPublicKeyRing pgpPublicKeyRing = ringIterator.next();
      Iterator<PGPPublicKey> keyIterator = pgpPublicKeyRing.getPublicKeys();
      while (keyIterator.hasNext()) {
        PGPPublicKey key = keyIterator.next();
        if (key.isEncryptionKey()) {
          return key;
        }
      }
    }
    throw new ReadPublicKeyException();
  }

}
