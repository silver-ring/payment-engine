package com.optiva.topup.voms.common.entities.vouchers;

import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import com.optiva.topup.voms.common.listeners.DeletedVoucherListener;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Entity
@EntityListeners({DeletedVoucherListener.class, AuditEntityListener.class})
public class DeletedVoucher extends Voucher {

}
