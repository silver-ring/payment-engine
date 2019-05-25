package com.optiva.topup.voms.common.entities.file;

import com.optiva.topup.voms.common.listeners.AuditEntityListener;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@EntityListeners({AuditEntityListener.class})
public class TemporaryFile {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TemporaryFile.id")
  @GenericGenerator(name = "TemporaryFile.id", strategy = "native")
  private Integer id;

  @Column(nullable = false)
  private String filePath;

  @Column(nullable = false)
  private String fileName;

}
