import React from 'react';
import DeleteUserAccountActions from "./DeleteUserAccountActions";
import UserAccountSearchScreen from "../../../fragment/UserAccountSearchScreen";

class DeleteUserAccount extends React.Component {

  render = () => {
    return (
        <UserAccountSearchScreen>
          <DeleteUserAccountActions/>
        </UserAccountSearchScreen>
    );
  }

}

export default DeleteUserAccount;
