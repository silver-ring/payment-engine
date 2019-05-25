package com.optiva.topup.voms.db.init.initialization.vouchers;

import static com.optiva.topup.voms.common.types.BatchOrderConfigParameterType.VOUCHER_ID_LENGTH;

import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import com.optiva.topup.voms.common.entities.voucherconfig.VomsGenerator;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import com.optiva.topup.voms.common.repositories.VomsGeneratorRepo;
import com.optiva.topup.voms.common.repositories.configparameters.BatchOrderConfigParameterRepo;
import com.optiva.topup.voms.db.init.DataInitializer;
import com.optiva.topup.voms.db.init.repositories.voucherconfig.RechargeValueRepo;
import com.optiva.topup.voms.db.init.repositories.voucherconfig.VoucherTypeRepo;
import com.optiva.topup.voms.db.init.repositories.vouchers.CreatedVoucherRepo;
import com.optiva.topup.voms.db.init.utils.RandomDateTimeUtils;
import com.optiva.topup.voms.db.init.utils.RandomEntityUtils;
import java.security.SecureRandom;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreatedVoucherInit implements DataInitializer {

  @Autowired
  private RechargeValueRepo rechargeValueRepo;
  @Autowired
  private VoucherTypeRepo voucherTypeRepo;
  @Autowired
  private CreatedVoucherRepo createdVoucherRepo;
  @Autowired
  private VomsGeneratorRepo vomsGeneratorRepo;
  @Autowired
  private BatchOrderConfigParameterRepo batchOrderConfigParameterRepo;

  public void init() {

    Integer numberOfVouchers = 1000;

    RandomEntityUtils<RechargeValue, RechargeValueRepo> rechargeValueRandom = new RandomEntityUtils<>(
        rechargeValueRepo);
    RandomEntityUtils<VoucherType, VoucherTypeRepo> voucherTypeRandom = new RandomEntityUtils<>(
        voucherTypeRepo);

    for (int i = 0; i < numberOfVouchers; i++) {
      long voucherId = generateVoucherId();

      VomsGenerator voucherSerialNumber = getVoucherSerialNumber();
      Long currentValue = voucherSerialNumber.getValue();
      Long nextValue = 1 + voucherSerialNumber.getValue();
      voucherSerialNumber.setValue(nextValue);

      CreatedVoucher createdVoucher = new CreatedVoucher();
      createdVoucher.setVoucherId(voucherId);
      createdVoucher.setSerialNumber(currentValue);
      createdVoucher.setRechargeValue(rechargeValueRandom.randomEntity());
      createdVoucher.setVoucherType(voucherTypeRandom.randomEntity());
      createdVoucher.setExpirationDate(RandomDateTimeUtils.futureOrPastDate());
      createdVoucher.setRechargePeriod(RandomUtils.nextInt(30, 90));
      createdVoucherRepo.save(createdVoucher);
      vomsGeneratorRepo.save(voucherSerialNumber);
    }
  }

  private long generateVoucherId() {

    Long voucherIdLength = Long.parseLong(
        batchOrderConfigParameterRepo.findByParameter(VOUCHER_ID_LENGTH).getValue());

    final SecureRandom rng = new SecureRandom();
    long max = (long) (Math.pow(10, voucherIdLength) - 1);
    long offset = (long) Math.pow(10, voucherIdLength - 1);
    long available = max - offset;
    long voucherId;
    do {
      double rndDouble = rng.nextDouble();
      voucherId = (long) (rndDouble * available) + offset;
    } while (voucherId < 0 && createdVoucherRepo
        .existsByVoucherId(voucherId)); // make sure the result is positive
    return voucherId;
  }

  private VomsGenerator getVoucherSerialNumber() {
    return vomsGeneratorRepo.voucherSerialNumber();
  }

}
