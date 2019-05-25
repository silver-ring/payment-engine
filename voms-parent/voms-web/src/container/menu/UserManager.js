import AssignmentIndIcon from '@material-ui/icons/AssignmentInd';
import ListMenuItem from "../../component/ListMenuItem";
import React from "react";
import Item from "../../component/Item";

const UserManager = (props) => {

  const {onItemClick, active} = props;

  return (
      <ListMenuItem icon={<AssignmentIndIcon/>} title="User Manager">
        <Item to="UserActivity" active={active} name="User Activity"
              onItemClick={onItemClick}/>
        <Item to="UserRole" active={active} name="User Role"
              onItemClick={onItemClick}/>
        <Item to="UserRegistration" active={active} name="User Registration"
              onItemClick={onItemClick}/>
        <Item to="ResetUserPassword" active={active} name="Reset User Password"
              onItemClick={onItemClick}/>
        <Item to="UpdateUserRoles" active={active} name="Update User Roles"
              onItemClick={onItemClick}/>
        <Item to="ChangeUserAccountStatus" active={active}
              name="Change User Account Status"
              onItemClick={onItemClick}/>
        <Item to="DeleteUserAccount" active={active} name="Delete User Account"
              onItemClick={onItemClick}/>
        <Item to="UserAccount" active={active} name="User Account"
              onItemClick={onItemClick}/>
        <Item to="UserEmailDomainMigration" active={active}
              name="User Email Domain Migration"
              onItemClick={onItemClick}/>
      </ListMenuItem>)
};

export default UserManager;
