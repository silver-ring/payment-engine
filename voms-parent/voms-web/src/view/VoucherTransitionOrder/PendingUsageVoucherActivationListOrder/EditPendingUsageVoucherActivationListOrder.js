import React from "react";
import EditListOrderForm from "../../../fragment/EditListOrderForm";

class EditPendingUsageVoucherActivationListOrder extends React.Component {

  render = () => {

    const {data} = this.props;

    return (
        <EditListOrderForm data={data}>

        </EditListOrderForm>
    )
  }
}

export default EditPendingUsageVoucherActivationListOrder;
