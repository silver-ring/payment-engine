import React from 'react';
import withStyles from '@material-ui/core/styles/withStyles';
import CssBaseline from '@material-ui/core/CssBaseline';
import Button from '@material-ui/core/Button';
import {restInstance} from "../axios/RestInstance";
import CircularProgress
  from "@material-ui/core/CircularProgress/CircularProgress";
import AlertMessage from "../component/AlertMessage";
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import {convertFormToJson} from "../util/ConvertUtils";
import ValidateableTextField from "../component/ValidateableTextField";
import {ValidatorForm} from "react-material-ui-form-validator";
import {handleRemoteErrorResponse} from "../util/ErrorHandlerUtils";

const styles = theme => ({
  buttons: {
    display: 'flex',
    justifyContent: 'flex-end',
  },
  button: {
    marginTop: theme.spacing.unit * 3,
    marginLeft: theme.spacing.unit,
  },
});

class ChangePassword extends React.Component {

  state = {
    alertMessage: <React.Fragment/>,
    loading: false,
    error: false
  };

  handleClose = () => {
    this.props.onClose();
  };

  handleFormSubmit = (e) => {
    e.preventDefault();

    if (!this.validationForm.isFormValid(false)) {
      return;
    }
    const json = convertFormToJson(this.form);
    restInstance.post("ChangePassword", json)
    .then((response) => {
      const successMessage = "Password change successfully!";
      const successAlertMessage = (
          <AlertMessage variant="success" message={successMessage}/>);
      this.setState({
        loading: false,
        error: false,
        alertMessage: successAlertMessage,
        open: false
      });
      this.handleClose();
    }).catch((error) => {
          const responseErrorMessage = handleRemoteErrorResponse(error);
          const errorAlertMessage = (
              <AlertMessage variant="error" message={responseErrorMessage}/>);
          this.setState({
            loading: false,
            error: true,
            alertMessage: errorAlertMessage
          });
        }
    );
    this.setState({
      loading: true,
      error: false,
      alertMessage: <React.Fragment/>
    });
  };

  render() {
    const {open} = this.props;

    return (
        <React.Fragment>
          <CssBaseline/>
          {this.state.alertMessage}
          <Dialog open={open} onClose={this.handleClose}
                  aria-labelledby="form-dialog-title" fullWidth>
            <DialogTitle id="form-dialog-title">Change
              Password</DialogTitle>
            <DialogContent>
              <ValidatorForm
                  ref={(form) => {
                    this.validationForm = form
                  }}
                  onSubmit={this.handleFormSubmit}
                  instantValidate={true}
              >
                <form ref={(form) => {
                  this.form = form
                }}>
                  <ValidateableTextField
                      autoFocus
                      fullWidth
                      id="currentPassword"
                      name="currentPassword"
                      type="password"
                      label="Current Password"
                      margin="dense"
                      validators={['required']}
                  />
                </form>
              </ValidatorForm>
            </DialogContent>
            <DialogActions>
              <Button color="primary"
                      variant={"contained"}
                      disabled={this.state.loading}
                      onClick={this.handleFormSubmit}>
                {this.state.loading ? (
                    <CircularProgress size={20}/>) : "Change Password"}
              </Button>
              <Button color="primary"
                      disabled={this.state.loading}
                      onClick={this.handleClose}
                      autoFocus>
                {"Cancel"}
              </Button>
            </DialogActions>
          </Dialog>
        </React.Fragment>
    );
  }
}

export default withStyles(styles)(ChangePassword);