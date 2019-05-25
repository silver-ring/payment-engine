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

class UserProfile extends React.Component {

  state = {
    alertMessage: <React.Fragment/>,
    loading: false,
    error: false,
    data: {}
  };

  handleClose = () => {
    this.props.onClose();
  };

  componentWillMount() {

    const url = "userProfile";

    restInstance.get(url)
    .then(response => {
      this.setState({
        data: response.data,
        loading: false,
        error: false,
        alertMessage: <React.Fragment/>
      })
    }).catch(error => {
      const responseErrorMessage = handleRemoteErrorResponse(error);
      const errorAlertMessage = (
          <AlertMessage variant="error" message={responseErrorMessage}/>);
      this.setState({
        loading: false,
        error: true,
        alertMessage: errorAlertMessage
      });
    });
    this.setState({
      loading: true,
      error: false,
      alertMessage: <React.Fragment/>
    });
  }

  handleFormSubmit = (e) => {
    e.preventDefault();

    if (!this.validationForm.isFormValid(false)) {
      return;
    }
    const json = convertFormToJson(this.form);
    restInstance.patch("userProfile", json)
    .then((response) => {
      const successMessage = "Profile Updated Successfully!";
      const successAlertMessage = (
          <AlertMessage variant="success" message={successMessage}/>);
      const data = response.data;
      this.setState({
        loading: false,
        error: false,
        alertMessage: successAlertMessage,
        data: data
      });
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
    const {data, loading} = this.state;

    return (
        <React.Fragment>
          <CssBaseline/>
          {this.state.alertMessage}
          <Dialog open={open} onClose={this.handleClose}
                  aria-labelledby="form-dialog-title" fullWidth>
            <DialogTitle id="form-dialog-title">Change Profile</DialogTitle>
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
                      fullWidth
                      id="firstName"
                      name="firstName"
                      label="First Name"
                      margin="dense"
                      defaultValue={data.firstName}
                      validators={['required']}
                  />
                  <ValidateableTextField
                      fullWidth
                      id="lastName"
                      name="lastName"
                      label="Last Name"
                      margin="dense"
                      defaultValue={data.lastName}
                      validators={['required']}
                  />
                </form>
              </ValidatorForm>
            </DialogContent>
            <DialogActions>
              <Button color="primary"
                      variant={"contained"}
                      disabled={loading}
                      onClick={this.handleFormSubmit}>
                {loading ? (
                    <CircularProgress size={20}/>) : "Update Profile"}
              </Button>
              <Button
                  color="primary"
                  onClick={this.handleClose}
                  disabled={loading}
                  autoFocus>
                Cancel
              </Button>
            </DialogActions>
          </Dialog>
        </React.Fragment>
    );
  }
}

export default withStyles(styles)(UserProfile);
