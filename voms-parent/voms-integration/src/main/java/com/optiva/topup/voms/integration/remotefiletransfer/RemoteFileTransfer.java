package com.optiva.topup.voms.integration.remotefiletransfer;

import static com.optiva.topup.voms.common.support.WithRemoteFileTransferSupport.REMOTE_FILE_TRANSFER_TOPIC;

import com.optiva.topup.voms.common.document.RemoteFileTransferHistory;
import com.optiva.topup.voms.common.messages.RemoteFileTransferMessage;
import com.optiva.topup.voms.common.support.WithDocumentTopicsSupport;
import com.optiva.topup.voms.common.types.RemoteFileTransferStatus;
import java.io.File;
import java.time.LocalDateTime;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileUtil;
import org.apache.commons.vfs2.VFS;
import org.elasticsearch.common.UUIDs;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RemoteFileTransfer implements WithDocumentTopicsSupport {

  @KafkaListener(topics = REMOTE_FILE_TRANSFER_TOPIC)
  public void fileTransfer(RemoteFileTransferMessage remoteFileTransferMessage) {

    RemoteFileTransferHistory remoteFileTransferHistory = new RemoteFileTransferHistory();
    try {
      remoteFileTransferHistory.setRemoteTransferUrl(remoteFileTransferMessage.getRemoteTransferUrl());
      remoteFileTransferHistory.setLocalFileUri(remoteFileTransferMessage.getFile().toURI().getPath());

      String remoteUrl = remoteFileTransferMessage.getRemoteTransferUrl();
      File localFile = remoteFileTransferMessage.getFile();

      FileSystemManager fileSystemManager = VFS.getManager();
      FileObject remoteFileObject = fileSystemManager.resolveFile(remoteUrl + localFile.getName());
      FileObject localFileObject = fileSystemManager.toFileObject(localFile);

      if (!remoteFileObject.exists()) {
        remoteFileObject.createFile();
      }
      FileUtil.copyContent(localFileObject, remoteFileObject);
      remoteFileTransferHistory.setRemoteFileTransferStatus(RemoteFileTransferStatus.SUCCESS);
    } catch (Exception ex) {
      remoteFileTransferHistory.setErrorMessage(ex.getMessage());
      remoteFileTransferHistory.setRemoteFileTransferStatus(RemoteFileTransferStatus.FAIL);
    } finally {
      remoteFileTransferHistory.setId(UUIDs.randomBase64UUID());
      remoteFileTransferHistory.setEventTimestamp(LocalDateTime.now());
      sendDocumentMessage(remoteFileTransferHistory);
    }

  }

}
