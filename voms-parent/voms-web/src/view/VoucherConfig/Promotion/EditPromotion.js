import React, {Fragment} from "react";
import Grid from "@material-ui/core/Grid/Grid";
import StatusInput from "../../../fragment/StatusInput";
import AmountTypeInput from "../../../fragment/AmountTypeInput";
import ValidateableTextField from "../../../component/ValidateableTextField";

class EditPromotion extends React.Component {

  render = () => {

    const {data} = this.props;

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
            <ValidateableTextField
                id="amount"
                name="amount"
                label="Amount"
                defaultValue={data.amount}
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
                defaultValue={data.startDate}
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
                defaultValue={data.endDate}
                fullWidth
                validators={['required']}
            />
          </Grid>
          <Grid item xs={12}>
            <AmountTypeInput defaultValue={data.selectedAmountType}/>
          </Grid>
          <Grid item xs={12}>
            <StatusInput defaultValue={data.status}/>
          </Grid>
        </Fragment>
    );
  }

}

export default EditPromotion;
