import React, {Fragment} from "react";
import Grid from "@material-ui/core/Grid/Grid";
import StatusInput from "../../../fragment/StatusInput";
import SelectableList from "../../../component/SelectableList";
import * as halfred from "halfred";
import SingleValueSelect from "../../../component/SingleValueSelect";
import ValidateableTextField from "../../../component/ValidateableTextField";

class EditRechargeValue extends React.Component {

  render = () => {

    const {data} = this.props;

    const parsedData = halfred.parse(data).allLinkArrays();

    const ocaAccountDefault = parsedData["ocsAccount"][0]["href"];
    const promotionsDefault = parsedData["promotions"][0]["href"];
    const taxationsDefault = parsedData["taxations"][0]["href"];

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
            <SingleValueSelect
                label={"Ocs Account"} resource={"ocsAccounts"} property={"name"}
                field="ocsAccount" defaultValue={ocaAccountDefault} required/>
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                id="value"
                name="value"
                label="Value"
                defaultValue={data.value}
                fullWidth
                validators={['required', 'isFloat']}
            />
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                id="valueTaxation"
                name="valueTaxation"
                label="Value Taxation"
                defaultValue={data.valueTaxation}
                fullWidth
                validators={['required', 'isFloat']}
            />
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                id="valuePromotion"
                name="valuePromotion"
                label="Value Promotion"
                defaultValue={data.valuePromotion}
                fullWidth
                validators={['required', 'isFloat']}
            />
          </Grid>
          <Grid item xs={12}>
            <SelectableList title={"Promotion"} resource={"promotions"}
                            property={"name"} field={"promotions"}
                            defaultValue={promotionsDefault}/>
          </Grid>
          <Grid item xs={12}>
            <SelectableList title={"Taxation"} resource={"taxations"}
                            property={"name"} field={"taxations"}
                            defaultValue={taxationsDefault}/>
          </Grid>
          <Grid item xs={12}>
            <StatusInput defaultValue={data.status}/>
          </Grid>
        </Fragment>
    );
  }

}

export default EditRechargeValue;
