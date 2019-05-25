import ListMenuItem from "../../component/ListMenuItem";
import React from "react";
import BuildIcon from '@material-ui/icons/Build';
import Item from "../../component/Item";

const VoucherConfig = (props) => {

  const {onItemClick, active} = props;

  return (
      <ListMenuItem icon={<BuildIcon/>} title="Voucher Config">

        <Item to="VoucherType" active={active}
              name="Voucher Type" onItemClick={onItemClick}/>

        <Item to="VoucherPrinter" active={active}
              name="Voucher Printer" onItemClick={onItemClick}/>

        <Item to="OcsAccount" active={active}
              name="Ocs Account" onItemClick={onItemClick}/>

        <Item to="Taxation" active={active}
              name="Taxation" onItemClick={onItemClick}/>

        <Item to="Promotion" active={active}
              name="Promotion" onItemClick={onItemClick}/>

        <Item to="RechargeValue" active={active}
              name="Recharge Value" onItemClick={onItemClick}/>

        <Item to="VoucherProvider" active={active}
              name="Voucher Provider" onItemClick={onItemClick}/>

        <Item to="MsisdnProvider" active={active}
              name="Msisdn Provider" onItemClick={onItemClick}/>

        <Item to="ConsumableVoucherType" active={active}
              name="Consumable Voucher Type" onItemClick={onItemClick}/>

      </ListMenuItem>)
};

export default VoucherConfig;
