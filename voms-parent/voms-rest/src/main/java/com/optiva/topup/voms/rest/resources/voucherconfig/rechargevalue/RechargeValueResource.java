package com.optiva.topup.voms.rest.resources.voucherconfig.rechargevalue;

import com.optiva.topup.voms.common.entities.voucherconfig.QRechargeValue;
import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import com.optiva.topup.voms.common.security.GuiUserAuthoritiesHelper;
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
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.RECHARGE_VALUE + "')")
public interface RechargeValueResource extends JpaRepository<RechargeValue, Integer>,
    QuerydslPredicateExecutor<RechargeValue>, QuerydslBinderCustomizer<QRechargeValue> {

  @Override
  default void customize(QuerydslBindings bindings, QRechargeValue root) {
    bindings.bind(Integer.class).first((NumberPath<Integer> path, Integer value) -> path.stringValue()
        .containsIgnoreCase(value.toString()));
    bindings.bind(String.class).first(
        (SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
  }

  @RestResource
  @Override
  <S extends RechargeValue> S save(S entity);

  @RestResource
  @Override
  Optional<RechargeValue> findById(Integer integer);

  @RestResource
  @Override
  Page<RechargeValue> findAll(Pageable pageable);

}
