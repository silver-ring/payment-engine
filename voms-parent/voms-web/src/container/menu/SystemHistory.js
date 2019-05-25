import EventNoteIcon from '@material-ui/icons/EventNote';
import ListMenuItem from "../../component/ListMenuItem";
import React from "react";
import Item from "../../component/Item";

const SystemHistory = (props) => {

  const {onItemClick, active} = props;

  return (
      <ListMenuItem icon={<EventNoteIcon/>} title="System History">
        <Item to="UserNotificationEmailHistory" active={active}
              name="User Notification Email History"
              onItemClick={onItemClick}/>
        <Item to="RemoteFileTransferHistory" active={active}
              name="Remote File Transfer History"
              onItemClick={onItemClick}/>
        <Item to="UserEmailDomainMigrationHistory" active={active}
              name="User Email Domain Migration History"
              onItemClick={onItemClick}/>
        <Item to="VoucherHistory" active={active} name="Voucher History"
              onItemClick={onItemClick}/>
        <Item to="RechargeHistory" active={active} name="Recharge History"
              onItemClick={onItemClick}/>
        <Item to="VoucherPolicyHistory" active={active}
              name="Voucher Policy History" onItemClick={onItemClick}/>
        <Item to="BatchOrderHistory" active={active} name="Batch Order History"
              onItemClick={onItemClick}/>
      </ListMenuItem>
  )
};

export default SystemHistory;
