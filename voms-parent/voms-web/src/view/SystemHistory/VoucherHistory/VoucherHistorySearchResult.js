import React, {Fragment} from "react";
import AlertMessage from "../../../component/AlertMessage";
import Card from "@material-ui/core/Card/Card";
import CardContent from "@material-ui/core/CardContent/CardContent";
import Grid from "@material-ui/core/Grid/Grid";
import Typography from "@material-ui/core/Typography/Typography";
import TextField from "@material-ui/core/TextField/TextField";
import * as halfred from "halfred";
import {
  convertJsonDateToStringDate,
  convertJsonDateToStringDateTime
} from "../../../util/ConvertUtils";
import Button from '@material-ui/core/Button';
import CircularProgress from '@material-ui/core/CircularProgress';
import {handleRemoteErrorResponse} from "../../../util/ErrorHandlerUtils";
import restResourceInstance from "../../../axios/RestResourceInstance";

class VoucherHistorySearchResult extends React.Component {

  state = {
    voucherHistoryDetails: [],
    moreLoading: [],
    alertMessage: <Fragment/>
  };

  handleExpandClick = (voucherHistoryId, voucherHistoryDetailsLink) => {
    restResourceInstance.get(voucherHistoryDetailsLink)
    .then((response) => {
      const data = response.data;

      const newVoucherHistoryDetails = {};

      if (data.voucherType != null) {
        newVoucherHistoryDetails["voucherTypeName"] = data.voucherType.name;
      }
      if (data.voucherProvider != null) {
        newVoucherHistoryDetails["voucherProviderName"] = data.voucherProvider.name;
      }
      if (data.rechargeValue != null) {
        newVoucherHistoryDetails["rechargeValueName"] = data.rechargeValue.name;
      }

      const voucherHistoryDetailsArr = this.state.voucherHistoryDetails;
      voucherHistoryDetailsArr[voucherHistoryId] = newVoucherHistoryDetails;

      const newMoreLoading = this.state.moreLoading;
      newMoreLoading[voucherHistoryId] = false;

      this.setState({
        moreLoading: newMoreLoading,
        voucherHistoryDetails: voucherHistoryDetailsArr
      });
    })
    .catch((error) => {
      const responseErrorMessage = handleRemoteErrorResponse(error);
      const errorAlertMessage = (
          <AlertMessage variant="error" message={responseErrorMessage}/>);

      const newMoreLoading = this.state.moreLoading;
      newMoreLoading[voucherHistoryId] = false;

      this.setState({
        moreLoading: newMoreLoading,
        alertMessage: errorAlertMessage
      })
    });

    const newMoreLoading = this.state.moreLoading;
    newMoreLoading[voucherHistoryId] = true;

    this.setState({
      moreLoading: newMoreLoading,
      alertMessage: <Fragment/>
    });
  };

  render = () => {

    const {data} = this.props;
    const {voucherHistoryDetails, moreLoading, alertMessage} = this.state;

    const voucherHistories = halfred.parse(data).embeddedArray(
        "voucherHistories");

    return (
        <Fragment>
          {alertMessage}
          {voucherHistories.map(row => {
            const id = halfred.parse(row).link("self")["href"];
            const voucherHistoryDetailsLink = halfred.parse(row).link(
                "voucherHistoryDetails")["href"];
            const eventTimestamp = convertJsonDateToStringDateTime(
                row.eventTimestamp);
            const expirationDate = convertJsonDateToStringDate(
                row.expirationDate);
            const usageRequestTime = convertJsonDateToStringDateTime(
                row.usageRequestTime);

            return (
                <Card>
                  <CardContent>
                    <Grid container justify="space-evenly"
                          spacing={16}>
                      <Grid item>
                        <Typography variant={"title"} color={"primary"}>
                          {row.voucherStatus + " (" + eventTimestamp + ")"}
                        </Typography>
                      </Grid>
                      <Grid item>
                        <Grid container spacing={24}>
                          <Grid item xs={12}>
                            <TextField
                                readonly={true}
                                id="serialNumber"
                                name="serialNumber"
                                label="Serial Number"
                                value={row.serialNumber}
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
                                value={row.voucherId}
                                InputLabelProps={{shrink: true}}
                                fullWidth
                            />
                          </Grid>
                          {row.ccid != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="ccid"
                                    name="ccid"
                                    label="Customer Care Id"
                                    value={row.ccid}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              : <Fragment/>
                          }
                          {expirationDate != null ?
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
                              : <Fragment/>
                          }
                          {row.finalRechargeValue != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="finalRechargeValue"
                                    name="finalRechargeValue"
                                    label="Final Recharge Value"
                                    value={row.finalRechargeValue}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              : <Fragment/>
                          }
                          {row.msisdn != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="msisdn"
                                    name="msisdn"
                                    label="Recharged Msisdn"
                                    value={row.msisdn}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              : <Fragment/>
                          }
                          {row.rechargePeriod != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="rechargePeriod"
                                    name="rechargePeriod"
                                    label="Recharge Period"
                                    value={row.rechargePeriod}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              : <Fragment/>
                          }
                          {row.transactionId != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="transactionId"
                                    name="transactionId"
                                    label="Transaction Id"
                                    value={row.transactionId}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              : <Fragment/>
                          }
                          {usageRequestTime != null ?
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
                              : <Fragment/>
                          }
                          <Grid item xs={12}>
                            <TextField
                                readonly={true}
                                id="voucherStatus"
                                name="voucherStatus"
                                label="Voucher Status"
                                value={row.voucherStatus}
                                InputLabelProps={{shrink: true}}
                                fullWidth
                            />
                          </Grid>

                          {voucherHistoryDetails[id] == null ?
                              <Grid item xs={12}>
                                <Button
                                    onClick={() => this.handleExpandClick(id,
                                        voucherHistoryDetailsLink)}
                                    fullWidth>
                                  {moreLoading[id] ?
                                      <CircularProgress size={20}/>
                                      :
                                      <Typography
                                          variant={"caption"}>more...</Typography>
                                  }
                                </Button>
                              </Grid>
                              :
                              <Fragment>
                                {voucherHistoryDetails[id].rechargeValueName
                                != null ?
                                    <Grid item xs={12}>
                                      <TextField
                                          readonly={true}
                                          id="rechargeValueName"
                                          name="rechargeValueName"
                                          label="Recharge Value Name"
                                          value={voucherHistoryDetails[id].rechargeValueName}
                                          InputLabelProps={{shrink: true}}
                                          fullWidth
                                      />
                                    </Grid>
                                    : <Fragment/>
                                }
                                {voucherHistoryDetails[id].voucherTypeName
                                != null ?
                                    <Grid item xs={12}>
                                      <TextField
                                          readonly={true}
                                          id="voucherTypeName"
                                          name="voucherTypeName"
                                          label="Voucher Type Name"
                                          value={voucherHistoryDetails[id].voucherTypeName}
                                          InputLabelProps={{shrink: true}}
                                          fullWidth
                                      />
                                    </Grid>
                                    : <Fragment/>
                                }
                                {voucherHistoryDetails[id].voucherProviderName
                                != null ?
                                    <Grid item xs={12}>
                                      <TextField
                                          readonly={true}
                                          id="voucherProviderName"
                                          name="voucherProviderName"
                                          label="Voucher Provider Name"
                                          value={voucherHistoryDetails[id].voucherProviderName}
                                          InputLabelProps={{shrink: true}}
                                          fullWidth
                                      />
                                    </Grid>
                                    : <Fragment/>
                                }
                              </Fragment>
                          }
                        </Grid>
                      </Grid>
                    </Grid>
                  </CardContent>
                </Card>
            )
          })
          }
        </Fragment>
    )
  }

}

export default VoucherHistorySearchResult;