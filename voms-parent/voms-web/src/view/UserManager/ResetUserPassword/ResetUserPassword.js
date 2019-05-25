import React from 'react';
import ResetUserPasswordActions from "./ResetUserPasswordActions";
import UserAccountSearchScreen from "../../../fragment/UserAccountSearchScreen";

class ResetUserPassword extends React.Component {

  render = () => {
    return (
        <UserAccountSearchScreen>
          <ResetUserPasswordActions/>
        </UserAccountSearchScreen>
    );
  }

}

export default ResetUserPassword;
