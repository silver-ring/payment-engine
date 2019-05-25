import React, {Fragment} from "react";
import Grid from "@material-ui/core/Grid/Grid";
import ValidateableTextField from "../../../component/ValidateableTextField";
import BatchJobScheduleStatus from "../../../fragment/BatchJobScheduleStatus";

class NewUserEmailDomainMigration extends React.Component {

  render = () => {

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
          <Grid item xs={12}>
            <ValidateableTextField
                id="migrateFromDomain"
                name="migrateFromDomain"
                label="Migrate From Domain"
                fullWidth
                validators={['required']}
            />
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                id="migrateToDomain"
                name="migrateToDomain"
                label="Migrate To Domain"
                fullWidth
                validators={['required']}
            />
          </Grid>
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

export default NewUserEmailDomainMigration;
