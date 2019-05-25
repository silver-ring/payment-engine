import React, {Fragment} from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import CssBaseline from "@material-ui/core/CssBaseline";
import Paper from "@material-ui/core/Paper";
import Avatar from "@material-ui/core/Avatar";
import logo from "../asset/img/logo.jpg";
import {ValidatorForm} from "react-material-ui-form-validator";
import ValidateableTextField from "../component/ValidateableTextField";
import Button from "@material-ui/core/Button";
import CircularProgress from "@material-ui/core/CircularProgress";
import anonymousRestInstance from "../axios/AnonymousRestInstance";
import {convertFormToJson} from "../util/ConvertUtils";
import {handleRemoteErrorResponse} from "../util/ErrorHandlerUtils";
import AlertMessage from "../component/AlertMessage";
import Typography from "@material-ui/core/Typography";

const styles = theme => ({
  layout: {
    width: 'auto',
    display: 'block', // Fix IE11 issue.
    marginLeft: theme.spacing.unit * 3,
    marginRight: theme.spacing.unit * 3,
    [theme.breakpoints.up(400 + theme.spacing.unit * 3 * 2)]: {
      width: 400,
      marginLeft: 'auto',
      marginRight: 'auto',
    },
  },
  paper: {
    marginTop: theme.spacing.unit * 8,
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: `${theme.spacing.unit * 2}px ${theme.spacing.unit
    * 3}px ${theme.spacing.unit * 3}px`,
  },
  avatar: {
    width: "100%",
    height: "100%"
  },
  form: {
    width: '100%', // Fix IE11 issue.
    marginTop: theme.spacing.unit,
  },
  submit: {
    marginTop: theme.spacing.unit * 3,
  }
});

class ResetUserPasswordConfirmation extends React.Component {

  state = {
    alertMessage: <Fragment/>,
    loading: false,
    resetUserPasswordToken: null,
    finished: false
  };

  componentWillMount() {

    const {resetUserPasswordToken} = this.props.match.params;

    const url = "resetUserPasswordConfirmation/checkToken";

    const json = {"resetUserPasswordToken": resetUserPasswordToken};

    anonymousRestInstance.post(url, json)
    .then(response => {
      this.setState({
        resetUserPasswordToken: resetUserPasswordToken
      })
    }).catch(error => {
      this.props.history.push('/login');
    })
  }

  componentDidMount() {
    // custom rule will have name 'isPasswordMatch'
    ValidatorForm.addValidationRule('isPasswordMatch', (value) => {
      const json = convertFormToJson(this.form);
      const newPassword = json["newPassword"];
      if (value !== newPassword) {
        return false;
      }
      return true;
    });
  }

  handleFormSubmit = (e) => {

    const {resetUserPasswordToken} = this.state;

    if (!this.validationForm.isFormValid(false)) {
      return;
    }

    const url = "resetUserPasswordConfirmation";

    const json = convertFormToJson(this.form);
    json["resetUserPasswordToken"] = resetUserPasswordToken;

    anonymousRestInstance.post(url, json).then(() => {
      this.setState({
        loading: false,
        finished: true
      });
    }).catch((error) => {
      const responseErrorMessage = handleRemoteErrorResponse(error);
      const errorAlertMessage = (
          <AlertMessage variant="error" message={responseErrorMessage}/>);
      this.setState({
        loading: false,
        alertMessage: errorAlertMessage
      });
    })

  };

  render = () => {
    const {classes} = this.props;
    const {resetUserPasswordToken, alertMessage, loading, finished} = this.state;

    if (resetUserPasswordToken == null) {
      return (
          <Fragment/>
      )
    }

    if (finished) {
      return (
          <React.Fragment>
            <CssBaseline/>
            <main className={classes.layout}>
              <Paper className={classes.paper}>
                <Avatar className={classes.avatar} src={logo}/>
                <Typography variant="body1" gutterBottom>
                  You Password Successfully Rest, Please click finish to be
                  redirected to the login page
                </Typography>
                <Button
                    type="submit"
                    fullWidth
                    variant="raised"
                    color="primary"
                    className={classes.submit}
                    href="/login"
                    autoFocus
                >
                  {"Finish"}
                </Button>
              </Paper>
            </main>
          </React.Fragment>
      )
    }

    return (
        <React.Fragment>
          <CssBaseline/>
          <main className={classes.layout}>
            {alertMessage}
            <Paper className={classes.paper}>
              <Avatar className={classes.avatar} src={logo}/>
              <ValidatorForm
                  ref={(form) => {
                    this.validationForm = form
                  }}
                  onSubmit={this.handleFormSubmit}
                  instantValidate={true}
              >
                <form className={classes.form} ref={(form) => {
                  this.form = form
                }}>
                  <ValidateableTextField
                      id="newPassword"
                      name="newPassword"
                      label="New Password"
                      margin="normal"
                      type="password"
                      fullWidth
                      validators={['required']}
                  />
                  <ValidateableTextField
                      id="newPasswordConfirmation"
                      name="newPasswordConfirmation"
                      label="New Password Confirmation"
                      margin="normal"
                      type="password"
                      fullWidth
                      validators={['required', 'isPasswordMatch']}
                  />
                  <Button
                      type="submit"
                      fullWidth
                      variant="raised"
                      disabled={loading}
                      color="primary"
                      className={classes.submit}
                      onClick={this.handleFormSubmit}
                      autoFocus
                  >
                    {loading ? (
                            <CircularProgress size={20}/>)
                        : "Reset Password"}
                  </Button>
                </form>
              </ValidatorForm>
            </Paper>
          </main>
        </React.Fragment>
    );
  }

}

export default withStyles(styles)(ResetUserPasswordConfirmation);
