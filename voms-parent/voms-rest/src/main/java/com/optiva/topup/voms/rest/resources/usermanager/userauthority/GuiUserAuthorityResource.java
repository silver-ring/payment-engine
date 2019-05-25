package com.optiva.topup.voms.rest.resources.usermanager.userauthority;

import com.optiva.topup.voms.common.entities.usermanager.GuiUserAuthority;
import com.optiva.topup.voms.common.entities.usermanager.QGuiUserAuthority;
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
@PreAuthorize("hasAuthority('" + GuiUserAuthoritiesHelper.USER_ROLE + "')")
public interface GuiUserAuthorityResource extends JpaRepository<GuiUserAuthority, Integer>,
    QuerydslPredicateExecutor<GuiUserAuthority>, QuerydslBinderCustomizer<QGuiUserAuthority> {

  @Override
  default void customize(QuerydslBindings bindings, QGuiUserAuthority root) {
    bindings.bind(Integer.class).first(
        (NumberPath<Integer> path, Integer value) -> path.stringValue().containsIgnoreCase(value.toString()));
    bindings.bind(String.class).first(
        (SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
  }

  @RestResource
  @Override
  Page<GuiUserAuthority> findAll(Pageable pageable);

  @RestResource
  @Override
  Optional<GuiUserAuthority> findById(Integer integer);

}
