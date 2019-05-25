import ListMenuItem from "../../component/ListMenuItem";
import React from "react";
import ShopIcon from '@material-ui/icons/Shop';
import Item from "../../component/Item";

const VoucherModificationOrder = (props) => {

  const {onItemClick, active} = props;

  return (
      <ListMenuItem icon={<ShopIcon/>} title="Voucher Modification Order">
        <Item to="CreatedVoucherModificationOrder" active={active}
              name="Created Voucher Modification Order"
              onItemClick={onItemClick}/>
        <Item to="BlockedVoucherModificationOrder" active={active}
              name="Blocked Voucher Modification Order"
              onItemClick={onItemClick}/>
      </ListMenuItem>)
};

export default VoucherModificationOrder;
