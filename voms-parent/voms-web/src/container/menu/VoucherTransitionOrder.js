import ListMenuItem from "../../component/ListMenuItem";
import React from "react";
import NextWeekIcon from '@material-ui/icons/NextWeek';
import Item from "../../component/Item";

const VoucherTransitionOrder = (props) => {

  const {onItemClick, active} = props;

  return (
      <ListMenuItem icon={<NextWeekIcon/>} title="Voucher Transition Order">
        <Item to="CreatedVoucherTransitionOrder" active={active}
              name="Created Voucher Transition Order"
              onItemClick={onItemClick}/>
        <Item to="ActiveVoucherTransitionOrder" active={active}
              name="Active Voucher Transition Order"
              onItemClick={onItemClick}/>
        <Item to="BlockedVoucherTransitionOrder" active={active}
              name="Blocked Voucher Transition Order"
              onItemClick={onItemClick}/>
        <Item to="PendingUsageVoucherTransitionOrder" active={active}
              name="Pending Usage Voucher Transition Order"
              onItemClick={onItemClick}/>

      </ListMenuItem>)
};

export default VoucherTransitionOrder;
