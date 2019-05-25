package com.optiva.topup.voms.common.repositories;

import com.optiva.topup.voms.common.entities.userconfirmationtoken.UserConfirmationToken;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserConfirmationTokenRepo extends SimpleJpaRepository<UserConfirmationToken, Integer> {

  public UserConfirmationTokenRepo(EntityManager entityManager) {
    super(UserConfirmationToken.class, entityManager);
  }

  public Optional<UserConfirmationToken> findByEmail(String email) {
    UserConfirmationToken userConfirmationToken = new UserConfirmationToken();
    userConfirmationToken.setEmail(email);
    return findOne(Example.of(userConfirmationToken));
  }

  public Optional<UserConfirmationToken> findByToken(String token) {
    UserConfirmationToken userConfirmationToken = new UserConfirmationToken();
    userConfirmationToken.setToken(token);
    return findOne(Example.of(userConfirmationToken));
  }

}
