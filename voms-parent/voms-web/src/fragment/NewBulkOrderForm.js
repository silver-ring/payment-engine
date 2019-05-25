import React, {Fragment} from "react";
import Grid from "@material-ui/core/Grid/Grid";
import ValidateableTextField from "../component/ValidateableTextField";
import BatchJobScheduleStatus from "./BatchJobScheduleStatus";

class NewBulkOrderForm extends React.Component {

  render = () => {
    const {children} = this.props;

    return (
        <Fragment>
          <Grid item xs={12}>
            <ValidateableTextField
                id="tagId"
                name="tagId"
                label="Tag Id"
                fullWidth
                validators={['required']}
            />
          </Grid>
          {/*-------------*/}
          {children}
          {/*-------------*/}
          <Grid item xs={12}>
            <ValidateableTextField
                type="datetime-local"
                id="executionTime"
                name="executionTime"
                label="Execution Time"
                InputLabelProps={{
                  shrink: true,
                }}
                fullWidth
                validators={['required']}
            />
          </Grid>
          <Grid item xs={12}>
            <BatchJobScheduleStatus/>
          </Grid>
        </Fragment>
    )
  }

}

export default NewBulkOrderForm;
