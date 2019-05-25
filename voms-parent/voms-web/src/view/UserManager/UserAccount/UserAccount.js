import React from 'react';
import UserAccountActions from "./UserAccountActions";
import UserAccountSearchScreen from "../../../fragment/UserAccountSearchScreen";

class UserAccount extends React.Component {

  render = () => {
    return (
        <UserAccountSearchScreen>
          <UserAccountActions/>
        </UserAccountSearchScreen>
    );
  }

}

export default UserAccount;
