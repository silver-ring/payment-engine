package com.optiva.topup.voms.common.repositories.configparameters;

import com.optiva.topup.voms.common.entities.configparameters.FileManagerConfigParameter;
import com.optiva.topup.voms.common.types.FileManagerConfigParameterType;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class FileManagerConfigParameterRepo extends SimpleJpaRepository<FileManagerConfigParameter, Integer> {

  public FileManagerConfigParameterRepo(EntityManager entityManager) {
    super(FileManagerConfigParameter.class, entityManager);
  }

  public Map<FileManagerConfigParameterType, String> findAllAsMap() {
    return findAll().stream()
        .collect(
            Collectors.toMap(FileManagerConfigParameter::getParameter, FileManagerConfigParameter::getValue));
  }

  public FileManagerConfigParameter findByParameter(
      FileManagerConfigParameterType fileManagerConfigParameterType) {
    FileManagerConfigParameter fileManagerConfigParameter = new FileManagerConfigParameter();
    fileManagerConfigParameter.setParameter(fileManagerConfigParameterType);
    return findOne(Example.of(fileManagerConfigParameter))
        .orElseThrow(FileManagerConfigParameterNotFoundException::new);
  }

}
