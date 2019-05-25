import React from "react";
import Grid from "@material-ui/core/Grid/Grid";
import ValidateableTextField from "../../../component/ValidateableTextField";
import NewBulkOrderForm from "../../../fragment/NewBulkOrderForm";
import OptionalControl from "../../../component/OptionalControl";
import SingleValueSelect from "../../../component/SingleValueSelect";

class NewBlockedVoucherModificationBulkOrder extends React.Component {

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
            <OptionalControl label={"Modify Expiration Date"}
                             field={"modifyExpirationDate"}>
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
            <OptionalControl label={"Modify Recharge Period"}
                             field={"modifyRechargePeriod"}>
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
            <OptionalControl label={"Modify Voucher Type"}
                             field={"modifyVoucherType"}>
              <SingleValueSelect required
                                 label={"Voucher Type"}
                                 resource={"voucherTypes"}
                                 property={"name"}
                                 field="voucherType"/>
            </OptionalControl>
          </Grid>
          <Grid item xs={12}>
            <OptionalControl label={"Modify Recharge Value"}
                             field={"modifyRechargeValue"}>
              <SingleValueSelect
                  required
                  label={"Recharge Value"}
                  resource={"rechargeValues"}
                  property={"name"}
                  field="rechargeValue"/>
            </OptionalControl>
          </Grid>
        </NewBulkOrderForm>
    )

  }
}

export default NewBlockedVoucherModificationBulkOrder;
