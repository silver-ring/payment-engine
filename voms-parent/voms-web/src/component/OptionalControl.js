import FormControlLabel
  from "@material-ui/core/FormControlLabel/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox/Checkbox";
import React, {Fragment} from "react";

class OptionalControl extends React.Component {

  state = {
    checked: false
  };

  componentWillMount = () => {
    const defaultValue = this.props.defaultValue;
    if (defaultValue != null) {
      this.setState({
        checked: defaultValue
      })
    }
  };

  handleOnCheck = (e) => {
    this.setState({
      checked: e.target.checked
    })
  };

  render() {

    const {checked} = this.state;
    const {label, field} = this.props;

    return (
        <Fragment>
          <input type={"hidden"} name={field} value={checked}/>
          <FormControlLabel
              control={
                <Checkbox
                    checked={checked}
                    onChange={this.handleOnCheck}
                />
              }
              label={label}
          />
          {checked ?
              this.props.children :
              <React.Fragment/>
          }
        </Fragment>
    );
  }

}

export default OptionalControl;