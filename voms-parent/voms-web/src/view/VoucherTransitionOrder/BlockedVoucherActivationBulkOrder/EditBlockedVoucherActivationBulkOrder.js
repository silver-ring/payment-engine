import Grid from "@material-ui/core/Grid/Grid";
import React from "react";
import ValidateableTextField from "../../../component/ValidateableTextField";
import EditBulkOrderForm from "../../../fragment/EditBulkOrderForm";

class EditBlockedVoucherActivationBulkOrder extends React.Component {

  render = () => {

    const {data} = this.props;

    return (
        <EditBulkOrderForm data={data}>
          <Grid item xs={12}>
            <ValidateableTextField
                id="startSerialNumber"
                name="startSerialNumber"
                label="Start Serial Number"
                defaultValue={data.startSerialNumber}
                type="text"
                fullWidth
                validators={['required', 'isNumber']}
            />
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                id="endSerialNumber"
                name="endSerialNumber"
                label="End Serial Number"
                type="text"
                defaultValue={data.endSerialNumber}
                fullWidth
                validators={['required', 'isNumber']}
            />
          </Grid>
        </EditBulkOrderForm>
    )
  }
}

export default EditBlockedVoucherActivationBulkOrder;
