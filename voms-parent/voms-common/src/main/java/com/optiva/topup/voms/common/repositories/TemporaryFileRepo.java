package com.optiva.topup.voms.common.repositories;

import com.optiva.topup.voms.common.entities.file.TemporaryFile;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TemporaryFileRepo extends SimpleJpaRepository<TemporaryFile, Integer> {

  public TemporaryFileRepo(EntityManager entityManager) {
    super(TemporaryFile.class, entityManager);
  }

  public TemporaryFile findByFileName(String fileName) {
    TemporaryFile temporaryFile = new TemporaryFile();
    temporaryFile.setFileName(fileName);
    return findOne(Example.of(temporaryFile)).orElseThrow(TemporaryFileNotFoundException::new);
  }

}
