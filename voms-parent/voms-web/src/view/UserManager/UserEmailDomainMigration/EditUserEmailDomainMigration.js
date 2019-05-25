import React, {Fragment} from "react";
import Grid from "@material-ui/core/Grid/Grid";
import {convertJsonDateToJsDate} from "../../../util/ConvertUtils";
import ValidateableTextField from "../../../component/ValidateableTextField";
import BatchJobScheduleStatus from "../../../fragment/BatchJobScheduleStatus";

class EditUserEmailDomainMigration extends React.Component {

  render = () => {
    const {data} = this.props;

    const executionTime = convertJsonDateToJsDate(data.executionTime);

    return (
        <Fragment>
          <Grid item xs={12}>
            <ValidateableTextField
                id="tagId"
                name="tagId"
                label="Tag Id"
                fullWidth
                defaultValue={data.tagId}
                validators={['required']}
            />
          </Grid>

          <Grid item xs={12}>
            <ValidateableTextField
                id="migrateFromDomain"
                name="migrateFromDomain"
                label="Migrate From Domain"
                fullWidth
                defaultValue={data.migrateFromDomain}
                validators={['required']}
            />
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                id="migrateToDomain"
                name="migrateToDomain"
                label="Migrate To Domain"
                fullWidth
                defaultValue={data.migrateToDomain}
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
                defaultValue={executionTime}
                fullWidth
                validators={['required']}
            />
          </Grid>
          <Grid item xs={12}>
            <BatchJobScheduleStatus defaultValue={data.batchJobScheduleStatus}/>
          </Grid>
        </Fragment>
    )
  }

}

export default EditUserEmailDomainMigration;
