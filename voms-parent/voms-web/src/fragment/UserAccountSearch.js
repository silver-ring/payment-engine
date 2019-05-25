import React, {Fragment} from "react";
import Grid from "@material-ui/core/Grid";
import ValidateableTextField from "../component/ValidateableTextField";

class ChangeUserAccountStatusSearch extends React.Component {

  render = () => {

    return (
        <Fragment>
          <Grid item xs={12}>
            <Grid item>
              <ValidateableTextField
                  id="email"
                  name="email"
                  label="Email"
                  fullWidth
                  validators={['required']}
              />
            </Grid>
          </Grid>
        </Fragment>
    );
  }

}

export default ChangeUserAccountStatusSearch;