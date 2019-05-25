import Grid from "@material-ui/core/Grid/Grid";
import React from "react";
import SingleValueSelect from "../../../component/SingleValueSelect";
import ValidateableTextField from "../../../component/ValidateableTextField";
import NewBulkOrderForm from "../../../fragment/NewBulkOrderForm";

class NewCreatedVoucherActivationBulkOrder extends React.Component {

  render = () => {
    return (
        <NewBulkOrderForm>
          <Grid item xs={12}>
            <ValidateableTextField
                id="startSerialNumber"
                name="startSerialNumber"
                label="Start Serial Number"
                fullWidth
                validators={['required', 'isNumber']}
            />
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                id="endSerialNumber"
                name="endSerialNumber"
                label="End Serial Number"
                fullWidth
                validators={['required', 'isNumber']}
            />
          </Grid>
          <Grid item xs={12}>
            <SingleValueSelect required
                               label={"Voucher Provider"}
                               resource={"voucherProviders"}
                               property={"name"}
                               field="voucherProvider"/>
          </Grid>
        </NewBulkOrderForm>
    )
  }
}

export default NewCreatedVoucherActivationBulkOrder;