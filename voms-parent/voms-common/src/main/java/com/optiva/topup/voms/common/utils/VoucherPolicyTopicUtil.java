package com.optiva.topup.voms.common.utils;

import static com.optiva.topup.voms.common.support.WithVoucherPolicyTopicsSupport.ACTIVE_VOUCHER_BLOCKING_POLICY_TASK_TOPIC;
import static com.optiva.topup.voms.common.support.WithVoucherPolicyTopicsSupport.BLOCKED_VOUCHER_DELETION_POLICY_TASK_TOPIC;
import static com.optiva.topup.voms.common.support.WithVoucherPolicyTopicsSupport.CREATED_VOUCHER_DESTROYING_POLICY_TASK_TOPIC;
import static com.optiva.topup.voms.common.support.WithVoucherPolicyTopicsSupport.DELETED_VOUCHER_DESTROYING_POLICY_TASK_TOPIC;
import static com.optiva.topup.voms.common.support.WithVoucherPolicyTopicsSupport.DESTROYED_VOUCHER_POLICY_TASK_TOPIC;
import static com.optiva.topup.voms.common.support.WithVoucherPolicyTopicsSupport.PENDING_USAGE_VOUCHER_BLOCKING_POLICY_TASK_TOPIC;
import static com.optiva.topup.voms.common.support.WithVoucherPolicyTopicsSupport.PRODUCTION_VOUCHER_DESTROYING_POLICY_TASK_TOPIC;
import static com.optiva.topup.voms.common.support.WithVoucherPolicyTopicsSupport.USED_VOUCHER_DELETION_POLICY_TASK_TOPIC;
import static com.optiva.topup.voms.common.types.VoucherPolicyType.ACTIVE_VOUCHER_BLOCKING_POLICY;
import static com.optiva.topup.voms.common.types.VoucherPolicyType.BLOCKED_VOUCHER_DELETION_POLICY;
import static com.optiva.topup.voms.common.types.VoucherPolicyType.CREATED_VOUCHER_DESTROYING_POLICY;
import static com.optiva.topup.voms.common.types.VoucherPolicyType.DELETED_VOUCHER_DESTROYING_POLICY;
import static com.optiva.topup.voms.common.types.VoucherPolicyType.DESTROYED_VOUCHER_POLICY;
import static com.optiva.topup.voms.common.types.VoucherPolicyType.PENDING_USAGE_VOUCHER_BLOCKING_POLICY;
import static com.optiva.topup.voms.common.types.VoucherPolicyType.PRODUCTION_VOUCHER_DESTROYING_POLICY;
import static com.optiva.topup.voms.common.types.VoucherPolicyType.USED_VOUCHER_DELETION_POLICY;

import com.optiva.topup.voms.common.types.VoucherPolicyType;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class VoucherPolicyTopicUtil {

  private final Map<VoucherPolicyType, String> voucherPolicies;

  public VoucherPolicyTopicUtil() {
    Map<VoucherPolicyType, String> voucherPolicies = new HashMap<>();

    voucherPolicies.put(DESTROYED_VOUCHER_POLICY, DESTROYED_VOUCHER_POLICY_TASK_TOPIC);
    voucherPolicies.put(DELETED_VOUCHER_DESTROYING_POLICY, DELETED_VOUCHER_DESTROYING_POLICY_TASK_TOPIC);
    voucherPolicies.put(CREATED_VOUCHER_DESTROYING_POLICY, CREATED_VOUCHER_DESTROYING_POLICY_TASK_TOPIC);
    voucherPolicies.put(USED_VOUCHER_DELETION_POLICY, USED_VOUCHER_DELETION_POLICY_TASK_TOPIC);
    voucherPolicies.put(BLOCKED_VOUCHER_DELETION_POLICY, BLOCKED_VOUCHER_DELETION_POLICY_TASK_TOPIC);
    voucherPolicies.put(ACTIVE_VOUCHER_BLOCKING_POLICY, ACTIVE_VOUCHER_BLOCKING_POLICY_TASK_TOPIC);
    voucherPolicies
        .put(PRODUCTION_VOUCHER_DESTROYING_POLICY, PRODUCTION_VOUCHER_DESTROYING_POLICY_TASK_TOPIC);
    voucherPolicies
        .put(PENDING_USAGE_VOUCHER_BLOCKING_POLICY, PENDING_USAGE_VOUCHER_BLOCKING_POLICY_TASK_TOPIC);

    this.voucherPolicies = Collections.unmodifiableMap(voucherPolicies);
  }

  public String getVoucherPolicyTopic(VoucherPolicyType voucherPolicyType) {
    return voucherPolicies.get(voucherPolicyType);
  }

}
