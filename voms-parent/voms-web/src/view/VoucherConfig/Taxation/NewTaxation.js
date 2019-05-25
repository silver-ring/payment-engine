import React from "react";
import Grid from "@material-ui/core/Grid/Grid";
import StatusInput from "../../../fragment/StatusInput";
import AmountTypeInput from "../../../fragment/AmountTypeInput";
import ValidateableTextField from "../../../component/ValidateableTextField";

class NewTaxation extends React.Component {

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
                id="amount"
                name="amount"
                label="Amount"
                fullWidth
                validators={['required', 'isFloat']}
            />
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                type="date"
                id="startDate"
                name="startDate"
                label="Start Date"
                InputLabelProps={{
                  shrink: true,
                }}
                fullWidth
                validators={['required']}
            />
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                type="date"
                id="endDate"
                name="endDate"
                label="End Date"
                InputLabelProps={{
                  shrink: true,
                }}
                fullWidth
                validators={['required']}
            />
          </Grid>
          <Grid item xs={12}>
            <AmountTypeInput/>
          </Grid>
          <Grid item xs={12}>
            <StatusInput/>
          </Grid>
        </React.Fragment>
    )
  }

}

export default NewTaxation;
