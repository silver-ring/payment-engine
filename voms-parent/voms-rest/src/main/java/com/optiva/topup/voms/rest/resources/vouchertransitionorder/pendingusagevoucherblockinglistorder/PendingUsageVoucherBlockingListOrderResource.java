package com.optiva.topup.voms.rest.resources.vouchertransitionorder.pendingusagevoucherblockinglistorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.PENDING_USAGE_VOUCHER_BLOCKING_LIST_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.PendingUsageVoucherBlockingListOrder;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.QPendingUsageVoucherBlockingListOrder;
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
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.PENDING_USAGE_VOUCHER_TRANSITION_ORDER + "')")
public interface PendingUsageVoucherBlockingListOrderResource extends
    JpaRepository<PendingUsageVoucherBlockingListOrder, Integer>,
    QuerydslPredicateExecutor<PendingUsageVoucherBlockingListOrder>,
    QuerydslBinderCustomizer<QPendingUsageVoucherBlockingListOrder> {

  @Override
  default void customize(QuerydslBindings bindings, QPendingUsageVoucherBlockingListOrder root) {
    bindings.bind(Integer.class).first(
        (NumberPath<Integer> path, Integer value) -> path.stringValue().containsIgnoreCase(value.toString()));
    bindings.bind(String.class).first(
        (SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
  }

  @RestResource
  @BatchOrderSchedule(PENDING_USAGE_VOUCHER_BLOCKING_LIST_ORDER_TASK_TOPIC)
  @Override
  <S extends PendingUsageVoucherBlockingListOrder> S save(S entity);

  @RestResource
  @Override
  Optional<PendingUsageVoucherBlockingListOrder> findById(Integer integer);

  @RestResource
  @Override
  Page<PendingUsageVoucherBlockingListOrder> findAll(Pageable pageable);

}
