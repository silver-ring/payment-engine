package com.optiva.topup.voms.common.entities.voucherconfig;

import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import com.optiva.topup.voms.common.types.Status;
import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@EntityListeners(AuditEntityListener.class)
public class VoucherPrinter {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VoucherPrinter.id")
  @GenericGenerator(name = "VoucherPrinter.id", strategy = "native")
  private Integer id;

  @Column(unique = true, nullable = false)
  private String name;

  @Setter(value = AccessLevel.NONE)
  @Getter(value = AccessLevel.NONE)
  @Column(nullable = false, length = 4096)
  private byte[] encryptionKeyFileData;

  @Column(nullable = false)
  private String encryptionKeyFileName;

  @Column
  private String remoteFileTransferUrl;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status;

  public byte[] getEncryptionKeyFileData() {
    return Arrays.copyOf(encryptionKeyFileData, encryptionKeyFileData.length);
  }

  public void setEncryptionKeyFileData(byte[] encryptionKeyFileData) {
    this.encryptionKeyFileData = Arrays.copyOf(encryptionKeyFileData, encryptionKeyFileData.length);
  }

}
