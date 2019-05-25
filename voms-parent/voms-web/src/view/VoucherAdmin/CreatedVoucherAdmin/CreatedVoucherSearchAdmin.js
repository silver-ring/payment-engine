import React, {Fragment} from "react";
import ValidateableTextField from "../../../component/ValidateableTextField";
import Grid from "@material-ui/core/Grid/Grid";
import Radio from "@material-ui/core/Radio/Radio";

class CreatedVoucherSearchAdmin extends React.Component {
  state = {
    selectedValue: 'findBySerialNumber',
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

    const findBySerialNumber = "findBySerialNumber";
    const findByVoucherId = "findByVoucherId";

    return (
        <Fragment>
          <Grid item xs={12}>
            <Grid container>
              <Grid item>
                <Radio
                    id={findBySerialNumber}
                    checked={selectedValue === findBySerialNumber}
                    onChange={this.handleChange}
                    value={findBySerialNumber}
                    color="primary"
                />
              </Grid>
              <Grid item>
                <ValidateableTextField
                    id="serialNumber"
                    name="serialNumber"
                    label="Serial Number"
                    disabled={selectedValue !== findBySerialNumber}
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
                    id={findByVoucherId}
                    checked={selectedValue === findByVoucherId}
                    onChange={this.handleChange}
                    value={findByVoucherId}
                />
              </Grid>
              <Grid item>
                <ValidateableTextField
                    id="voucherId"
                    name="voucherId"
                    label="Voucher Id"
                    disabled={selectedValue !== findByVoucherId}
                    fullWidth
                    validators={['required', 'isNumber']}
                />
              </Grid>
            </Grid>
          </Grid>
        </Fragment>
    );
  }

}

export default CreatedVoucherSearchAdmin;
