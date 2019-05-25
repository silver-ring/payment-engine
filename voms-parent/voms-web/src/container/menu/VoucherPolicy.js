import ListMenuItem from "../../component/ListMenuItem";
import React from "react";
import ReceiptIcon from '@material-ui/icons/Receipt';
import Item from "../../component/Item";

const VoucherPolicy = (props) => {

  const {onItemClick, active} = props;

  return (
      <ListMenuItem icon={<ReceiptIcon/>} title="Voucher Policy">
        <Item to="VoucherPolicySchedule" active={active}
              name="Voucher Policy Schedule" onItemClick={onItemClick}/>
      </ListMenuItem>)
};

export default VoucherPolicy;
