import React from "react";
import ConfigParameterForm from "../../../fragment/ConfigParameterForm";

class BatchOrderConfigParameter extends React.Component {

  render = () => {

    return (
        <ConfigParameterForm resource={"batchOrderConfigParameters"}/>
    )
  }

}

export default BatchOrderConfigParameter;
