package com.optiva.topup.voms.batch.scheduledbatch.batchorder.voucherproductionorders.productionfilecreationlistorder;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class ProductionFileListOrderWriteCloseException extends VomsException {

  public ProductionFileListOrderWriteCloseException(Exception ex) {
    super("Production File List Order Write Close Error", ex);
  }

}
