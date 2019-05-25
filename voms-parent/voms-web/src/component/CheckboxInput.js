import React from "react";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import Input from "@material-ui/core/Input/Input";

class CheckboxInput extends React.Component {

  state = {
    value: false
  };

  componentDidMount = () => {
    this.setState({
      value: this.props.defaultValue
    })
  };

  handleChange = () => {
    const newValue = !this.state.value;
    this.setState({
      value: newValue
    })
  };

  render = () => {

    const {label, field} = this.props;
    const {value} = this.state;

    return (
        <React.Fragment>
          <Input type={"hidden"} name={field} value={value}/>
          <FormControlLabel
              control={
                <Checkbox
                    checked={value}
                    onChange={this.handleChange}
                    color="primary"
                />
              }
              label={label}
          />
        </React.Fragment>
    )
  }

}

export default CheckboxInput;