package com.optiva.topup.voms.batch.jobitemssupport;

import com.optiva.topup.voms.batch.utils.JobBuilderUtils;
import com.optiva.topup.voms.common.entities.vouchers.Voucher;
import com.optiva.topup.voms.common.utils.FileUtil;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

@Log4j2
public abstract class ListOrderItemStreamReader<T extends Voucher> implements ItemStreamReader<T> {

  @Autowired
  protected EntityManager entityManager;
  @Autowired
  protected JobBuilderUtils jobBuilderUtils;
  @Autowired
  protected FileUtil fileUtil;
  private LineIterator it;
  private List<T> items;
  @Value("#{jobParameters[orderFileResource]}")
  private Resource orderFileResource;

  @Override
  public void open(ExecutionContext executionContext) {
    try {
      it = FileUtils.lineIterator(orderFileResource.getFile(), fileUtil.getFileEncode());
    } catch (IOException ex) {
      throw new ListOrderFileOpenException(ex);
    }
  }

  @Override
  public void update(ExecutionContext executionContext) {
    int chunk = jobBuilderUtils.getChunkSize();
    List<Object> serialNumbers = new ArrayList<>();
    while (it.hasNext()) {
      serialNumbers.add(it.next());
      if (--chunk == 0) {
        break;
      }
    }
    if (serialNumbers.isEmpty()) {
      items = Collections.emptyList();
    } else {
      items = findItemsList(serialNumbers);
    }
  }

  @Override
  public void close() {
    try {
      if (!it.hasNext()) {
        it.close();
      }
    } catch (IOException ex) {
      throw new ListOrderFileCloseException(ex);
    }
  }

  @Override
  public T read() {
    return items.isEmpty() ? null : items.remove(0);
  }

  protected List<T> findItemsList(List<Object> serialNumbers) {
    CriteriaQuery<T> criteriaQuery = buildSelectionQuery(serialNumbers);
    return getQueryResult(criteriaQuery);
  }

  protected CriteriaQuery<T> buildSelectionQuery(List<Object> serialNumbers) {

    final String serialNumberPropertyName = "serialNumber";

    Class<T> entityClassType = getVoucherEntityTypeName();
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> criteria = builder.createQuery(entityClassType);
    Root<T> root = criteria.from(entityClassType);
    criteria.where(root.get(serialNumberPropertyName).in(serialNumbers));
    return criteria;
  }

  protected List<T> getQueryResult(CriteriaQuery<T> criteria) {
    final int startIndex = 0;
    int pageSize = jobBuilderUtils.getPageSize();
    TypedQuery<T> typedQuery = entityManager.createQuery(criteria);
    typedQuery.setFirstResult(startIndex);
    typedQuery.setMaxResults(pageSize);
    return typedQuery.getResultList();
  }

  protected Class<T> getVoucherEntityTypeName() {
    return (Class<T>) ((ParameterizedType) getClass()
        .getGenericSuperclass()).getActualTypeArguments()[0];
  }

}
