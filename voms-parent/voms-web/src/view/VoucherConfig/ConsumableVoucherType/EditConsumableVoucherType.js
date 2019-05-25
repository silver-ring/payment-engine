import Grid from "@material-ui/core/Grid/Grid";
import React, {Fragment} from "react";
import StatusInput from "../../../fragment/StatusInput";
import SingleValueSelect from "../../../component/SingleValueSelect";
import * as halfred from "halfred";
import ValidateableTextField from "../../../component/ValidateableTextField";

class EditConsumableVoucherType extends React.Component {

  render = () => {

    const {data} = this.props;
    const parsedData = halfred.parse(data);

    const msisdnProviderDefaultValue = halfred.parse(parsedData).link(
        "msisdnProvider")["href"];
    const voucherProviderDefaultValue = halfred.parse(parsedData).link(
        "voucherProvider")["href"];
    const voucherTypeDefaultValue = halfred.parse(parsedData).link(
        "voucherType")["href"];

    return (
        <Fragment>
          <Grid item xs={12}>
            <ValidateableTextField
                id="name"
                name="name"
                label="Name"
                defaultValue={data.name}
                fullWidth
                validators={['required']}
            />
          </Grid>
          <Grid item xs={12}>
            <SingleValueSelect required
                               label={"Msisdn Provider"}
                               resource={"msisdnProviders"} property={"name"}
                               defaultValue={msisdnProviderDefaultValue}
                               field="msisdnProvider"/>
          </Grid>
          <Grid item xs={12}>
            <SingleValueSelect required
                               label={"Voucher Provider"}
                               resource={"voucherProviders"} property={"name"}
                               defaultValue={voucherProviderDefaultValue}
                               field="voucherProvider"/>
          </Grid>
          <Grid item xs={12}>
            <SingleValueSelect required
                               label={"Voucher Type"} resource={"voucherTypes"}
                               property={"name"}
                               defaultValue={voucherTypeDefaultValue}
                               field="voucherTypes"/>
          </Grid>
          <Grid item xs={12}>
            <StatusInput defaultValue={data.status}/>
          </Grid>
        </Fragment>
    )
  }
}

export default EditConsumableVoucherType;
