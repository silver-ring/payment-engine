import React, {Fragment} from "react";
import * as halfred from "halfred";
import Card from "@material-ui/core/Card/Card";
import CardContent from "@material-ui/core/CardContent/CardContent";
import Grid from "@material-ui/core/Grid/Grid";
import TextField from "@material-ui/core/TextField/TextField";
import CardActions from "@material-ui/core/CardActions/CardActions";
import Button from "@material-ui/core/Button/Button";
import {handleRemoteErrorResponse} from "../../../util/ErrorHandlerUtils";
import AlertMessage from "../../../component/AlertMessage";
import LinearProgress from "@material-ui/core/LinearProgress/LinearProgress";
import Paper from "@material-ui/core/Paper/Paper";
import {withRouter} from "react-router";
import withStyles from "@material-ui/core/styles/withStyles";
import CircularProgress
  from "@material-ui/core/CircularProgress/CircularProgress";
import {TextValidator, ValidatorForm} from "react-material-ui-form-validator";
import {convertFormToJson} from "../../../util/ConvertUtils";
import Popover from "@material-ui/core/Popover";
import ClickAwayListener from "@material-ui/core/ClickAwayListener";
import restResourceInstance from "../../../axios/RestResourceInstance";
import restInstance from "../../../axios/RestInstance";

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

class VoucherPolicySchedule extends React.Component {

  state = {
    voucherPolicySchedules: [],
    pageLoading: false,
    actionLoading: [],
    open: [],
    modifyLoading: [],
    cronExpressionValues: [],
    cronExpressionTempValues: [],
    messages: []
  };

  componentWillMount = () => {
    restResourceInstance.get("voucherPolicySchedules")
    .then((response) => {
          const data = response.data;

          const voucherPolicySchedules = halfred.parse(data).embeddedArray(
              "voucherPolicySchedules");

          const values = [];
          const tempValues = [];

          voucherPolicySchedules.map((row, index) => {
            values[index] = row.cronExpression;
            tempValues[index] = row.cronExpression;
          });

          this.setState({
            pageLoading: false,
            cronExpressionValues: values,
            cronExpressionTempValues: tempValues,
            voucherPolicySchedules: voucherPolicySchedules
          });
        }
    )
    .catch((error) => {
      const responseErrorMessage = handleRemoteErrorResponse(error);
      const errorAlertMessage = (
          <AlertMessage variant="error" message={responseErrorMessage}/>);
      this.pushMessage(errorAlertMessage);
      this.setState({
        pageLoading: false,
        voucherPolicySchedules: []
      })
    });
    this.setState({
      pageLoading: true
    });
  };

  handleModifyOpen = (index) => {

    const open = this.state.open;
    open[index] = true;

    this.setState({
      open: open
    })

  };

  handleModifyClose = (index) => {
    const open = this.state.open;
    open[index] = false;

    const tempValues = this.state.cronExpressionTempValues;
    tempValues[index] = this.state.cronExpressionValues[index];

    this.setState({
      open: open,
      cronExpressionTempValues: tempValues
    })
  };

  handleChange = (index, value) => {
    const tempValues = this.state.cronExpressionTempValues;
    tempValues[index] = value;
    this.setState({
      cronExpressionTempValues: tempValues
    })
  };

  handleValidateSubmit = (index) => {

    const tempValue = this.state.cronExpressionTempValues[index];
    const values = this.state.cronExpressionValues;
    const open = this.state.open;
    const modifyLoading = this.state.modifyLoading;

    if (!this.validationForm[index].isFormValid(false)) {
      return;
    }

    const json = {"cronExpression": tempValue};

    restInstance.post("validateCronExpression", json)
    .then(() => {
      values[index] = tempValue;
      open[index] = false;
      modifyLoading[index] = false;
      this.setState({
        open: open,
        modifyLoading: modifyLoading,
        cronExpressionValues: values
      })
    })
    .catch((error) => {
      const responseErrorMessage = handleRemoteErrorResponse(error);
      const errorAlertMessage = (
          <AlertMessage variant="error" message={responseErrorMessage}/>);
      this.pushMessage(errorAlertMessage);
      modifyLoading[index] = false;
      this.setState({
        modifyLoading: modifyLoading
      })
    });

    modifyLoading[index] = true;
    this.setState({
      modifyLoading: modifyLoading
    })
  };

  handleFormSubmit = (index, resourceLink) => {
    const {voucherPolicySchedules, actionLoading} = this.state;

    if (!this.validationForm[index].isFormValid(false)) {
      return;
    }

    const json = convertFormToJson(this.form[index]);

    restResourceInstance.patch(resourceLink, json)
    .then((response) => {
      voucherPolicySchedules[index] = response.data;
      const responseSuccessMessage = "Voucher Policy Schedule Saved Successfully!";
      const successAlertMessage = (
          <AlertMessage variant="success" message={responseSuccessMessage}/>);
      actionLoading[index] = false;
      this.pushMessage(successAlertMessage);
      this.setState({
        actionLoading: actionLoading,
        voucherPolicySchedules: voucherPolicySchedules
      });
    })
    .catch((error) => {
      const responseErrorMessage = handleRemoteErrorResponse(error);
      const errorAlertMessage = (
          <AlertMessage variant="error" message={responseErrorMessage}/>);
      actionLoading[index] = false;
      this.pushMessage(errorAlertMessage);
      this.setState({
        actionLoading: actionLoading
      })
    });
    actionLoading[index] = true;
    this.setState({
      actionLoading: actionLoading
    });
  };

  pushMessage = (message) => {
    const messages = this.state.messages;
    messages.push(message);
    this.setState({messages: messages});
  };

  render = () => {

    const {
      voucherPolicySchedules, pageLoading, actionLoading, open,
      modifyLoading, cronExpressionValues, cronExpressionTempValues, messages
    } = this.state;

    const {classes} = this.props;

    if (pageLoading) {
      return (<LinearProgress/>);
    }

    return (
        <Fragment>
          {messages.map(message => (message))}
          <main className={classes.layout}>
            <Paper classes={classes.paper}>
              {voucherPolicySchedules.map((row, index) => {

                const resourceLink = halfred.parse(row).link("self")["href"];

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
                                        label="Voucher Policy Type"
                                        value={row.voucherPolicyType}
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
                                        label="Default Cron Expression"
                                        value={row.defaultCronExpression}
                                        InputLabelProps={{shrink: true}}
                                        fullWidth
                                    />
                                  </Grid>
                                  <Grid item xs={12}>
                                    <TextField
                                        id="cronExpression"
                                        name="cronExpression"
                                        readonly={true}
                                        label="Cron Expression"
                                        value={cronExpressionValues[index]}
                                        InputLabelProps={{shrink: true}}
                                        fullWidth
                                    />
                                    <Button color={"primary"}
                                            variant={"contained"}
                                            onClick={() => this.handleModifyOpen(
                                                index)}>
                                      {"Modify"}
                                    </Button>
                                  </Grid>
                                  <Popover id="render-props-popover"
                                           open={open[index]}
                                           anchorOrigin={{
                                             vertical: 'center',
                                             horizontal: 'center',
                                           }}
                                           transformOrigin={{
                                             vertical: 'center',
                                             horizontal: 'center',
                                           }}
                                  >
                                    <ClickAwayListener
                                        onClickAway={() => this.handleModifyClose(
                                            index)}>
                                      <Card>
                                        <CardContent>
                                          <Grid container spacing={24}>
                                            <Grid item xs={12}>
                                              <TextField
                                                  readonly={true}
                                                  label="Voucher Policy Type"
                                                  value={row.voucherPolicyType}
                                                  InputLabelProps={{shrink: true}}
                                                  fullWidth
                                              />
                                            </Grid>
                                            <Grid item xs={12}>
                                              <TextValidator
                                                  id="newCronExpression"
                                                  name="newCronExpression"
                                                  label="New Cron Expression"
                                                  value={cronExpressionTempValues[index]}
                                                  validators={["required"]}
                                                  errorMessages={['this field is required']}
                                                  fullWidth
                                                  onChange={e => {
                                                    this.handleChange(index,
                                                        e.target.value)
                                                  }}
                                              />
                                            </Grid>
                                          </Grid>
                                        </CardContent>
                                        <CardActions style={{float: 'right'}}>
                                          <Button color={"primary"}
                                                  variant={"contained"}
                                                  disabled={modifyLoading[index]}
                                                  onClick={() => this.handleModifyClose(
                                                      index)}>
                                            {"Cancel"}
                                          </Button>
                                          <Button color={"primary"}
                                                  variant={"contained"}
                                                  disabled={modifyLoading[index]}
                                                  onClick={() => this.handleValidateSubmit(
                                                      index)}>
                                            {modifyLoading[index] ?
                                                <CircularProgress size={20}/>
                                                :
                                                "Validate"
                                            }
                                          </Button>
                                        </CardActions>
                                      </Card>
                                    </ClickAwayListener>
                                  </Popover>
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
                                  "Reschedule"
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

export default withRouter(withStyles(styles)(VoucherPolicySchedule));