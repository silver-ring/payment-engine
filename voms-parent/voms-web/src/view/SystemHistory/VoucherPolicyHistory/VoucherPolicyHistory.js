import React, {Fragment} from "react";
import * as halfred from "halfred";
import Card from "@material-ui/core/Card/Card";
import CardContent from "@material-ui/core/CardContent/CardContent";
import Grid from "@material-ui/core/Grid/Grid";
import TextField from "@material-ui/core/TextField/TextField";
import {handleRemoteErrorResponse} from "../../../util/ErrorHandlerUtils";
import AlertMessage from "../../../component/AlertMessage";
import LinearProgress from "@material-ui/core/LinearProgress/LinearProgress";
import Paper from "@material-ui/core/Paper/Paper";
import {withRouter} from "react-router";
import withStyles from "@material-ui/core/styles/withStyles";
import Typography from "@material-ui/core/Typography/Typography";
import {convertJsonDateToStringDateTime} from "../../../util/ConvertUtils";
import restResourceInstance from "../../../axios/RestResourceInstance";

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
      width: 800,
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

class VoucherPolicyHistory extends React.Component {

  state = {
    voucherPolicyHistories: [],
    pageLoading: false,
    alertMessage: <Fragment/>
  };

  componentWillMount = () => {

    const resource = "voucherPolicyHistories";
    const sortProperty = "eventTimestamp";
    const sortByParam = "sort=" + sortProperty + ",desc";

    const url = resource + "?" + sortByParam;

    restResourceInstance.get(url)
    .then((response) => {
      const data = response.data;

      const voucherPolicyHistories = halfred.parse(data).embeddedArray(
          "voucherPolicyHistories");

      this.setState({
        pageLoading: false,
        voucherPolicyHistories: voucherPolicyHistories
      });
    })
    .catch((error) => {
      const responseErrorMessage = handleRemoteErrorResponse(error);
      const errorAlertMessage = (
          <AlertMessage variant="error" message={responseErrorMessage}/>);

      this.setState({
        alertMessage: errorAlertMessage,
        pageLoading: false,
        voucherPolicyHistories: []
      })
    });
    this.setState({
      pageLoading: true,
      alertMessage: <Fragment/>
    });
  };

  render = () => {

    const {voucherPolicyHistories, alertMessage, pageLoading} = this.state;
    const {classes} = this.props;

    if (pageLoading) {
      return (<LinearProgress/>);
    }

    return (
        <Fragment>
          {alertMessage}
          <main className={classes.layout}>
            <Paper classes={classes.paper}>
              {voucherPolicyHistories.map((row) => {
                const eventTimestamp = convertJsonDateToStringDateTime(
                    row.eventTimestamp);
                return (
                    <Card>
                      <CardContent>
                        <Grid container justify="space-evenly"
                              spacing={16}>
                          <Grid item>
                            <Typography variant={"title"} color={"primary"}>
                              {row.voucherPolicyType + " (" + eventTimestamp
                              + ")"}
                            </Typography>
                          </Grid>
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
                              {row.errorMessages != null ?
                                  row.errorMessages.map(
                                      (errorMessage, index) => (
                                          <Grid item xs={12}>
                                            <TextField
                                                readonly={true}
                                                id={"errorMessages[" + index
                                                + "]"}
                                                name={"errorMessages[" + index
                                                + "]"}
                                                label={"Error Message"}
                                                value={errorMessage}
                                                InputLabelProps={{shrink: true}}
                                                fullWidth
                                            />
                                          </Grid>
                                      ))
                                  :
                                  <Fragment/>
                              }
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="batchJobHistoryStatus"
                                    name="batchJobHistoryStatus"
                                    label="Batch Job History Status"
                                    value={row.batchJobHistoryStatus}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                            </Grid>
                          </Grid>
                        </Grid>
                      </CardContent>
                    </Card>
                )
              })
              }
            </Paper>
          </main>
        </Fragment>
    )
  }

}

export default withRouter(withStyles(styles)(VoucherPolicyHistory));