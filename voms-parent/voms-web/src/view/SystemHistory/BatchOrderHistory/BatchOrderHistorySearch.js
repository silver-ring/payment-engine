import React, {Fragment} from "react";
import ValidateableTextField from "../../../component/ValidateableTextField";
import Grid from "@material-ui/core/Grid/Grid";

class BatchOrderHistorySearch extends React.Component {

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
        </Fragment>
    );
  }

}

export default BatchOrderHistorySearch;