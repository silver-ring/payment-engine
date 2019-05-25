package com.optiva.topup.voms.rest.resources.voucheradmin.blockedvoucheradmin;

import com.optiva.topup.voms.common.entities.vouchers.BlockedVoucher;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class BlockedVoucherProcessor implements RepresentationModelProcessor<EntityModel<BlockedVoucher>> {

  @Override
  public EntityModel<BlockedVoucher> process(EntityModel<BlockedVoucher> resource) {
    String blockedVoucherHref = Objects.requireNonNull(resource.getLink(IanaLinkRelations.SELF)
        .orElse(null)).getHref();

    String slash = "/";
    List<Link> links = new ArrayList<>();

    final String blockedVoucherDetailsResource = "blockedVoucherDetails";
    String voucherDetailsHref = blockedVoucherHref + slash + blockedVoucherDetailsResource;
    Link voucherDetailsLink = new Link(voucherDetailsHref).withRel(blockedVoucherDetailsResource);
    links.add(voucherDetailsLink);

    final String activateResource = "activate";
    String activateResourceHref = blockedVoucherHref + slash + activateResource;
    Link activateLink = new Link(activateResourceHref).withRel(activateResource);
    links.add(activateLink);

    resource.add(links);
    return resource;
  }

}
