import Grid from "@material-ui/core/Grid/Grid";
import React, {Fragment} from "react";
import StatusInput from "../../../fragment/StatusInput";
import ValidateableTextField from "../../../component/ValidateableTextField";

class NewVoucherProvider extends React.Component {

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
            <ValidateableTextField
                id="idAtIn"
                name="idAtIn"
                label="Id At In"
                fullWidth
                validators={['required', 'isNumber']}
            />
          </Grid>
          <Grid item xs={12}>
            <StatusInput/>
          </Grid>
        </Fragment>
    )
  }
}

export default NewVoucherProvider;
