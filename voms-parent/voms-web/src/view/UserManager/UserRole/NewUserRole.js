import Grid from "@material-ui/core/Grid/Grid";
import React, {Fragment} from "react";
import StatusInput from "../../../fragment/StatusInput";
import ValidateableTextField from "../../../component/ValidateableTextField";
import SelectableList from "../../../component/SelectableList";

class NewUserRole extends React.Component {

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
            <SelectableList title={"Gui User Authorities"}
                            resource={"guiUserAuthorities"}
                            property={"name"}
                            field={"guiUserAuthorities"}/>
          </Grid>
          <Grid item xs={12}>
            <SelectableList title={"Api User Authority"}
                            resource={"apiUserAuthorities"}
                            property={"name"}
                            field={"apiUserAuthorities"}/>
          </Grid>
          <Grid item xs={12}>
            <StatusInput/>
          </Grid>
        </Fragment>
    )
  }
}

export default NewUserRole;
