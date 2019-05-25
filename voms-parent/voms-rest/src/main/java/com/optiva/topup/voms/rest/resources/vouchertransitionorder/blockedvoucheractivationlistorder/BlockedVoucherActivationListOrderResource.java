package com.optiva.topup.voms.rest.resources.vouchertransitionorder.blockedvoucheractivationlistorder;

import static com.optiva.topup.voms.common.support.WithBatchOrderTopicsSupport.BLOCKED_VOUCHER_ACTIVATION_LIST_ORDER_TASK_TOPIC;

import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.BlockedVoucherActivationListOrder;
import com.optiva.topup.voms.common.entities.orders.vouchertransitionorders.QBlockedVoucherActivationListOrder;
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
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.BLOCKED_VOUCHER_TRANSITION_ORDER + "')")
public interface BlockedVoucherActivationListOrderResource extends
    JpaRepository<BlockedVoucherActivationListOrder, Integer>,
    QuerydslPredicateExecutor<BlockedVoucherActivationListOrder>,
    QuerydslBinderCustomizer<QBlockedVoucherActivationListOrder> {

  @Override
  default void customize(QuerydslBindings bindings, QBlockedVoucherActivationListOrder root) {
    bindings.bind(Integer.class).first(
        (NumberPath<Integer> path, Integer value) -> path.stringValue().containsIgnoreCase(value.toString()));
    bindings.bind(String.class).first(
        (SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
  }

  @RestResource
  @BatchOrderSchedule(BLOCKED_VOUCHER_ACTIVATION_LIST_ORDER_TASK_TOPIC)
  @Override
  <S extends BlockedVoucherActivationListOrder> S save(S entity);

  @RestResource
  @Override
  Optional<BlockedVoucherActivationListOrder> findById(Integer integer);

  @RestResource
  @Override
  Page<BlockedVoucherActivationListOrder> findAll(Pageable pageable);

}
