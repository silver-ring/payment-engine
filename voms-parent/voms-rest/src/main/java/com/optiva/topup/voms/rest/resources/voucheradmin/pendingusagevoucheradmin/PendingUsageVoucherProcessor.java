package com.optiva.topup.voms.rest.resources.voucheradmin.pendingusagevoucheradmin;

import com.optiva.topup.voms.common.entities.vouchers.PendingUsageVoucher;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class PendingUsageVoucherProcessor implements
    RepresentationModelProcessor<EntityModel<PendingUsageVoucher>> {

  @Override
  public EntityModel<PendingUsageVoucher> process(EntityModel<PendingUsageVoucher> resource) {
    String pendingUsageVoucherHref = Objects.requireNonNull(resource.getLink(IanaLinkRelations.SELF)
        .orElse(null)).getHref();

    String slash = "/";
    List<Link> links = new ArrayList<>();

    final String detailsRel = "pendingUsageVoucherDetails";
    String voucherDetailsHref = pendingUsageVoucherHref + slash + detailsRel;
    Link voucherDetailsLink = new Link(voucherDetailsHref).withRel(detailsRel);
    links.add(voucherDetailsLink);

    final String blockResource = "block";
    String blockResourceHref = pendingUsageVoucherHref + slash + blockResource;
    Link blockLink = new Link(blockResourceHref).withRel(blockResource);
    links.add(blockLink);

    final String activateResource = "activate";
    String activateResourceHref = pendingUsageVoucherHref + slash + activateResource;
    Link activateLink = new Link(activateResourceHref).withRel(activateResource);
    links.add(activateLink);

    final String useResource = "use";
    String useResourceHref = pendingUsageVoucherHref + slash + useResource;
    Link useLink = new Link(useResourceHref).withRel(useResource);
    links.add(useLink);

    resource.add(links);
    return resource;
  }

}
