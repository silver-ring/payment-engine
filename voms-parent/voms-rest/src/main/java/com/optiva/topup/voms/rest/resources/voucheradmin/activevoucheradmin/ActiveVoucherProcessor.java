package com.optiva.topup.voms.rest.resources.voucheradmin.activevoucheradmin;

import com.optiva.topup.voms.common.entities.vouchers.ActiveVoucher;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class ActiveVoucherProcessor implements RepresentationModelProcessor<EntityModel<ActiveVoucher>> {

  @Override
  public EntityModel<ActiveVoucher> process(EntityModel<ActiveVoucher> resource) {
    String activeVoucherHref = Objects.requireNonNull(resource.getLink(IanaLinkRelations.SELF)
        .orElse(null)).getHref();

    String slash = "/";
    List<Link> links = new ArrayList<>();

    final String activeVoucherDetailsResource = "activeVoucherDetails";
    String voucherDetailsHref = activeVoucherHref + slash + activeVoucherDetailsResource;
    Link voucherDetailsLink = new Link(voucherDetailsHref).withRel(activeVoucherDetailsResource);
    links.add(voucherDetailsLink);

    final String blockResource = "block";
    String blockResourceHref = activeVoucherHref + slash + blockResource;
    Link blockLink = new Link(blockResourceHref).withRel(blockResource);
    links.add(blockLink);

    resource.add(links);
    return resource;
  }

}
