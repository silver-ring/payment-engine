package com.optiva.topup.voms.rest.resources.voucheradmin.createdvoucheradmin;

import com.optiva.topup.voms.common.entities.vouchers.CreatedVoucher;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class CreatedVoucherProcessor implements RepresentationModelProcessor<EntityModel<CreatedVoucher>> {

  @Override
  public EntityModel<CreatedVoucher> process(EntityModel<CreatedVoucher> resource) {
    String createdVoucherHref =
        Objects.requireNonNull(resource.getLink(IanaLinkRelations.SELF).orElse(null)).getHref();
    String slash = "/";
    List<Link> links = new ArrayList<>();

    final String createdVoucherDetailsResource = "createdVoucherDetails";
    String voucherDetailsHref = createdVoucherHref + slash + createdVoucherDetailsResource;
    Link voucherDetailsLink = new Link(voucherDetailsHref).withRel(createdVoucherDetailsResource);
    links.add(voucherDetailsLink);

    resource.add(links);
    return resource;
  }

}
