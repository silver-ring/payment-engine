package com.optiva.topup.voms.common.entities.voucherconfig;

import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Entity
@EntityListeners({AuditEntityListener.class})
public class Taxation extends RechargeValueAdjustment {

}
