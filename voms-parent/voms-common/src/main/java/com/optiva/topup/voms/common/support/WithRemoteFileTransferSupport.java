package com.optiva.topup.voms.common.support;

import com.optiva.topup.voms.common.messages.RemoteFileTransferMessage;
import java.io.File;

public interface WithRemoteFileTransferSupport extends WithKafkaSupport {

  String REMOTE_FILE_TRANSFER_TOPIC = "RemoteFileTransferTopic";

  default void sendRemoteFileTransferMessage(String remoteUrl, File fileToTransfer) {
    if (remoteUrl == null || remoteUrl.isEmpty()) {
      throw new MissingRemoteServerInformation();
    }
    RemoteFileTransferMessage remoteFileTransferMessage = new RemoteFileTransferMessage();
    remoteFileTransferMessage.setFile(fileToTransfer);
    remoteFileTransferMessage.setRemoteTransferUrl(remoteUrl);
    sendTopic(REMOTE_FILE_TRANSFER_TOPIC, remoteFileTransferMessage);
  }

}
