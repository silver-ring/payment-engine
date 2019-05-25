import ListMenuItem from "../../component/ListMenuItem";
import React from "react";
import WorkIcon from '@material-ui/icons/Work';
import Item from "../../component/Item";

const VoucherProductionOrder = (props) => {

  const {onItemClick, active} = props;

  return (
      <ListMenuItem icon={<WorkIcon/>} title="Voucher Production Order">
        <Item to="VoucherCreationBatchOrder" active={active}
              name="Voucher Creation Batch Order"
              onItemClick={onItemClick}/>
        <Item to="ProductionFile" active={active} name="Production File"
              onItemClick={onItemClick}/>
      </ListMenuItem>)
};

export default VoucherProductionOrder;
