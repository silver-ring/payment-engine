import ListMenuItem from "../../component/ListMenuItem";
import LocalOfferIcon from '@material-ui/icons/LocalOffer';
import React from "react";
import Item from "../../component/Item";

const VoucherAdmin = (props) => {

  const {onItemClick, active} = props;

  return (
      <ListMenuItem icon={<LocalOfferIcon/>} title="Voucher Admin">
        <Item to="CreatedVoucherAdmin" active={active}
              name="Created Voucher Admin" onItemClick={onItemClick}/>
        <Item to="ActiveVoucherAdmin" active={active}
              name="Active Voucher Admin" onItemClick={onItemClick}/>
        <Item to="BlockedVoucherAdmin" active={active}
              name="Blocked Voucher Admin" onItemClick={onItemClick}/>
        <Item to="PendingUsageVoucherAdmin" active={active}
              name="Pending Usage Voucher Admin" onItemClick={onItemClick}/>
      </ListMenuItem>
  )
};

export default VoucherAdmin;