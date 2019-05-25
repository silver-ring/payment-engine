package com.optiva.topup.voms.batch.lanuchers;

import com.optiva.topup.voms.batch.listeners.VoucherPolicyJobListener;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.stereotype.Component;

@Component
@JobScope
public class DefaultVoucherPolicyJobListener extends VoucherPolicyJobListener {

}
