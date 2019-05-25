import React from "react";
import ConfigParameterForm from "../../../fragment/ConfigParameterForm";

class UserManagementConfigParameter extends React.Component {

  render = () => {

    return (
        <ConfigParameterForm resource={"userManagementConfigParameters"}/>
    )
  }

}

export default UserManagementConfigParameter;
