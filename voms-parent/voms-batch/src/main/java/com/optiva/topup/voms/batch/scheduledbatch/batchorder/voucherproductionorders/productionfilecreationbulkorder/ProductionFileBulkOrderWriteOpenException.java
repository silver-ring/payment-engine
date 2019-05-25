package com.optiva.topup.voms.batch.scheduledbatch.batchorder.voucherproductionorders.productionfilecreationbulkorder;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class ProductionFileBulkOrderWriteOpenException extends VomsException {

  public ProductionFileBulkOrderWriteOpenException(Exception ex) {
    super("Production File Bulk Order Write Open Error", ex);
  }

}
