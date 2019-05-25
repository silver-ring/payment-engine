import Grid from "@material-ui/core/Grid/Grid";
import React from "react";
import StatusInput from "../../../fragment/StatusInput";
import ValidateableTextField from "../../../component/ValidateableTextField";

class NewOcsAccount extends React.Component {

  render = () => {
    return (
        <React.Fragment>
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
            <ValidateableTextField
                id="idAtOcs"
                name="idAtOcs"
                label="Id At OCS"
                fullWidth
                validators={['required', 'isNumber']}
            />
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                label="Currency Unit"
                id="currency[unit]"
                name="currency[unit]"
                fullWidth
                validators={['required']}
            />
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                defaultValue={0}
                label="Current Base"
                id="currency[base]"
                name="currency[base]"
                fullWidth
                validators={['required', 'isNumber']}
            />
          </Grid>
          <Grid item xs={12}>
            <StatusInput/>
          </Grid>
        </React.Fragment>
    )
  }
}

export default NewOcsAccount;
