package com.optiva.topup.voms.rest.resources.file;

import com.optiva.topup.voms.common.exceptions.VomsException;

public class UploadFileNotSelectedException extends VomsException {

  public UploadFileNotSelectedException() {
    super("You must select file for uploading");
  }

}
