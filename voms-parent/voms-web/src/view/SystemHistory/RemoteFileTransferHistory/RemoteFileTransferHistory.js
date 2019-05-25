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

class RemoteFileTransferHistory extends React.Component {

  state = {
    remoteFileTransferHistories: [],
    pageLoading: false,
    alertMessage: <Fragment/>
  };

  componentWillMount = () => {

    const resource = "remoteFileTransferHistories";
    const sortProperty = "eventTimestamp";
    const sortByParam = "sort=" + sortProperty + ",desc";

    const url = resource + "?" + sortByParam;

    restResourceInstance.get(url)
    .then((response) => {
      const data = response.data;

      const remoteFileTransferHistories = halfred.parse(data).embeddedArray(
          "remoteFileTransferHistories");

      this.setState({
        pageLoading: false,
        remoteFileTransferHistories: remoteFileTransferHistories
      });
    })
    .catch((error) => {
      const responseErrorMessage = handleRemoteErrorResponse(error);
      const errorAlertMessage = (
          <AlertMessage variant="error" message={responseErrorMessage}/>);

      this.setState({
        alertMessage: errorAlertMessage,
        pageLoading: false,
        remoteFileTransferHistories: []
      })
    });
    this.setState({
      pageLoading: true,
      alertMessage: <Fragment/>
    });
  };

  render = () => {

    const {remoteFileTransferHistories, alertMessage, pageLoading} = this.state;
    const {classes} = this.props;

    if (pageLoading) {
      return (<LinearProgress/>);
    }

    return (
        <Fragment>
          {alertMessage}
          <main className={classes.layout}>
            <Paper classes={classes.paper}>
              {remoteFileTransferHistories.map((row) => {
                const eventTimestamp = convertJsonDateToStringDateTime(
                    row.eventTimestamp);
                return (
                    <Card>
                      <CardContent>
                        <Grid container justify="space-evenly"
                              spacing={16}>
                          <Grid item>
                            <Typography variant={"title"} color={"primary"}>
                              {"(" + eventTimestamp + ")"}
                            </Typography>
                          </Grid>
                          <Grid item>
                            <Grid container spacing={24}>
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    label="Remote Transfer Url"
                                    value={row.remoteTransferUrl}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    label="Local File Uri"
                                    value={row.localFileUri}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              {row.errorMessage != null ?
                                  <Grid item xs={12}>
                                    <TextField
                                        readonly={true}
                                        id={"errorMessage"}
                                        name={"errorMessage"}
                                        label={"Error Message"}
                                        value={row.errorMessage}
                                        InputLabelProps={{shrink: true}}
                                        fullWidth
                                    />
                                  </Grid>
                                  :
                                  <Fragment/>
                              }
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="remoteFileTransferStatus"
                                    name="remoteFileTransferStatus"
                                    label="Remote File Transfer Status"
                                    value={row.remoteFileTransferStatus}
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

export default withRouter(withStyles(styles)(RemoteFileTransferHistory));
