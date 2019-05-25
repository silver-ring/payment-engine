import React from "react";
import InputLabel from "@material-ui/core/InputLabel/InputLabel";
import Select from "@material-ui/core/Select/Select";
import MenuItem from "@material-ui/core/MenuItem/MenuItem";
import FormControl from "@material-ui/core/FormControl/FormControl";

class SelectInput extends React.Component {

  state = {
    selectedValue: "",
    open: false
  };

  componentWillMount = () => {
    const defaultValue = this.props.defaultValue;

    this.setState({
      selectedValue: defaultValue
    })
  };

  handleChange = event => {
    this.setState({selectedValue: event.target.value});
  };

  handleClose = () => {
    this.setState({open: false});
  };

  handleOpen = () => {
    this.setState({open: true});
  };

  render = () => {

    const {options, field, label} = this.props;
    const {selectedValue, open} = this.state;

    return (
        <FormControl style={{width: "100%"}}>
          <InputLabel htmlFor={field}>{label}</InputLabel>
          <Select
              open={open}
              onClose={this.handleClose}
              onOpen={this.handleOpen}
              value={selectedValue}
              onChange={this.handleChange}
              inputProps={{
                name: field,
                id: field,
              }}
          >
            {
              Object.keys(options).map(key => (
                  <MenuItem key={key} value={key}>{options[key]}</MenuItem>
              ))
            }
          </Select>
        </FormControl>
    )
  }

}

export default SelectInput;
