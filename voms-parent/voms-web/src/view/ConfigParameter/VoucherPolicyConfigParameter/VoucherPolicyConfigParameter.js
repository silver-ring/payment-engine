import React from "react";
import ConfigParameterForm from "../../../fragment/ConfigParameterForm";

class VoucherPolicyConfigParameter extends React.Component {

  render = () => {

    return (
        <ConfigParameterForm resource={"voucherPolicyConfigParameters"}/>
    )
  }

}

export default VoucherPolicyConfigParameter;
