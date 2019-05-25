import Grid from "@material-ui/core/Grid/Grid";
import React from "react";
import ValidateableTextField from "../../../../component/ValidateableTextField";
import SingleValueSelect from "../../../../component/SingleValueSelect";
import NewListOrderForm from "../../../../fragment/NewListOrderForm";
import CheckboxInput from "../../../../component/CheckboxInput";

class NewProductionFileCreationListOrder extends React.Component {

  render = () => {
    return (
        <NewListOrderForm>
          <Grid
              item
              xs={12}>
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
                label={"Voucher Printer"}
                resource={"voucherPrinters"}
                property={"name"}
                field="voucherPrinter" required/>
          </Grid>
          <Grid item xs={12}>
            <CheckboxInput label={"Should Remote Transfer"}
                           field={"shouldRemoteTransfer"}/>
          </Grid>
        </NewListOrderForm>
    )
  }
}

export default NewProductionFileCreationListOrder;
