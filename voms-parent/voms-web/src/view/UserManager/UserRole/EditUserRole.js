import Grid from "@material-ui/core/Grid/Grid";
import React, {Fragment} from "react";
import StatusInput from "../../../fragment/StatusInput";
import ValidateableTextField from "../../../component/ValidateableTextField";
import SelectableList from "../../../component/SelectableList";
import * as halfred from "halfred";

class EditUserRole extends React.Component {

  render = () => {

    const {data} = this.props;
    const parsedData = halfred.parse(data).allLinkArrays();
    const guiUserAuthoritiesDefault = parsedData["guiUserAuthorities"][0]["href"];
    const apiUserAuthoritiesDefault = parsedData["apiUserAuthorities"][0]["href"];

    return (
        <Fragment>
          <Grid item xs={12}>
            <ValidateableTextField
                id="name"
                name="name"
                label="Name"
                defaultValue={data.name}
                fullWidth
                validators={['required']}
            />
          </Grid>
          <Grid item xs={12}>
            <SelectableList title={"Gui User Authorities"}
                            resource={"guiUserAuthorities"}
                            property={"name"}
                            field={"guiUserAuthorities"}
                            defaultValue={guiUserAuthoritiesDefault}/>
          </Grid>
          <Grid item xs={12}>
            <SelectableList title={"Api User Authorities"}
                            resource={"apiUserAuthorities"}
                            property={"name"}
                            field={"apiUserAuthorities"}
                            defaultValue={apiUserAuthoritiesDefault}/>
          </Grid>
          <Grid item xs={12}>
            <StatusInput defaultValue={data.status}/>
          </Grid>
        </Fragment>
    )
  }
}

export default EditUserRole;
