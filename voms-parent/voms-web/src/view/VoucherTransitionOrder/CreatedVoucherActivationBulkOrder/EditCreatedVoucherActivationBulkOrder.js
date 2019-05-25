import Grid from "@material-ui/core/Grid/Grid";
import React from "react";
import * as halfred from "halfred";
import SingleValueSelect from "../../../component/SingleValueSelect";
import ValidateableTextField from "../../../component/ValidateableTextField";
import EditBulkOrderForm from "../../../fragment/EditBulkOrderForm";

class EditCreatedVoucherActivationBulkOrder extends React.Component {

  render = () => {
    const {data} = this.props;

    const parsedData = halfred.parse(data);

    const voucherProviderDefaultValue = parsedData.link(
        "voucherProvider")["href"];

    return (
        <EditBulkOrderForm data={data}>
          <Grid item xs={12}>
            <ValidateableTextField
                id="startSerialNumber"
                name="startSerialNumber"
                label="Start Serial Number"
                defaultValue={data.startSerialNumber}
                fullWidth
                validators={['required', 'isNumber']}
            />
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                id="endSerialNumber"
                name="endSerialNumber"
                label="End Serial Number"
                defaultValue={data.endSerialNumber}
                fullWidth
                validators={['required', 'isNumber']}
            />
          </Grid>
          <Grid item xs={12}>
            <SingleValueSelect required
                               label={"Voucher Provider"}
                               resource={"voucherProviders"}
                               property={"name"}
                               field="voucherProvider"
                               defaultValue={voucherProviderDefaultValue}/>
          </Grid>
        </EditBulkOrderForm>
    )
  }
}

export default EditCreatedVoucherActivationBulkOrder;