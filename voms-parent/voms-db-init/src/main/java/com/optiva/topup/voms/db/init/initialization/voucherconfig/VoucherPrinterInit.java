package com.optiva.topup.voms.db.init.initialization.voucherconfig;

import com.optiva.topup.voms.common.entities.voucherconfig.VoucherPrinter;
import com.optiva.topup.voms.common.types.Status;
import com.optiva.topup.voms.db.init.DataInitializer;
import com.optiva.topup.voms.db.init.repositories.voucherconfig.VoucherPrinterRepo;
import com.optiva.topup.voms.db.init.utils.RandomTypeUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class VoucherPrinterInit implements DataInitializer {

  @Autowired
  private VoucherPrinterRepo voucherPrinterRepo;

  public void init() {
    List<VoucherPrinter> voucherPrinters = new ArrayList<>();
    RandomTypeUtils<Status> statusRandom = new RandomTypeUtils<>(Status.values());

    for (int i = 0; i < 10; i++) {
      byte[] key;
      try {
        key = IOUtils.toByteArray(new ClassPathResource("test_pubilc_key.asc").getInputStream());
      } catch (IOException ex) {
        throw new ReadTestEncryptionKeyException(ex);
      }

      VoucherPrinter voucherPrinter = new VoucherPrinter();
      voucherPrinter.setName(RandomStringUtils.randomAlphabetic(10));
      voucherPrinter.setEncryptionKeyFileName(RandomStringUtils.randomAlphabetic(5));
      voucherPrinter.setEncryptionKeyFileData(key);
      voucherPrinter.setStatus(statusRandom.randomEnums());
      voucherPrinters.add(voucherPrinter);
    }

    voucherPrinterRepo.saveAll(voucherPrinters);
  }

}
