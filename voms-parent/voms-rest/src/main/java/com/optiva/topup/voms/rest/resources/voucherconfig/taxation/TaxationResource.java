package com.optiva.topup.voms.rest.resources.voucherconfig.taxation;

import com.optiva.topup.voms.common.entities.voucherconfig.QTaxation;
import com.optiva.topup.voms.common.entities.voucherconfig.Taxation;
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
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.TAXATION + "')")
public interface TaxationResource extends JpaRepository<Taxation, Integer>,
    QuerydslPredicateExecutor<Taxation>,
    QuerydslBinderCustomizer<QTaxation> {

  @Override
  default void customize(QuerydslBindings bindings, QTaxation root) {
    bindings.bind(Integer.class).first(
        (NumberPath<Integer> path, Integer value) -> path.stringValue().containsIgnoreCase(value.toString()));
    bindings.bind(String.class).first(
        (SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
  }

  @RestResource
  @Override
  <S extends Taxation> S save(S entity);

  @RestResource
  @Override
  Optional<Taxation> findById(Integer integer);

  @RestResource
  @Override
  Page<Taxation> findAll(Pageable pageable);

}
