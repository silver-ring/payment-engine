package com.optiva.topup.voms.rest.resources.systemhistory.voucherhistory;

import com.optiva.topup.voms.common.document.VoucherHistory;
import java.util.Objects;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class VoucherHistoryProcessor implements RepresentationModelProcessor<EntityModel<VoucherHistory>> {

  @Override
  public EntityModel<VoucherHistory> process(EntityModel<VoucherHistory> resource) {
    final String voucherHistoryDetailsResource = "voucherHistoryDetails";
    String voucherHistoryHref = Objects.requireNonNull(resource.getLink(IanaLinkRelations.SELF)
        .orElse(null)).getHref() + "/" + voucherHistoryDetailsResource;
    Link voucherHistoryDetailsLink = new Link(voucherHistoryHref).withRel(voucherHistoryDetailsResource);
    resource.add(voucherHistoryDetailsLink);
    return resource;
  }

}
