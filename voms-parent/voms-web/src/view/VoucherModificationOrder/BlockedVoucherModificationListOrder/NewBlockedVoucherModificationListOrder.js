import React from "react";
import Grid from "@material-ui/core/Grid/Grid";
import ValidateableTextField from "../../../component/ValidateableTextField";
import NewListOrderForm from "../../../fragment/NewListOrderForm";
import OptionalControl from "../../../component/OptionalControl";
import SingleValueSelect from "../../../component/SingleValueSelect";

class NewBlockedVoucherModificationListOrder extends React.Component {

  render = () => {

    return (
        <NewListOrderForm>
          <Grid item xs={12}>
            <OptionalControl label={"Modify Expiration Date"}>
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
            </OptionalControl>
          </Grid>
          <Grid item xs={12}>
            <OptionalControl label={"Modify Recharge Period"}>
              <ValidateableTextField
                  id="rechargePeriod"
                  name="rechargePeriod"
                  label="Recharge Period"
                  fullWidth
                  validators={['required', 'isNumber']}
              />
            </OptionalControl>
          </Grid>
          <Grid item xs={12}>
            <OptionalControl label={"Modify Voucher Type"}>
              <SingleValueSelect required
                                 label={"Voucher Type"}
                                 resource={"voucherTypes"}
                                 property={"name"}
                                 field="voucherType"/>
            </OptionalControl>
          </Grid>
          <Grid item xs={12}>
            <OptionalControl label={"Modify Recharge Value"}>
              <SingleValueSelect
                  required
                  label={"Recharge Value"}
                  resource={"rechargeValues"}
                  property={"name"}
                  field="rechargeValue"/>
            </OptionalControl>
          </Grid>
        </NewListOrderForm>
    )

  }
}

export default NewBlockedVoucherModificationListOrder;
