import React, {Fragment} from "react";
import * as halfred from "halfred";
import Card from "@material-ui/core/Card/Card";
import CardContent from "@material-ui/core/CardContent/CardContent";
import Grid from "@material-ui/core/Grid/Grid";
import TextField from "@material-ui/core/TextField/TextField";
import CardActions from "@material-ui/core/CardActions/CardActions";
import Button from "@material-ui/core/Button/Button";
import {handleRemoteErrorResponse} from "../util/ErrorHandlerUtils";
import AlertMessage from "../component/AlertMessage";
import LinearProgress from "@material-ui/core/LinearProgress/LinearProgress";
import Paper from "@material-ui/core/Paper/Paper";
import {withRouter} from "react-router";
import withStyles from "@material-ui/core/styles/withStyles";
import CircularProgress
  from "@material-ui/core/CircularProgress/CircularProgress";
import {ValidatorForm} from "react-material-ui-form-validator";
import {convertFormToJson} from "../util/ConvertUtils";
import ValidateableTextField from "../component/ValidateableTextField";
import restResourceInstance from "../axios/RestResourceInstance";

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

class ConfigParameterForm extends React.Component {

  state = {
    configParameter: [],
    pageLoading: false,
    actionLoading: [],
    alertMessage: <Fragment/>
  };

  componentWillMount = () => {

    const {resource} = this.props;

    restResourceInstance.get(resource)
    .then((response) => {
      const data = response.data;

      const configParameters = halfred.parse(data).embeddedArray(resource);

      this.setState({
        pageLoading: false,
        configParameter: configParameters
      });
    })
    .catch((error) => {
      const responseErrorMessage = handleRemoteErrorResponse(error);
      const errorAlertMessage = (
          <AlertMessage variant="error" message={responseErrorMessage}/>);

      this.setState({
        alertMessage: errorAlertMessage,
        pageLoading: false,
        configParameter: []
      })
    });
    this.setState({
      pageLoading: true,
      alertMessage: <Fragment/>
    });
  };

  handleFormSubmit = (index, resourceLink) => {
    const {configParameter, actionLoading} = this.state;

    if (!this.validationForm[index].isFormValid(false)) {
      return;
    }

    const json = convertFormToJson(this.form[index]);

    restResourceInstance.patch(resourceLink, json)
    .then((response) => {
      configParameter[index] = response.data;
      const responseSuccessMessage = "Voucher Config Parameter Saved Successfully!";
      const successAlertMessage = (
          <AlertMessage variant="success" message={responseSuccessMessage}/>);
      actionLoading[index] = false;
      this.setState({
        alertMessage: successAlertMessage,
        actionLoading: actionLoading,
        configParameter: configParameter
      });
    })
    .catch((error) => {
      const responseErrorMessage = handleRemoteErrorResponse(error);
      const errorAlertMessage = (
          <AlertMessage variant="error" message={responseErrorMessage}/>);
      actionLoading[index] = false;
      this.setState({
        alertMessage: errorAlertMessage,
        actionLoading: actionLoading
      })
    });
    actionLoading[index] = true;
    this.setState({
      actionLoading: actionLoading,
      alertMessage: <Fragment/>
    });
  };

  render = () => {

    const {configParameter, alertMessage, pageLoading, actionLoading} = this.state;
    const {classes} = this.props;

    if (pageLoading) {
      return (<LinearProgress/>);
    }

    return (
        <Fragment>
          {alertMessage}
          <main className={classes.layout}>
            <Paper classes={classes.paper}>
              {configParameter.map((row, index) => {

                const resourceLink = halfred.parse(row).link("self")["href"];

                const valueType = row.valueType;

                const validators = [];

                if (!row.optional) {
                  validators.push('required');
                }

                if (valueType == 'NUMBER') {
                  validators.push('isNumber');
                }

                if (valueType == 'PATH') {
                  validators.push('isValidPath');
                }

                if (valueType == 'STRING') {
                  validators.push('isString');
                }

                return (
                    <ValidatorForm
                        ref={(form) => {
                          let newValidationForm = this.validationForm;
                          if (newValidationForm == null) {
                            newValidationForm = [];
                          }
                          newValidationForm[index] = form;
                          this.validationForm = newValidationForm;
                        }}
                        onSubmit={() => this.handleFormSubmit(index,
                            resourceLink)}
                        instantValidate={true}
                    >
                      <form ref={(form) => {
                        let newForm = this.form;
                        if (newForm == null) {
                          newForm = [];
                        }
                        newForm[index] = form;
                        this.form = newForm;
                      }}>
                        <Card>
                          <CardContent>
                            <Grid container justify="space-evenly"
                                  spacing={16}>
                              <Grid item>
                                <Grid container spacing={24}>
                                  <Grid item xs={12}>
                                    <TextField
                                        readonly={true}
                                        label="Parameter"
                                        value={row.parameter}
                                        InputLabelProps={{shrink: true}}
                                        fullWidth
                                    />
                                  </Grid>
                                  <Grid item xs={12}>
                                    <TextField
                                        readonly={true}
                                        label="Description"
                                        value={row.description}
                                        InputLabelProps={{shrink: true}}
                                        fullWidth
                                    />
                                  </Grid>
                                  <Grid item xs={12}>
                                    <TextField
                                        readonly={true}
                                        label="Default Value"
                                        value={row.defaultValue}
                                        InputLabelProps={{shrink: true}}
                                        fullWidth
                                    />
                                  </Grid>
                                  <Grid item xs={12}>
                                    <ValidateableTextField
                                        id="value"
                                        name="value"
                                        label="Value"
                                        defaultValue={row.value}
                                        fullWidth
                                        validators={validators}
                                    />
                                  </Grid>
                                </Grid>
                              </Grid>
                            </Grid>
                          </CardContent>
                          <CardActions style={{float: 'right'}}>
                            <Button color={"primary"} variant={"contained"}
                                    disabled={actionLoading[index]}
                                    onClick={() => this.handleFormSubmit(index,
                                        resourceLink)}>
                              {actionLoading[index] ?
                                  <CircularProgress size={20}/>
                                  :
                                  "Save"
                              }
                            </Button>
                          </CardActions>
                        </Card>
                      </form>
                    </ValidatorForm>
                )
              })
              }
            </Paper>
          </main>
        </Fragment>
    )
  }

}

export default withRouter(withStyles(styles)(ConfigParameterForm));