import React, {Fragment} from "react";
import Grid from "@material-ui/core/Grid/Grid";
import ValidateableTextField from "../component/ValidateableTextField";
import BatchJobScheduleStatus from "./BatchJobScheduleStatus";
import {convertJsonDateToJsDate} from "../util/ConvertUtils";
import FileUpload from "../component/FileUpload";

class EditListOrderForm extends React.Component {

  render = () => {
    const {children, data} = this.props;

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
            <FileUpload required label={"Order List"}
                        originalFileNameField={"originalFileName"}
                        fileNameField={"fileName"}
                        defaultOriginalFileName={data.originalFileName}
                        defaultFileName={data.fileName}/>
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
                defaultValue={executionTime}
                fullWidth
                validators={['required']}
            />
          </Grid>
          <Grid item xs={12}>
            <BatchJobScheduleStatus defaultValue={data.batchOrderStatus}/>
          </Grid>
        </Fragment>
    )
  }

}

export default EditListOrderForm;
