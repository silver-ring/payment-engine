package com.optiva.topup.voms.rest.resources.vouchertransitionorder.createdvoucherdestroylistorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.CREATED_VOUCHER_DESTROY_LIST_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.CreatedVoucherDestroyListOrder;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.QCreatedVoucherDestroyListOrder;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
import com.optiva.topup.voms.rest.interceptors.BatchOrderSchedule;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.CREATED_VOUCHER_TRANSITION_ORDER + "')")
public interface CreatedVoucherDestroyListOrderResource extends
    JpaRepository<CreatedVoucherDestroyListOrder, Integer>,
    QuerydslPredicateExecutor<CreatedVoucherDestroyListOrder>,
    QuerydslBinderCustomizer<QCreatedVoucherDestroyListOrder> {

  @Override
  default void customize(QuerydslBindings bindings, QCreatedVoucherDestroyListOrder root) {
    bindings.bind(Integer.class).first(
        (NumberPath<Integer> path, Integer value) -> path.stringValue().containsIgnoreCase(value.toString()));
    bindings.bind(String.class).first(
        (SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
  }

  @RestResource
  @BatchOrderSchedule(CREATED_VOUCHER_DESTROY_LIST_ORDER_TASK_TOPIC)
  @Override
  <S extends CreatedVoucherDestroyListOrder> S save(S entity);

  @RestResource
  @Override
  Optional<CreatedVoucherDestroyListOrder> findById(Integer integer);

  @RestResource
  @Override
  Page<CreatedVoucherDestroyListOrder> findAll(Pageable pageable);

}
