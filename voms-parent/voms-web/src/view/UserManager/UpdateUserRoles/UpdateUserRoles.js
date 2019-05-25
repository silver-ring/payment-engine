import React from 'react';
import UpdateUserRolesActions from "./UpdateUserRolesActions";
import UserAccountSearchScreen from "../../../fragment/UserAccountSearchScreen";

class UpdateUserRoles extends React.Component {

  render = () => {
    return (
        <UserAccountSearchScreen>
          <UpdateUserRolesActions/>
        </UserAccountSearchScreen>
    );
  }

}

export default UpdateUserRoles;
