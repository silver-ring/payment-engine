package com.optiva.topup.voms.common.repositories.user;

import com.optiva.topup.voms.common.entities.usermanager.UserAccount;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserAccountRepo extends SimpleJpaRepository<UserAccount, Integer> {

  public UserAccountRepo(EntityManager entityManager) {
    super(UserAccount.class, entityManager);
  }

  public Optional<UserAccount> findByEmail(String email) {
    UserAccount userAccount = new UserAccount();
    userAccount.setEmail(email);
    return findOne(Example.of(userAccount));
  }

  public Boolean existsByEmail(String email) {
    UserAccount userAccount = new UserAccount();
    userAccount.setEmail(email);
    return exists(Example.of(userAccount));
  }

}
