import Grid from "@material-ui/core/Grid";
import ValidateableTextField from "../../../component/ValidateableTextField";
import React, {Fragment} from "react";
import SingleValueSelect from "../../../component/SingleValueSelect";
import * as halfred from "halfred";

class ModifyCreatedVoucherAdmin extends React.Component {
  render() {

    const {data} = this.props;

    const parsedData = halfred.parse(data).allLinkArrays();

    const voucherTypeDefault = parsedData["voucherType"][0]["href"];
    const rechargeValueDefault = parsedData["rechargeValue"][0]["href"];

    return (
        <Fragment>
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
                defaultValue={data.expirationDate}
            />
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                id="rechargePeriod"
                name="rechargePeriod"
                label="Recharge Period"
                fullWidth
                validators={['required', 'isNumber']}
                defaultValue={data.rechargePeriod}
            />
          </Grid>
          <Grid item xs={12}>
            <SingleValueSelect required
                               label={"Voucher Type"} resource={"voucherTypes"}
                               property={"name"}
                               field="voucherType"
                               defaultValue={voucherTypeDefault}/>
          </Grid>
          <Grid item xs={12}>
            <SingleValueSelect required
                               label={"Recharge Value"}
                               resource={"rechargeValues"}
                               property={"name"}
                               field="rechargeValue"
                               defaultValue={rechargeValueDefault}/>
          </Grid>
        </Fragment>
    );
  }
}

export default ModifyCreatedVoucherAdmin;