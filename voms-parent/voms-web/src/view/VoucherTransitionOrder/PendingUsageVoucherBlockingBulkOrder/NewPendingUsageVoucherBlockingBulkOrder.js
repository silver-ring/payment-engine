import Grid from "@material-ui/core/Grid/Grid";
import React from "react";
import ValidateableTextField from "../../../component/ValidateableTextField";
import NewBulkOrderForm from "../../../fragment/NewBulkOrderForm";

class NewPendingUsageVoucherBlockingBulkOrder extends React.Component {

  render = () => {

    return (
        <NewBulkOrderForm>
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
            />
          </Grid>
        </NewBulkOrderForm>
    )

  }
}

export default NewPendingUsageVoucherBlockingBulkOrder;