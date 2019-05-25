package com.optiva.topup.voms.batch.listeners;

import com.optiva.topup.voms.common.entities.orders.BatchOrder;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BatchOrderDao {

  private final EntityManager entityManager;

  @Autowired
  public BatchOrderDao(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional
  public void deleteOrder(BatchOrder batchOrder) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaDelete batchOrderCriteriaDelete = cb.createCriteriaDelete(batchOrder.getClass());
    Root batchOrderRoot = batchOrderCriteriaDelete.from(batchOrder.getClass());

    final String idParameterName = "id";
    CriteriaDelete criteriaDelete =
        batchOrderCriteriaDelete.where(cb.equal(batchOrderRoot.get(idParameterName), batchOrder.getId()));
    entityManager.createQuery(criteriaDelete).executeUpdate();
  }

}
