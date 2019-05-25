package com.optiva.topup.voms.rest.resources.voucherconfig.consumablevouchertype;

import com.optiva.topup.voms.common.entities.voucherconfig.ConsumableVoucherType;
import com.optiva.topup.voms.common.entities.voucherconfig.QConsumableVoucherType;
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
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.CONSUMABLE_VOUCHER_TPYE + "')")
public interface ConsumableVoucherTypeResource extends JpaRepository<ConsumableVoucherType, Integer>,
    QuerydslPredicateExecutor<ConsumableVoucherType>, QuerydslBinderCustomizer<QConsumableVoucherType> {

  @Override
  default void customize(QuerydslBindings bindings, QConsumableVoucherType root) {
    bindings.bind(Integer.class).first(
        (NumberPath<Integer> path, Integer value) -> path.stringValue().containsIgnoreCase(value.toString()));
    bindings.bind(String.class).first(
        (SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
  }

  @RestResource
  @Override
  <S extends ConsumableVoucherType> S save(S entity);

  @RestResource
  @Override
  Optional<ConsumableVoucherType> findById(Integer integer);

  @RestResource
  @Override
  Page<ConsumableVoucherType> findAll(Pageable pageable);

}
