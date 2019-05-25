package com.optiva.topup.voms.db.init.initialization;

import com.optiva.topup.voms.common.entities.voucherconfig.VomsGenerator;
import com.optiva.topup.voms.common.repositories.VomsGeneratorRepo;
import com.optiva.topup.voms.db.init.DataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VomsGeneratorInit implements DataInitializer {

  private final VomsGeneratorRepo vomsGeneratorRepo;

  @Autowired
  public VomsGeneratorInit(VomsGeneratorRepo vomsGeneratorRepo) {
    this.vomsGeneratorRepo = vomsGeneratorRepo;
  }

  public void init() {
    VomsGenerator vomsGenerator = new VomsGenerator();
    vomsGenerator.setId(1);
    vomsGenerator.setName("voucherSerialNumber");
    vomsGenerator.setValue(100000000000000L);
    vomsGeneratorRepo.save(vomsGenerator);
  }

}
