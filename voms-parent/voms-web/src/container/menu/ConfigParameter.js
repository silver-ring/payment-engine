import PermDataSettingIcon from '@material-ui/icons/PermDataSetting';
import ListMenuItem from "../../component/ListMenuItem";
import React from "react";
import Item from "../../component/Item";

const ConfigParameter = (props) => {

  const {onItemClick, active} = props;

  return (
      <ListMenuItem icon={<PermDataSettingIcon/>} title="Config Parameter">
        <Item to="FileManagerConfigParameter" active={active}
              name="File Manager Config Parameter" onItemClick={onItemClick}/>
        <Item to="BatchOrderConfigParameter" active={active}
              name="Batch Order Config Parameter" onItemClick={onItemClick}/>
        <Item to="VoucherPolicyConfigParameter" active={active}
              name="Voucher Policy Config Parameter" onItemClick={onItemClick}/>
        <Item to="UserManagementConfigParameter" active={active}
              name="User Management Config Parameter"
              onItemClick={onItemClick}/>
        <Item to="EmailConfigParameter" active={active}
              name="Email Config Parameter" onItemClick={onItemClick}/>
      </ListMenuItem>)
};

export default ConfigParameter;
