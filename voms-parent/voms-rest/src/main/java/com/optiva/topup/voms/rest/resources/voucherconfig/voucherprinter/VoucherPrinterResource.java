package com.optiva.topup.voms.rest.resources.voucherconfig.voucherprinter;

import com.optiva.topup.voms.common.entities.voucherconfig.QVoucherPrinter;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherPrinter;
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
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.VOUCHER_PRINTER + "')")
public interface VoucherPrinterResource extends JpaRepository<VoucherPrinter, Integer>,
    QuerydslPredicateExecutor<VoucherPrinter>, QuerydslBinderCustomizer<QVoucherPrinter> {

  @Override
  default void customize(QuerydslBindings bindings, QVoucherPrinter root) {
    bindings.bind(Integer.class).first(
        (NumberPath<Integer> path, Integer value) -> path.stringValue().containsIgnoreCase(value.toString()));
    bindings.bind(String.class).first(
        (SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
  }

  @RestResource
  @Override
  <S extends VoucherPrinter> S save(S entity);

  @RestResource
  @Override
  Optional<VoucherPrinter> findById(Integer integer);

  @RestResource
  @Override
  Page<VoucherPrinter> findAll(Pageable pageable);

}
