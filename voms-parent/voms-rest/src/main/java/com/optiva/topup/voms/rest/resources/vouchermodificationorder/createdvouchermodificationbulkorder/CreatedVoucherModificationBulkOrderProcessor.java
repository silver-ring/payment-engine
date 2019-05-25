package com.optiva.topup.voms.rest.resources.vouchermodificationorder.createdvouchermodificationbulkorder;

import com.optiva.topup.voms.common.entities.orders.vouchermodificationorders.CreatedVoucherModificationBulkOrder;
import com.optiva.topup.voms.common.entities.voucherconfig.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class CreatedVoucherModificationBulkOrderProcessor implements
    RepresentationModelProcessor<EntityModel<CreatedVoucherModificationBulkOrder>> {

  private final RepositoryEntityLinks repositoryEntityLinks;

  @Autowired
  public CreatedVoucherModificationBulkOrderProcessor(RepositoryEntityLinks repositoryEntityLinks) {
    this.repositoryEntityLinks = repositoryEntityLinks;
  }

  @Override
  public EntityModel<CreatedVoucherModificationBulkOrder> process(
      EntityModel<CreatedVoucherModificationBulkOrder> resource) {

    if (resource.getContent().getVoucherType() != null) {
      Link link = repositoryEntityLinks
          .linkToItemResource(VoucherType.class, resource.getContent().getVoucherType().getId())
          .withRel("modifyVoucherType");
      resource.add(link);
    }

    if (resource.getContent().getRechargeValue() != null) {
      Link link = repositoryEntityLinks
          .linkToItemResource(VoucherType.class, resource.getContent().getRechargeValue().getId())
          .withRel("modifyRechargeValue");
      resource.add(link);
    }

    return resource;
  }

}
