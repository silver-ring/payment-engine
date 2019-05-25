package com.optiva.topup.voms.db.init.repositories.vouchers;

import com.optiva.topup.voms.common.entities.vouchers.DeletedVoucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedVoucherRepo extends JpaRepository<DeletedVoucher, Integer> {

}
