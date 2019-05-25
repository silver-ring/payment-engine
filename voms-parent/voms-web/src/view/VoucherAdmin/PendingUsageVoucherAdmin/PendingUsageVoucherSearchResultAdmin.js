import React, {Fragment} from "react";
import AlertMessage from "../../../component/AlertMessage";
import Card from "@material-ui/core/Card/Card";
import CardContent from "@material-ui/core/CardContent/CardContent";
import Grid from "@material-ui/core/Grid/Grid";
import Typography from "@material-ui/core/Typography/Typography";
import TextField from "@material-ui/core/TextField/TextField";
import {
  convertJsonDateToStringDate,
  convertJsonDateToStringDateTime
} from "../../../util/ConvertUtils";
import CardActions from "@material-ui/core/CardActions/CardActions";
import Divider from "@material-ui/core/Divider/Divider";
import LinearProgress from "@material-ui/core/LinearProgress/LinearProgress";
import * as halfred from "halfred";
import Button from "@material-ui/core/Button/Button";
import CircularProgress
  from "@material-ui/core/CircularProgress/CircularProgress";
import {handleRemoteErrorResponse} from "../../../util/ErrorHandlerUtils";
import restResourceInstance from "../../../axios/RestResourceInstance";

class ActiveVoucherSearchResult extends React.Component {

  state = {
    voucherDetails: null,
    moreLoading: false,
    actionLoading: [],
    alertMessage: <Fragment/>
  };

  handleExpandClick = (voucherDetailsLink) => {
    restResourceInstance.get(voucherDetailsLink)
    .then((response) => {
      const data = response.data;

      const voucherDetails = {};
      voucherDetails["voucherTypeName"] = data.voucherType.name;
      voucherDetails["voucherProviderName"] = data.voucherProvider.name;
      voucherDetails["rechargeValueName"] = data.rechargeValue.name;

      this.setState({
        moreLoading: false,
        voucherDetails: voucherDetails
      });
    })
    .catch((error) => {
      const responseErrorMessage = handleRemoteErrorResponse(error);
      const errorAlertMessage = (
          <AlertMessage variant="error" message={responseErrorMessage}/>);

      this.setState({
        moreLoading: false,
        alertMessage: errorAlertMessage
      })
    });

    this.setState({
      moreLoading: true,
      alertMessage: <Fragment/>
    });
  };

  handleOnAction = (actionLink) => {
    restResourceInstance.post(actionLink)
    .then((response) => {
      const successMessage = "Save Pending Usage Voucher Success";
      const successAlert = (
          <AlertMessage variant="success" message={successMessage}/>);
      this.props.onAction(successAlert);
    }).catch((error) => {
          const responseErrorMessage = handleRemoteErrorResponse(error);
          const errorAlert = (
              <AlertMessage variant="error" message={responseErrorMessage}/>);
          this.setState({
            actionLoading: [],
            alertMessage: errorAlert
          });
        }
    );
    const actionLoading = this.state.actionLoading;
    actionLoading[actionLink] = true;
    this.setState({
      actionLoading: actionLoading,
      alertMessage: <Fragment/>
    });

  };

  render = () => {

    const {voucherDetails, moreLoading, alertMessage, actionLoading} = this.state;
    const {data} = this.props;

    const isLoading = actionLoading.length !== 0;

    const expirationDate = convertJsonDateToStringDate(data.expirationDate);
    const usageRequestTime = convertJsonDateToStringDateTime(
        data.usageRequestTime);

    const activeLink = halfred.parse(data).link("activate")["href"];
    const blockLink = halfred.parse(data).link("block")["href"];
    const useLink = halfred.parse(data).link("use")["href"];
    const voucherDetailsLink = halfred.parse(data).link(
        "pendingUsageVoucherDetails")["href"];

    const isActivationAction = actionLoading[activeLink] != null
        && actionLoading[activeLink] === true;
    const isBlockingAction = actionLoading[blockLink] != null
        && actionLoading[blockLink] === true;
    const isUseAction = actionLoading[useLink] != null && actionLoading[useLink]
        === true;

    let progress = <Fragment/>;
    if (isLoading) {
      progress = <LinearProgress/>
    }

    return (
        <Fragment>
          {alertMessage}
          {progress}
          <Card>
            <CardContent>
              <Grid container justify="space-evenly"
                    spacing={16}>
                <Grid item>
                  <Typography variant={"title"} color={"primary"}>
                    {data.serialNumber}
                  </Typography>
                </Grid>
                <Grid item>
                  <Grid container spacing={24}>
                    <Grid item xs={12}>
                      <Divider/>
                    </Grid>
                    <Grid item xs={12}>
                      <TextField
                          readonly={true}
                          id="serialNumber"
                          name="serialNumber"
                          label="Serial Number"
                          value={data.serialNumber}
                          InputLabelProps={{shrink: true}}
                          fullWidth
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <TextField
                          readonly={true}
                          id="voucherId"
                          name="voucherId"
                          label="Voucher Id"
                          value={data.voucherId}
                          InputLabelProps={{shrink: true}}
                          fullWidth
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <TextField
                          readonly={true}
                          id="expirationDate"
                          name="expirationDate"
                          label="Expiration Date"
                          value={expirationDate}
                          InputLabelProps={{shrink: true}}
                          fullWidth
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <TextField
                          readonly={true}
                          id="rechargePeriod"
                          name="rechargePeriod"
                          label="Recharge Period"
                          value={data.rechargePeriod}
                          InputLabelProps={{shrink: true}}
                          fullWidth
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <TextField
                          readonly={true}
                          id="transactionId"
                          name="transactionId"
                          label="Transaction Id"
                          value={data.transactionId}
                          InputLabelProps={{shrink: true}}
                          fullWidth
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <TextField
                          readonly={true}
                          id="msisdn"
                          name="msisdn"
                          label="Recharged Msisdn"
                          value={data.expirationDate}
                          InputLabelProps={{shrink: true}}
                          fullWidth
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <TextField
                          readonly={true}
                          id="ccid"
                          name="ccid"
                          label="Customer Care Id"
                          value={data.ccid}
                          InputLabelProps={{shrink: true}}
                          fullWidth
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <TextField
                          readonly={true}
                          id="finalRechargeValue"
                          name="finalRechargeValue"
                          label="Final Recharge Value"
                          value={data.finalRechargeValue}
                          InputLabelProps={{shrink: true}}
                          fullWidth
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <TextField
                          readonly={true}
                          id="usageRequestTime"
                          name="usageRequestTime"
                          label="Usage Request Time"
                          value={usageRequestTime}
                          InputLabelProps={{shrink: true}}
                          fullWidth
                      />
                    </Grid>
                    {voucherDetails == null ?
                        <Grid item xs={12}>
                          <Button
                              onClick={() => this.handleExpandClick(
                                  voucherDetailsLink)}
                              fullWidth>
                            {moreLoading ?
                                <CircularProgress size={20}/>
                                :
                                <Typography
                                    variant={"caption"}>more...</Typography>
                            }
                          </Button>
                        </Grid>
                        :
                        <Fragment>
                          <Grid item xs={12}>
                            <TextField
                                readonly={true}
                                id="rechargeValueName"
                                name="rechargeValueName"
                                label="Recharge Value Name"
                                value={voucherDetails.rechargeValueName}
                                InputLabelProps={{shrink: true}}
                                fullWidth
                            />
                          </Grid>
                          <Grid item xs={12}>
                            <TextField
                                readonly={true}
                                id="voucherTypeName"
                                name="voucherTypeName"
                                label="Voucher Type Name"
                                value={voucherDetails.voucherTypeName}
                                InputLabelProps={{shrink: true}}
                                fullWidth
                            />
                          </Grid>
                          <Grid item xs={12}>
                            <TextField
                                readonly={true}
                                id="voucherProviderName"
                                name="voucherProviderName"
                                label="Voucher Provider Name"
                                value={voucherDetails.voucherProviderName}
                                InputLabelProps={{shrink: true}}
                                fullWidth
                            />
                          </Grid>
                        </Fragment>
                    }
                  </Grid>
                </Grid>
              </Grid>
            </CardContent>
            <Divider/>
            <CardActions style={{float: 'right'}}>
              <CardActions style={{float: 'right'}}>
                <Button color={"primary"} variant={"contained"}
                        disabled={isLoading}
                        onClick={() => this.handleOnAction(activeLink)}>
                  {isActivationAction ?
                      <CircularProgress size={20}/>
                      :
                      "Activate"
                  }
                </Button>
                <Button color={"primary"} variant={"contained"}
                        disabled={isLoading}
                        onClick={() => this.handleOnAction(blockLink)}>
                  {isBlockingAction ?
                      <CircularProgress size={20}/>
                      :
                      "Block"
                  }
                </Button>
                <Button color={"primary"} variant={"contained"}
                        disabled={isLoading}
                        onClick={() => this.handleOnAction(useLink)}>
                  {isUseAction ?
                      <CircularProgress size={20}/>
                      :
                      "Use"
                  }
                </Button>
              </CardActions>
            </CardActions>
          </Card>
        </Fragment>
    )
  }

}

export default ActiveVoucherSearchResult;