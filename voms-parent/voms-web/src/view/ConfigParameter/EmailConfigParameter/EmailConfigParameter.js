import React from "react";
import ConfigParameterForm from "../../../fragment/ConfigParameterForm";

class EmailConfigParameter extends React.Component {

  render = () => {

    return (
        <ConfigParameterForm resource={"emailConfigParameters"}/>
    )
  }

}

export default EmailConfigParameter;
