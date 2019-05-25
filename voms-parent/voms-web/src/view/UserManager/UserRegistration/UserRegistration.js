import React, {Fragment} from "react";
import Paper from "@material-ui/core/Paper";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import {ValidatorForm} from "react-material-ui-form-validator";
import {withRouter} from "react-router";
import withStyles from "@material-ui/core/styles/withStyles";
import Button from "@material-ui/core/Button";
import CircularProgress from "@material-ui/core/CircularProgress";
import CardActions from "@material-ui/core/CardActions";
import {convertFormToJson} from "../../../util/ConvertUtils";
import AlertMessage from "../../../component/AlertMessage";
import {handleRemoteErrorResponse} from "../../../util/ErrorHandlerUtils";
import ValidateableTextField from "../../../component/ValidateableTextField";
import SelectableList from "../../../component/SelectableList";
import restInstance from "../../../axios/RestInstance";
import Grid from "@material-ui/core/Grid";

const styles = theme => ({
  layout: {
    width: 'auto',
    padding: theme.spacing.unit * 4,
    marginTop: theme.spacing.unit * 2,
    marginDown: theme.spacing.unit * 2,
    marginLeft: theme.spacing.unit * 2,
    marginRight: theme.spacing.unit * 2,
    minHeight: 500,
    [theme.breakpoints.up(600 + theme.spacing.unit * 2 * 2)]: {
      width: 600,
      marginLeft: 'auto',
      marginRight: 'auto',
    },
  },
  paper: {
    marginTop: theme.spacing.unit * 3,
    marginBottom: theme.spacing.unit * 3,
    padding: theme.spacing.unit * 2,
    [theme.breakpoints.up(600 + theme.spacing.unit * 3 * 2)]: {
      marginTop: theme.spacing.unit * 6,
      marginBottom: theme.spacing.unit * 6,
      padding: theme.spacing.unit * 3,
    },
  }
});

class UserRegistration extends React.Component {

  state = {
    actionLoading: false,
    alertMessage: <Fragment/>
  };

  handleFormSubmit = () => {

    if (!this.validationForm.isFormValid(false)) {
      return;
    }
    const json = convertFormToJson(this.form);

    restInstance.post("userRegistration", json)
    .then((response) => {
      const successMessage = "User registration Success!";
      const successAlertMessage = (
          <AlertMessage variant="success" message={successMessage}/>);
      this.setState({
        actionLoading: false,
        alertMessage: successAlertMessage
      });
    }).catch((error) => {
          const responseErrorMessage = handleRemoteErrorResponse(error);
          const errorAlertMessage = (
              <AlertMessage variant="error" message={responseErrorMessage}/>);
          this.setState({
            actionLoading: false,
            alertMessage: errorAlertMessage
          });
        }
    );

    this.setState({
      actionLoading: true,
      alertMessage: <Fragment/>
    });

  };

  render() {

    const {classes} = this.props;
    const {actionLoading, alertMessage} = this.state;

    return (
        <React.Fragment>
          {alertMessage}
          <main className={classes.layout}>
            <Paper classes={classes.paper}>

              <ValidatorForm ref={(form) => {
                this.validationForm = form;
              }}
                             onSubmit={this.handleFormSubmit}
                             instantValidate={true}>
                <form ref={(form) => {
                  this.form = form;
                }}>
                  <Card>
                    <CardContent>
                      <Grid container spacing={24}>
                        <Grid item xs={12}>
                          <ValidateableTextField
                              id={"email"}
                              name={"email"}
                              label="Email"
                              fullWidth
                              validators={['required', 'isEmail']}
                          />
                        </Grid>
                        <Grid item xs={12}>
                          <SelectableList title={"User Roles"}
                                          resource={"userRoles"}
                                          property={"name"}
                                          field={"userRoles"}/>
                        </Grid>
                      </Grid>
                    </CardContent>
                    <CardActions style={{float: 'right'}}>
                      <Button color={"primary"} variant={"contained"}
                              disabled={actionLoading}
                              onClick={this.handleFormSubmit}>
                        {actionLoading ?
                            <CircularProgress size={20}/>
                            :
                            "Register"
                        }
                      </Button>
                    </CardActions>
                  </Card>
                </form>
              </ValidatorForm>
            </Paper>
          </main>
        </React.Fragment>
    );
  }

}

export default withRouter(withStyles(styles)(UserRegistration));
