package com.optiva.topup.voms.db.init.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.jpa.repository.JpaRepository;

public class RandomEntityUtils<E, R extends JpaRepository<E, ?>> {

  private final List<E> allEntities;
  private final long allEntitiesCount;

  public RandomEntityUtils(R repo) {
    allEntities = repo.findAll();
    allEntitiesCount = repo.count();
  }

  public Set<E> randomEntities() {

    Collections.shuffle(allEntities);

    List<E> entities = new ArrayList<>();
    long entitiesCount = RandomUtils.nextLong(1, allEntitiesCount + 1);

    for (int i = 0; i < entitiesCount; i++) {
      E entity = allEntities.get(i);
      entities.add(entity);
    }

    return new HashSet<>(entities);
  }

  public E randomEntity() {
    Collections.shuffle(allEntities);
    return allEntities.get(0);
  }

}
