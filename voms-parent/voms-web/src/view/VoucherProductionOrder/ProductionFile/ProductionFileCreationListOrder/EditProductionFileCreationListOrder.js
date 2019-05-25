import Grid from "@material-ui/core/Grid/Grid";
import React from "react";
import ValidateableTextField from "../../../../component/ValidateableTextField";
import SingleValueSelect from "../../../../component/SingleValueSelect";
import * as halfred from "halfred";
import EditListOrderForm from "../../../../fragment/EditListOrderForm";
import CheckboxInput from "../../../../component/CheckboxInput";

class EditProductionFileCreationListOrder extends React.Component {

  render = () => {
    const {data} = this.props;

    const parsedData = halfred.parse(data);
    const voucherPrinterDefaultValue = parsedData.link(
        "voucherPrinter")["href"];

    return (
        <EditListOrderForm
            data={data}>
          <Grid item xs={12}>
            <ValidateableTextField
                id="layoutId"
                name="layoutId"
                label="Layout Id"
                type="text"
                defaultValue={data.layoutId}
                fullWidth
                validators={['required']}/>
          </Grid>
          <Grid item xs={12}>
            <SingleValueSelect
                label={"Voucher Printer"}
                resource={"voucherPrinters"}
                property={"name"}
                field="voucherPrinter"
                required
                defaultValue={voucherPrinterDefaultValue}/>
          </Grid>
          <Grid item xs={12}>
            <CheckboxInput label={"Should Remote Transfer"}
                           field={"shouldRemoteTransfer"}
                           defaultValue={data.shouldRemoteTransfer}
            />
          </Grid>
        </EditListOrderForm>)
  }
}

export default EditProductionFileCreationListOrder;
