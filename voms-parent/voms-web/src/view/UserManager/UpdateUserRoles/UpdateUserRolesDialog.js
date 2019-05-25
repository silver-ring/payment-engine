import React from 'react';
import withStyles from '@material-ui/core/styles/withStyles';
import CssBaseline from '@material-ui/core/CssBaseline';
import Button from '@material-ui/core/Button';
import CircularProgress
  from "@material-ui/core/CircularProgress/CircularProgress";
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import {ValidatorForm} from "react-material-ui-form-validator";
import SelectableList from "../../../component/SelectableList";
import * as halfred from "halfred";
import AlertMessage from "../../../component/AlertMessage";
import {convertFormToJson} from "../../../util/ConvertUtils";
import {handleRemoteErrorResponse} from "../../../util/ErrorHandlerUtils";
import TextField from "@material-ui/core/TextField";
import restInstance from "../../../axios/RestInstance";

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

class UpdateUserRolesDialog extends React.Component {

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
    restInstance.put("updateUserRoles", json)
    .then((response) => {
      const successMessage = "User Roles Updated successfully!";
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
    const {open, data} = this.props;
    const {loading} = this.state;

    const defaultUserRoles = halfred.parse(data).link("userRole")["href"];

    return (
        <React.Fragment>
          <CssBaseline/>
          {this.state.alertMessage}
          <Dialog open={open} onClose={this.handleClose}
                  aria-labelledby="form-dialog-title" fullWidth>
            <DialogTitle id="form-dialog-title">Update User Roles</DialogTitle>
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
                  <TextField
                      readonly={true}
                      id="email"
                      name="email"
                      label="Email"
                      value={data.email}
                      InputLabelProps={{shrink: true}}
                      fullWidth
                  />
                  <SelectableList title={"User Roles"}
                                  resource={"userRoles"}
                                  property={"name"}
                                  field={"userRoles"}
                                  defaultValue={defaultUserRoles}/>
                </form>
              </ValidatorForm>
            </DialogContent>
            <DialogActions>
              <Button color="primary"
                      variant={"contained"}
                      disabled={loading}
                      onClick={this.handleFormSubmit}
                      autoFocus>
                {loading ? (
                    <CircularProgress size={20}/>) : "Update User Roles"}
              </Button>
              <Button color="primary"
                      disabled={loading}
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

export default withStyles(styles)(UpdateUserRolesDialog);
