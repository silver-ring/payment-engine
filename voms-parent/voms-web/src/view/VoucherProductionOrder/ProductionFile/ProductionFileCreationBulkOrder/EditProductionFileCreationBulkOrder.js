import React from "react";
import ValidateableTextField from "../../../../component/ValidateableTextField";
import Grid from "@material-ui/core/Grid";
import CheckboxInput from "../../../../component/CheckboxInput";
import SingleValueSelect from "../../../../component/SingleValueSelect";
import EditBulkOrderForm from "../../../../fragment/EditBulkOrderForm";
import * as halfred from "halfred";

class EditProductionFileCreationBulkOrder extends React.Component {

  render = () => {
    const {data} = this.props;

    const parsedData = halfred.parse(data);

    const voucherPrinterDefaultValue = parsedData.link(
        "voucherPrinter")["href"];

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
          <Grid item xs={12}>
            <ValidateableTextField
                id="layoutId"
                name="layoutId"
                label="Layout Id"
                type="text"
                defaultValue={data.layoutId}
                fullWidth
                validators={['required']}
            />
          </Grid>
          <Grid item xs={12}>
            <SingleValueSelect
                label={"Voucher Printer"} resource={"voucherPrinters"}
                property={"name"}
                field="voucherPrinter" required
                defaultValue={voucherPrinterDefaultValue}/>
          </Grid>
          <Grid item xs={12}>
            <CheckboxInput label={"Should Remote Transfer"}
                           field={"shouldRemoteTransfer"}
                           defaultValue={data.shouldRemoteTransfer}
            />
          </Grid>
        </EditBulkOrderForm>
    )
  }
}

export default EditProductionFileCreationBulkOrder;
