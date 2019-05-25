package com.optiva.topup.voms.scheduler.triggers.batchorders;

import com.optiva.topup.voms.common.messages.BatchOrderSchedulerMessage;
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
  public void deleteOrder(BatchOrderSchedulerMessage orderMessage) {

    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaDelete delete = cb.createCriteriaDelete(orderMessage.getOrderEntity());
    Root orderEntityRoot = delete.from(orderMessage.getOrderEntity());

    String idParameterName = "id";
    CriteriaDelete criteriaDelete = delete
        .where(cb.equal(orderEntityRoot.get(idParameterName), orderMessage.getOrderId()));
    entityManager.createQuery(criteriaDelete).executeUpdate();
  }

}
