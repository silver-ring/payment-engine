import Grid from "@material-ui/core/Grid/Grid";
import React from "react";
import ValidateableTextField from "../../../component/ValidateableTextField";
import EditBulkOrderForm from "../../../fragment/EditBulkOrderForm";
import {convertJsonDateToJsDate} from "../../../util/ConvertUtils";

class EditPendingUsageVoucherBlockingBulkOrder extends React.Component {

  render = () => {

    const {data} = this.props;

    const pendingRangeStartTime = convertJsonDateToJsDate(
        data.pendingRangeStartTime);
    const pendingRangeEndTime = convertJsonDateToJsDate(
        data.pendingRangeEndTime);

    return (
        <EditBulkOrderForm data={data}>
          <Grid item xs={12}>
            <ValidateableTextField
                type="datetime-local"
                id="pendingRangeStartTime"
                name="pendingRangeStartTime"
                label="Pending Range Start Time"
                InputLabelProps={{
                  shrink: true,
                }}
                fullWidth
                validators={['required']}
                defaultValue={pendingRangeStartTime}
            />
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                type="datetime-local"
                id="pendingRangeEndTime"
                name="pendingRangeEndTime"
                label="Pending Range End Time"
                InputLabelProps={{
                  shrink: true,
                }}
                fullWidth
                validators={['required']}
                defaultValue={pendingRangeEndTime}
            />
          </Grid>
        </EditBulkOrderForm>
    )
  }
}

export default EditPendingUsageVoucherBlockingBulkOrder;