package com.optiva.topup.voms.batch.scheduledbatch.batchorder.voucherproductionorders.productionfilecreationbulkorder;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class ProductionFileBulkOrderWriteCloseException extends VomsException {

  public ProductionFileBulkOrderWriteCloseException(Exception ex) {
    super("Production File Bulk Order Write Close Error", ex);
  }

}
