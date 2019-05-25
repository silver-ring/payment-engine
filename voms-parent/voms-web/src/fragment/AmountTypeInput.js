import InputLabel from "@material-ui/core/InputLabel/InputLabel";
import FormControlLabel
  from "@material-ui/core/FormControlLabel/FormControlLabel";
import Radio from "@material-ui/core/Radio/Radio";
import React, {Fragment} from "react";

class AmountTypeInput extends React.Component {

  state = {
    selectedAmountType: 'FIXED',
  };

  componentWillMount = () => {
    const selectedAmountType = this.props.defaultValue;
    if (selectedAmountType != null) {
      this.setState({
        selectedAmountType: selectedAmountType
      })
    }
  };

  handleChangeAmountType = event => {
    this.setState({selectedAmountType: event.target.value});
  };

  render = () => {
    const {selectedAmountType} = this.state;

    return (
        <Fragment>
          <input type="hidden" name="amountType" value={selectedAmountType}/>
          <InputLabel required style={{margin: "10px"}}>Amount Type</InputLabel>
          <FormControlLabel
              value="FIXED"
              control={<Radio checked={selectedAmountType === 'FIXED'}
                              value="FIXED"
                              onChange={this.handleChangeAmountType}
                              color="primary"/>}
              label="Fixed"
          />
          <FormControlLabel
              value="PERCENTAGE"
              control={<Radio checked={selectedAmountType === 'PERCENTAGE'}
                              value="PERCENTAGE"
                              onChange={this.handleChangeAmountType}/>}
              label="Percentage"
          />
        </Fragment>
    );
  }

}

export default AmountTypeInput;
