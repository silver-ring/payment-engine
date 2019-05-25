package com.optiva.topup.voms.rest.resources.voucherconfig.promotion;

import com.optiva.topup.voms.common.entities.voucherconfig.Promotion;
import com.optiva.topup.voms.common.entities.voucherconfig.QPromotion;
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
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.PROMOTION + "')")
public interface PromotionResource extends JpaRepository<Promotion, Integer>,
    QuerydslPredicateExecutor<Promotion>, QuerydslBinderCustomizer<QPromotion> {

  @Override
  default void customize(QuerydslBindings bindings, QPromotion root) {
    bindings.bind(Integer.class).first(
        (NumberPath<Integer> path, Integer value) -> path.stringValue().containsIgnoreCase(value.toString()));
    bindings.bind(String.class).first(
        (SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
  }

  @RestResource
  @Override
  <S extends Promotion> S save(S entity);

  @RestResource
  @Override
  Optional<Promotion> findById(Integer integer);

  @RestResource
  @Override
  Page<Promotion> findAll(Pageable pageable);

}
