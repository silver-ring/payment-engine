package com.optiva.topup.voms.batch.scheduledbatch.batchorder.voucherproductionorders.productionfilecreationlistorder;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class ProductionFileListOrderWriteOpenException extends VomsException {

  public ProductionFileListOrderWriteOpenException(Exception ex) {
    super("Production File List Order Write Open Exception", ex);
  }

}
