import React from "react";
import ConfigParameterForm from "../../../fragment/ConfigParameterForm";

class FileManagerConfigParameter extends React.Component {

  render = () => {

    return (
        <ConfigParameterForm resource={"fileManagerConfigParameters"}/>
    )
  }

}

export default FileManagerConfigParameter;
