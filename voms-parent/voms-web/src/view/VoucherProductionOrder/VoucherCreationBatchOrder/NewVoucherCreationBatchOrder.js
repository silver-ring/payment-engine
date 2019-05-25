import Grid from "@material-ui/core/Grid/Grid";
import React from "react";
import SingleValueSelect from "../../../component/SingleValueSelect";
import ValidateableTextField from "../../../component/ValidateableTextField";
import NewBulkOrderForm from "../../../fragment/NewBulkOrderForm";

class NewVoucherCreationBatchOrder extends React.Component {

  render = () => {
    return (
        <NewBulkOrderForm>
          <Grid item xs={12}>
            <ValidateableTextField
                id="numberOfVouchers"
                name="numberOfVouchers"
                label="Number Of Vouchers"
                fullWidth
                validators={['required', 'isNumber']}
            />
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                type="date"
                id="expirationDate"
                name="expirationDate"
                label="Expiration Date"
                InputLabelProps={{
                  shrink: true,
                }}
                fullWidth
                validators={['required']}
            />
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                id="rechargePeriod"
                name="rechargePeriod"
                label="Recharge Period"
                fullWidth
                validators={['required', 'isNumber']}
            />
          </Grid>
          <Grid item xs={12}>
            <SingleValueSelect required
                               label={"Voucher Type"} resource={"voucherTypes"}
                               property={"name"}
                               field="voucherType"/>
          </Grid>
          <Grid item xs={12}>
            <SingleValueSelect required
                               label={"Recharge Value"}
                               resource={"rechargeValues"}
                               property={"name"}
                               field="rechargeValue"/>
          </Grid>
        </NewBulkOrderForm>
    )
  }
}

export default NewVoucherCreationBatchOrder;
