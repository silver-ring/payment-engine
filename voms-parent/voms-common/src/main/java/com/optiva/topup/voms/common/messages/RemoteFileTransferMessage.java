package com.optiva.topup.voms.common.messages;

import java.io.File;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoteFileTransferMessage {

  private File file;
  private String remoteTransferUrl;

}
