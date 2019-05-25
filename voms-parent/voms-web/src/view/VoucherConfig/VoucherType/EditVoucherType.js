import Grid from "@material-ui/core/Grid/Grid";
import React from "react";
import StatusInput from "../../../fragment/StatusInput";
import ValidateableTextField from "../../../component/ValidateableTextField";

class EditVoucherType extends React.Component {

  render = () => {

    const {data} = this.props;

    return (
        <React.Fragment>
          <Grid item xs={12}>
            <ValidateableTextField
                defaultValue={data.name}
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
                defaultValue={data.idAtIn}
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

export default EditVoucherType;
