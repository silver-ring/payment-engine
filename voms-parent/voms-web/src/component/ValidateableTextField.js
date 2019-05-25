import React, {Fragment} from "react";
import TextField from "@material-ui/core/TextField/TextField";
import {TextValidator, ValidatorForm} from 'react-material-ui-form-validator';
import isValidPath from 'is-valid-path';

const defaultErrorMessages = {
  matchRegexp: "field should match expression",
  isEmail: "field should be email",
  isEmpty: "field should have value",
  required: "field is required",
  trim: "field should be trim",
  isNumber: "field should be a number",
  isFloat: "field should be float",
  isPositive: "field should be positive",
  minNumber: "field should have min number",
  maxNumber: "field should not exceed max value",
  minFloat: "field should have min float value",
  maxFloat: "field should not exceed float value",
  minStringLength: "field should not exceed max length",
  maxStringLength: "field should have min length",
  isString: "field should be string",
  isPasswordMatch: "password doesn't match",
  isValidPath: "field should be valid path"
};

class ValidateableTextField extends React.Component {

  state = {
    value: ""
  };

  componentWillMount = () => {
    if (this.props.defaultValue != null) {
      this.setState({
        value: this.props.defaultValue
      })
    }
  };

  componentDidMount() {
    ValidatorForm.addValidationRule('isValidPath', (value) => {
      return isValidPath(value);
    });
  }

  handleOnChange = (e) => {
    this.setState({
      value: e.target.value
    });
  };

  render() {
    const {value} = this.state;
    const {label, validators, errorMessages, disabled, ...rest} = this.props;

    let newLabel = label;

    if (validators.includes("required")) {
      newLabel = label + " *";
    }

    let newErrorMessages = errorMessages;

    if (newErrorMessages == null) {
      newErrorMessages = [];
      validators.map(key => {
        if (key.includes("matchRegexp")) {
          newErrorMessages.push(defaultErrorMessages["matchRegexp"]);
        } else {
          newErrorMessages.push(defaultErrorMessages[key]);
        }
      })
    }

    let newValidators = validators;
    if (disabled != null && disabled === true) {
      newValidators = [];
    }

    return (
        <Fragment>
          {disabled ?
              <TextField
                  {...rest}
                  disabled={disabled}
                  value={value}
                  label={newLabel}
              />
              :
              <TextValidator
                  {...rest}
                  disabled={disabled}
                  onChange={this.handleOnChange}
                  value={value}
                  label={newLabel}
                  validators={newValidators}
                  errorMessages={newErrorMessages}
              />
          }
        </Fragment>
    );
  }
}

export default ValidateableTextField;