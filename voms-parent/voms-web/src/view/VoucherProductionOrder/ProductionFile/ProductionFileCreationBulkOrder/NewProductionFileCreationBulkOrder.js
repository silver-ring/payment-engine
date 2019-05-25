import Grid from "@material-ui/core/Grid/Grid";
import React from "react";
import ValidateableTextField from "../../../../component/ValidateableTextField";
import NewBulkOrderForm from "../../../../fragment/NewBulkOrderForm";
import SingleValueSelect from "../../../../component/SingleValueSelect";
import CheckboxInput from "../../../../component/CheckboxInput";

class NewProductionFileCreationBulkOrder extends React.Component {

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
          <Grid item xs={12}>
            <ValidateableTextField
                id="layoutId"
                name="layoutId"
                label="Layout Id"
                type="text"
                fullWidth
                validators={['required']}
            />
          </Grid>
          <Grid item xs={12}>
            <SingleValueSelect
                label={"Voucher Printer"} resource={"voucherPrinters"}
                property={"name"}
                field="voucherPrinter" required/>
          </Grid>
          <Grid item xs={12}>
            <CheckboxInput label={"Should Remote Transfer"}
                           field={"shouldRemoteTransfer"}/>
          </Grid>
        </NewBulkOrderForm>
    )
  }
}

export default NewProductionFileCreationBulkOrder;
