import React, {Fragment} from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import Paper from '@material-ui/core/Paper';
import withStyles from '@material-ui/core/styles/withStyles';
import AlertMessage from "../component/AlertMessage";
import CircularProgress
  from "@material-ui/core/CircularProgress/CircularProgress";
import authInstance from '../axios/AuthInstance';
import {clearAuth, saveAuth} from "../store/Auth";
import {convertFormToJson} from "../util/ConvertUtils";
import logo from "./../asset/img/logo.jpg";
import ValidateableTextField from "../component/ValidateableTextField";
import {ValidatorForm} from "react-material-ui-form-validator";
import {handleRemoteErrorResponse} from "../util/ErrorHandlerUtils";

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

class Login extends React.Component {

  state = {
    alertMessage: (<Fragment/>),
    loading: false,
    error: false
  };

  componentWillMount = () => {
    // remove session info
    clearAuth();
  };

  handleLoginSubmit = (e) => {
    e.preventDefault();

    if (!this.validationForm.isFormValid(false)) {
      return;
    }

    const json = convertFormToJson(this.form);

    const username = json["username"];
    const password = json["password"];
    const authHeader = "Basic " + btoa(username + ":" + password);
    const config = {
      headers: {
        "Authorization": authHeader,
        "Content-Type": "application/x-www-form-urlencoded"
      }
    };

    let data = "grant_type=client_credentials";

    authInstance.post("/oauth/token", data, config)
    .then((response) => {
      saveAuth(response.data);
      this.props.history.push('/');
    }).catch((error) => {
          const responseErrorMessage = handleRemoteErrorResponse(error);
          const errorAlertMessage = (
              <AlertMessage variant="error" message={responseErrorMessage}/>);
          this.setState({
            loading: false,
            alertMessage: errorAlertMessage
          });
        }
    );

    this.setState({
      loading: true,
      alertMessage: (<Fragment/>)
    })
  };

  render = () => {
    const {classes} = this.props;
    const {alertMessage, loading} = this.state;

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
                  onSubmit={this.handleLoginSubmit}
                  instantValidate={true}
              >
                <form className={classes.form} ref={(form) => {
                  this.form = form
                }}>

                  <ValidateableTextField
                      id="username"
                      name="username"
                      label="Email"
                      fullWidth
                      margin="normal"
                      validators={['required']}
                      autoFocus
                  />
                  <ValidateableTextField
                      id="password"
                      name="password"
                      label="Password"
                      margin="normal"
                      type="password"
                      fullWidth
                      validators={['required']}
                      autoFocus
                  />
                  <Button
                      type="submit"
                      fullWidth
                      variant="raised"
                      disabled={loading}
                      color="primary"
                      className={classes.submit}
                      onClick={this.handleLoginSubmit}
                      autoFocus
                  >
                    {loading ? (
                            <CircularProgress size={20}/>)
                        : "Login"}
                  </Button>
                </form>
              </ValidatorForm>
            </Paper>
          </main>
        </React.Fragment>
    );
  }
}

export default withStyles(styles)(Login);
