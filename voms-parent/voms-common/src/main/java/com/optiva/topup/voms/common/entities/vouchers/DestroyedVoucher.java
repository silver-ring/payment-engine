package com.optiva.topup.voms.common.entities.vouchers;

import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import com.optiva.topup.voms.common.listeners.DestroyedVoucherListener;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Entity
@EntityListeners({DestroyedVoucherListener.class, AuditEntityListener.class})
public class DestroyedVoucher extends Voucher {

}
