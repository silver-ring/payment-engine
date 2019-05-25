import Grid from "@material-ui/core/Grid/Grid";
import React, {Fragment} from "react";
import StatusInput from "../../../fragment/StatusInput";
import SingleValueSelect from "../../../component/SingleValueSelect";
import ValidateableTextField from "../../../component/ValidateableTextField";

class NewConsumableVoucherType extends React.Component {

  render = () => {
    return (
        <Fragment>
          <Grid item xs={12}>
            <ValidateableTextField
                id="name"
                name="name"
                label="Name"
                fullWidth
                validators={['required']}
            />
          </Grid>
          <Grid item xs={12}>
            <SingleValueSelect required
                               label={"Msisdn Provider"}
                               resource={"msisdnProviders"} property={"name"}
                               field="msisdnProvider"/>
          </Grid>
          <Grid item xs={12}>
            <SingleValueSelect required
                               label={"Voucher Provider"}
                               resource={"voucherProviders"} property={"name"}
                               field="voucherProvider"/>
          </Grid>
          <Grid item xs={12}>
            <SingleValueSelect required
                               label={"Voucher Type"} resource={"voucherTypes"}
                               property={"name"}
                               field="voucherType"/>
          </Grid>
          <Grid item xs={12}>
            <StatusInput/>
          </Grid>
        </Fragment>
    )
  }
}

export default NewConsumableVoucherType;
