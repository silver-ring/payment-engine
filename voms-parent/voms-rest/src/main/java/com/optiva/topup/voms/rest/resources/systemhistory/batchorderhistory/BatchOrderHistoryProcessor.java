package com.optiva.topup.voms.rest.resources.systemhistory.batchorderhistory;

import com.optiva.topup.voms.common.document.BatchOrderHistory;
import java.util.Objects;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class BatchOrderHistoryProcessor implements
    RepresentationModelProcessor<EntityModel<BatchOrderHistory>> {

  @Override
  public EntityModel<BatchOrderHistory> process(EntityModel<BatchOrderHistory> resource) {
    final String detailsRel = "batchOrderHistoryDetails";
    String batchOrderHistoryHref = Objects.requireNonNull(resource.getLink(IanaLinkRelations.SELF)
        .orElse(null)).getHref() + "/" + detailsRel;
    Link batchOrderHistoryDetailsLink = new Link(batchOrderHistoryHref)
        .withRel(detailsRel);
    resource.add(batchOrderHistoryDetailsLink);
    return resource;
  }

}
