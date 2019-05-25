import React from "react";
import Grid from "@material-ui/core/Grid/Grid";
import StatusInput from "../../../fragment/StatusInput";
import SelectableList from "../../../component/SelectableList";
import SingleValueSelect from "../../../component/SingleValueSelect";
import ValidateableTextField from "../../../component/ValidateableTextField";

class NewRechargeValue extends React.Component {

  render = () => {
    return (
        <React.Fragment>
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
            <SingleValueSelect
                label={"Ocs Account"} resource={"ocsAccounts"} property={"name"}
                field="ocsAccount" required
            />
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                id="value"
                name="value"
                label="Value"
                fullWidth
                validators={['required', 'isFloat']}
            />
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                id="valueTaxation"
                name="valueTaxation"
                label="Value Taxation"
                defaultValue={0}
                fullWidth
                validators={['required', 'isFloat']}
            />
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                id="valuePromotion"
                name="valuePromotion"
                label="Value Promotion"
                defaultValue={0}
                fullWidth
                validators={['required', 'isFloat']}
            />
          </Grid>
          <Grid item xs={12}>
            <SelectableList title={"Promotion"} resource={"promotions"}
                            property={"name"}
                            field={"promotions"}/>
          </Grid>

          <Grid item xs={12}>
            <SelectableList title={"Taxation"} resource={"taxations"}
                            property={"name"}
                            field={"taxations"}/>
          </Grid>

          <Grid item xs={12}>
            <StatusInput/>
          </Grid>
        </React.Fragment>
    )
  }

}

export default NewRechargeValue;
