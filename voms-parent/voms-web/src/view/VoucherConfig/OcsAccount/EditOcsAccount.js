import Grid from "@material-ui/core/Grid/Grid";
import React from "react";
import StatusInput from "../../../fragment/StatusInput";
import ValidateableTextField from "../../../component/ValidateableTextField";

class EditOcsAccount extends React.Component {

  render = () => {

    const {data} = this.props;

    return (
        <React.Fragment>
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
            <ValidateableTextField
                id="idAtOcs"
                name="idAtOcs"
                label="Id At OCS"
                defaultValue={data.idAtOcs}
                fullWidth
                validators={['required', 'isNumber']}
            />
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                label="Currency Unit"
                id="currency[unit]"
                name="currency[unit]"
                defaultValue={data.currency.unit}
                fullWidth
                validators={['required']}
            />
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                defaultValue={data.currency.base}
                label="Current Base"
                id="currency[base]"
                name="currency[base]"
                fullWidth
                validators={['required', 'isNumber']}
            />
          </Grid>
          <Grid item xs={12}>
            <StatusInput defaultValue={data.status}/>
          </Grid>
        </React.Fragment>
    )
  }
}

export default EditOcsAccount;
