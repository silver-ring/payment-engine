import React, {Fragment} from "react";
import ValidateableTextField from "../../../component/ValidateableTextField";
import Grid from "@material-ui/core/Grid/Grid";
import Radio from "@material-ui/core/Radio/Radio";

class RechargeHistorySearch extends React.Component {

  state = {
    selectedValue: 'findAllBySerialNumber',
  };

  handleChange = event => {
    const selectedValue = event.target.value;
    this.setState({
      selectedValue: selectedValue
    });
    this.props.onChange(selectedValue);
  };

  render = () => {

    const {selectedValue} = this.state;

    return (
        <Fragment>
          <Grid item xs={12}>
            <Grid container>
              <Grid item>
                <Radio
                    id={"findAllBySerialNumber"}
                    checked={selectedValue === 'findAllBySerialNumber'}
                    onChange={this.handleChange}
                    value="findAllBySerialNumber"
                    color="primary"
                />
              </Grid>
              <Grid item>
                <ValidateableTextField
                    id="serialNumber"
                    name="serialNumber"
                    label="Serial Number"
                    disabled={selectedValue !== 'findAllBySerialNumber'}
                    fullWidth
                    validators={['required', 'isNumber']}
                />
              </Grid>
            </Grid>
          </Grid>
          <Grid item xs={12}>
            <Grid container>
              <Grid item>
                <Radio
                    id={"findAllByVoucherId"}
                    checked={selectedValue === 'findAllByVoucherId'}
                    onChange={this.handleChange}
                    value="findAllByVoucherId"
                />
              </Grid>
              <Grid item>
                <ValidateableTextField
                    id="voucherId"
                    name="voucherId"
                    label="Voucher Id"
                    disabled={selectedValue !== 'findAllByVoucherId'}
                    fullWidth
                    validators={['required', 'isNumber']}
                />
              </Grid>
            </Grid>
          </Grid>
          <Grid item xs={12}>
            <Grid container>
              <Grid item>
                <Radio
                    id={"findAllByMsisdn"}
                    checked={selectedValue === 'findAllByMsisdn'}
                    onChange={this.handleChange}
                    value="findAllByMsisdn"
                />
              </Grid>
              <Grid item>
                <ValidateableTextField
                    id="Msisdn"
                    name="Msisdn"
                    label="Msisdn"
                    disabled={selectedValue !== 'findAllByMsisdn'}
                    fullWidth
                    validators={['required']}
                />
              </Grid>
            </Grid>
          </Grid>
        </Fragment>
    );
  }

}

export default RechargeHistorySearch;