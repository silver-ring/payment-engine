import React from 'react';
import ChangeUserAccountStatusActions from "./ChangeUserAccountStatusActions";
import UserAccountSearchScreen from "../../../fragment/UserAccountSearchScreen";

class ChangeUserAccountStatus extends React.Component {

  render = () => {
    return (
        <UserAccountSearchScreen>
          <ChangeUserAccountStatusActions/>
        </UserAccountSearchScreen>
    );
  }

}

export default ChangeUserAccountStatus;
