import React from "react";
import NewBulkOrderForm from "../../../fragment/NewBulkOrderForm";
import Grid from "@material-ui/core/Grid/Grid";
import ValidateableTextField from "../../../component/ValidateableTextField";

class NewActiveVoucherBlockingBulkOrder extends React.Component {

  render = () => {

    return (
        <NewBulkOrderForm>
          <Grid item xs={12}>
            <ValidateableTextField
                id="startSerialNumber"
                name="startSerialNumber"
                label="Start Serial Number"
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
                fullWidth
                validators={['required', 'isNumber']}
            />
          </Grid>
        </NewBulkOrderForm>
    )

  }
}

export default NewActiveVoucherBlockingBulkOrder;
