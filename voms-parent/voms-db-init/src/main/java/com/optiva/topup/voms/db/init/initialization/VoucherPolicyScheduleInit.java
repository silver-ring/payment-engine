package com.optiva.topup.voms.db.init.initialization;

import com.optiva.topup.voms.common.entities.voucherpolicies.VoucherPolicySchedule;
import com.optiva.topup.voms.common.types.VoucherPolicyType;
import com.optiva.topup.voms.db.init.DataInitializer;
import com.optiva.topup.voms.db.init.repositories.VoucherPolicyScheduleRepo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoucherPolicyScheduleInit implements DataInitializer {

  private static final String DEFAULT_CRON_EXPRESSION = "0 0 0 1 * ? *";

  private final VoucherPolicyScheduleRepo voucherPolicyScheduleRepo;

  @Autowired
  public VoucherPolicyScheduleInit(VoucherPolicyScheduleRepo voucherPolicyScheduleRepo) {
    this.voucherPolicyScheduleRepo = voucherPolicyScheduleRepo;
  }

  public void init() {

    List<VoucherPolicySchedule> voucherPolicySchedules = new ArrayList<>();
    voucherPolicySchedules.add(getActiveVoucherBlockingPolicySchedule());
    voucherPolicySchedules.add(getBlockedVoucherDeletionPolicySchedule());
    voucherPolicySchedules.add(getCreatedVoucherDestroyingPolicySchedule());
    voucherPolicySchedules.add(getDeletedVoucherDestroyingPolicySchedule());
    voucherPolicySchedules.add(getDestroyedVoucherPolicySchedule());
    voucherPolicySchedules.add(getPendingUsageVoucherBlockingPolicySchedule());
    voucherPolicySchedules.add(getProductionVoucherDestroyingPolicySchedule());
    voucherPolicySchedules.add(getUsedVoucherDeletionPolicySchedule());

    voucherPolicyScheduleRepo.saveAll(voucherPolicySchedules);

  }

  private VoucherPolicySchedule getUsedVoucherDeletionPolicySchedule() {
    VoucherPolicySchedule voucherPolicySchedule = new VoucherPolicySchedule();
    voucherPolicySchedule.setVoucherPolicyType(VoucherPolicyType.USED_VOUCHER_DELETION_POLICY);
    voucherPolicySchedule.setDefaultCronExpression(DEFAULT_CRON_EXPRESSION);
    voucherPolicySchedule.setCronExpression(DEFAULT_CRON_EXPRESSION);
    voucherPolicySchedule.setDescription("Delete Used Vouchers");
    return voucherPolicySchedule;
  }

  private VoucherPolicySchedule getProductionVoucherDestroyingPolicySchedule() {
    VoucherPolicySchedule voucherPolicySchedule = new VoucherPolicySchedule();
    voucherPolicySchedule
        .setVoucherPolicyType(VoucherPolicyType.PRODUCTION_VOUCHER_DESTROYING_POLICY);
    voucherPolicySchedule.setDefaultCronExpression(DEFAULT_CRON_EXPRESSION);
    voucherPolicySchedule.setCronExpression(DEFAULT_CRON_EXPRESSION);
    voucherPolicySchedule
        .setDescription("Destroy Expired Production Vouchers After Production Vouchers Keeping Period");
    return voucherPolicySchedule;
  }

  private VoucherPolicySchedule getPendingUsageVoucherBlockingPolicySchedule() {
    VoucherPolicySchedule voucherPolicySchedule = new VoucherPolicySchedule();
    voucherPolicySchedule
        .setVoucherPolicyType(VoucherPolicyType.PENDING_USAGE_VOUCHER_BLOCKING_POLICY);
    voucherPolicySchedule.setDefaultCronExpression(DEFAULT_CRON_EXPRESSION);
    voucherPolicySchedule.setCronExpression(DEFAULT_CRON_EXPRESSION);
    voucherPolicySchedule.setDescription("Block Expired Pending Usage Vouchers");
    return voucherPolicySchedule;
  }

  private VoucherPolicySchedule getDestroyedVoucherPolicySchedule() {
    VoucherPolicySchedule voucherPolicySchedule = new VoucherPolicySchedule();
    voucherPolicySchedule.setVoucherPolicyType(VoucherPolicyType.DESTROYED_VOUCHER_POLICY);
    voucherPolicySchedule.setDefaultCronExpression(DEFAULT_CRON_EXPRESSION);
    voucherPolicySchedule.setCronExpression(DEFAULT_CRON_EXPRESSION);
    voucherPolicySchedule.setDescription("Fully Remove All Destroyed Vouchers Data from the VoMS");
    return voucherPolicySchedule;
  }

  private VoucherPolicySchedule getDeletedVoucherDestroyingPolicySchedule() {
    VoucherPolicySchedule voucherPolicySchedule = new VoucherPolicySchedule();
    voucherPolicySchedule
        .setVoucherPolicyType(VoucherPolicyType.DELETED_VOUCHER_DESTROYING_POLICY);
    voucherPolicySchedule.setDefaultCronExpression(DEFAULT_CRON_EXPRESSION);
    voucherPolicySchedule.setCronExpression(DEFAULT_CRON_EXPRESSION);
    voucherPolicySchedule.setDescription("Archive then Destroy Deleted Vouchers");
    return voucherPolicySchedule;
  }

  private VoucherPolicySchedule getCreatedVoucherDestroyingPolicySchedule() {
    VoucherPolicySchedule voucherPolicySchedule = new VoucherPolicySchedule();
    voucherPolicySchedule
        .setVoucherPolicyType(VoucherPolicyType.CREATED_VOUCHER_DESTROYING_POLICY);
    voucherPolicySchedule.setDefaultCronExpression(DEFAULT_CRON_EXPRESSION);
    voucherPolicySchedule.setCronExpression(DEFAULT_CRON_EXPRESSION);
    voucherPolicySchedule.setDescription("Destroy Expired Created Vouchers");
    return voucherPolicySchedule;
  }

  private VoucherPolicySchedule getBlockedVoucherDeletionPolicySchedule() {
    VoucherPolicySchedule voucherPolicySchedule = new VoucherPolicySchedule();
    voucherPolicySchedule
        .setVoucherPolicyType(VoucherPolicyType.BLOCKED_VOUCHER_DELETION_POLICY);
    voucherPolicySchedule.setDefaultCronExpression(DEFAULT_CRON_EXPRESSION);
    voucherPolicySchedule.setCronExpression(DEFAULT_CRON_EXPRESSION);
    voucherPolicySchedule
        .setDescription("Deleted Expired Blocked Vouchers After Block Voucher Keeping Period");
    return voucherPolicySchedule;
  }

  private VoucherPolicySchedule getActiveVoucherBlockingPolicySchedule() {
    VoucherPolicySchedule voucherPolicySchedule = new VoucherPolicySchedule();
    voucherPolicySchedule
        .setVoucherPolicyType(VoucherPolicyType.ACTIVE_VOUCHER_BLOCKING_POLICY);
    voucherPolicySchedule.setDefaultCronExpression(DEFAULT_CRON_EXPRESSION);
    voucherPolicySchedule.setCronExpression(DEFAULT_CRON_EXPRESSION);
    voucherPolicySchedule.setDescription("Block All Expired Active Vouchers");
    return voucherPolicySchedule;
  }

}
