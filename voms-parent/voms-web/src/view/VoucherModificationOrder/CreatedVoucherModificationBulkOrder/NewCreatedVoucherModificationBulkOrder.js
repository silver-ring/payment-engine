import React from "react";
import Grid from "@material-ui/core/Grid/Grid";
import ValidateableTextField from "../../../component/ValidateableTextField";
import NewBulkOrderForm from "../../../fragment/NewBulkOrderForm";
import SingleValueSelect from "../../../component/SingleValueSelect";
import OptionalControl from "../../../component/OptionalControl";

class NewCreatedVoucherModificationBulkOrder extends React.Component {

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
        </NewBulkOrderForm>
    )

  }
}

export default NewCreatedVoucherModificationBulkOrder;
