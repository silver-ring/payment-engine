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
import Button from "@material-ui/core/Button/Button";
import CircularProgress
  from "@material-ui/core/CircularProgress/CircularProgress";
import {handleRemoteErrorResponse} from "../../../util/ErrorHandlerUtils";
import restResourceInstance from "../../../axios/RestResourceInstance";

class BatchOrderHistorySearchResult extends React.Component {

  state = {
    batchOrderHistoryDetails: [],
    moreLoading: [],
    alertMessage: <Fragment/>
  };

  handleExpandClick = (batchOrderHistoryId, batchOrderHistoryDetailsLink) => {
    restResourceInstance.get(batchOrderHistoryDetailsLink)
    .then((response) => {
      const data = response.data;

      const newBatchOrderHistoryDetails = {};

      if (data.voucherProvider != null) {
        newBatchOrderHistoryDetails["voucherProviderName"] = data.voucherProvider.name;
      }
      if (data.rechargeValue != null) {
        newBatchOrderHistoryDetails["rechargeValueName"] = data.rechargeValue.name;
      }
      if (data.voucherType != null) {
        newBatchOrderHistoryDetails["voucherTypeName"] = data.voucherType.name;
      }
      if (data.voucherPrinter != null) {
        newBatchOrderHistoryDetails["voucherPrinterName"] = data.voucherPrinter.name;
      }

      const batchOrderHistoryDetailsArr = this.state.batchOrderHistoryDetails;
      batchOrderHistoryDetailsArr[batchOrderHistoryId] = newBatchOrderHistoryDetails;

      const newMoreLoading = this.state.moreLoading;
      newMoreLoading[batchOrderHistoryId] = false;

      this.setState({
        moreLoading: newMoreLoading,
        batchOrderHistoryDetails: batchOrderHistoryDetailsArr
      });
    })
    .catch((error) => {
      const responseErrorMessage = handleRemoteErrorResponse(error);
      const errorAlertMessage = (
          <AlertMessage variant="error" message={responseErrorMessage}/>);

      const newMoreLoading = this.state.moreLoading;
      newMoreLoading[batchOrderHistoryId] = false;

      this.setState({
        moreLoading: newMoreLoading,
        alertMessage: errorAlertMessage
      })
    });

    const newMoreLoading = this.state.moreLoading;
    newMoreLoading[batchOrderHistoryId] = true;

    this.setState({
      moreLoading: newMoreLoading,
      alertMessage: <Fragment/>
    });
  };

  render = () => {

    const {data} = this.props;
    const {batchOrderHistoryDetails, moreLoading, alertMessage} = this.state;

    const batchOrderHistories = halfred.parse(data).embeddedArray(
        "batchOrderHistories");

    return (
        <Fragment>
          {alertMessage}
          {batchOrderHistories.map(row => {
            const id = halfred.parse(row).link("self")["href"];
            const batchOrderHistoryDetailsLink = halfred.parse(row).link(
                "batchOrderHistoryDetails")["href"];
            const executionTime = convertJsonDateToStringDateTime(
                row.executionTime);
            const eventTimestamp = convertJsonDateToStringDateTime(
                row.eventTimestamp);
            const expirationDate = convertJsonDateToStringDate(
                row.expirationDate);

            return (
                <Card>
                  <CardContent>
                    <Grid container justify="space-evenly"
                          spacing={16}>
                      <Grid item>
                        <Typography variant={"title"} color={"primary"}>
                          {row.batchOrderType + " (" + eventTimestamp + ")"}
                        </Typography>
                      </Grid>
                      <Grid item>
                        <Grid container spacing={24}>
                          <Grid item xs={12}>
                            <TextField
                                readonly={true}
                                id="tagId"
                                name="tagId"
                                label="Tag Id"
                                value={row.tagId}
                                InputLabelProps={{shrink: true}}
                                fullWidth
                            />
                          </Grid>
                          <Grid item xs={12}>
                            <TextField
                                readonly={true}
                                id="executionTime"
                                name="executionTime"
                                label="Execution Time"
                                value={executionTime}
                                InputLabelProps={{shrink: true}}
                                fullWidth
                            />
                          </Grid>
                          {row.startSerialNumber != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="startSerialNumber"
                                    name="startSerialNumber"
                                    label="Start Serial Number"
                                    value={row.startSerialNumber}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              :
                              <Fragment/>
                          }
                          {row.endSerialNumber != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="endSerialNumber"
                                    name="endSerialNumber"
                                    label="End Serial Number"
                                    value={row.endSerialNumber}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              :
                              <Fragment/>
                          }
                          {row.pendingRangeStartTime != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="pendingRangeStartTime"
                                    name="pendingRangeStartTime"
                                    label="Pending Range Start Time"
                                    value={row.pendingRangeStartTime}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              :
                              <Fragment/>
                          }
                          {row.pendingRangeEndTime != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="pendingRangeEndTime"
                                    name="pendingRangeEndTime"
                                    label="Pending Range End Time"
                                    value={row.pendingRangeEndTime}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              :
                              <Fragment/>
                          }
                          {row.numberOfVouchers != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="numberOfVouchers"
                                    name="numberOfVouchers"
                                    label="Number Of Vouchers"
                                    value={row.numberOfVouchers}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              :
                              <Fragment/>
                          }
                          {row.originalFileName != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="originalFileName"
                                    name="originalFileName"
                                    label="Original File Name"
                                    value={row.originalFileName}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              :
                              <Fragment/>
                          }
                          {row.productionFileName != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="productionFileName"
                                    name="productionFileName"
                                    label="Production File Name"
                                    value={row.productionFileName}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              :
                              <Fragment/>
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
                              :
                              <Fragment/>
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
                              :
                              <Fragment/>
                          }
                          {row.layoutId != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="layoutId"
                                    name="layoutId"
                                    label="Layout Id"
                                    value={row.layoutId}
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
                                id="batchOrderType"
                                name="batchOrderType"
                                label="Batch Order Type"
                                value={row.batchOrderType}
                                InputLabelProps={{shrink: true}}
                                fullWidth
                            />
                          </Grid>
                          {row.errorMessages != null ?
                              row.errorMessages.map((errorMessage, index) => (
                                  <Grid item xs={12}>
                                    <TextField
                                        readonly={true}
                                        id={"errorMessages[" + index + "]"}
                                        name={"errorMessages[" + index + "]"}
                                        label={"Error Message"}
                                        value={errorMessage}
                                        InputLabelProps={{shrink: true}}
                                        fullWidth
                                        multiline
                                        rowsMax="10"
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
                          {batchOrderHistoryDetails[id] == null ?
                              <Grid item xs={12}>
                                <Button
                                    onClick={() => this.handleExpandClick(id,
                                        batchOrderHistoryDetailsLink)}
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
                                {batchOrderHistoryDetails[id].rechargeValueName
                                != null ?
                                    <Grid item xs={12}>
                                      <TextField
                                          readonly={true}
                                          id="rechargeValueName"
                                          name="rechargeValueName"
                                          label="Recharge Value Name"
                                          value={batchOrderHistoryDetails[id].rechargeValueName}
                                          InputLabelProps={{shrink: true}}
                                          fullWidth
                                      />
                                    </Grid>
                                    :
                                    <Fragment/>
                                }
                                {batchOrderHistoryDetails[id].voucherTypeName
                                != null
                                    ?
                                    <Grid item xs={12}>
                                      <TextField
                                          readonly={true}
                                          id="voucherTypeName"
                                          name="voucherTypeName"
                                          label="Voucher Type Name"
                                          value={batchOrderHistoryDetails[id].voucherTypeName}
                                          InputLabelProps={{shrink: true}}
                                          fullWidth
                                      />
                                    </Grid>
                                    :
                                    <Fragment/>
                                }
                                {batchOrderHistoryDetails[id].voucherProviderName
                                != null ?
                                    <Grid item xs={12}>
                                      <TextField
                                          readonly={true}
                                          id="voucherProviderName"
                                          name="voucherProviderName"
                                          label="Voucher Provider Name"
                                          value={batchOrderHistoryDetails[id].voucherProviderName}
                                          InputLabelProps={{shrink: true}}
                                          fullWidth
                                      />
                                    </Grid>
                                    :
                                    <Fragment/>
                                }
                                {batchOrderHistoryDetails[id].voucherPrinterName
                                != null ?
                                    <Grid item xs={12}>
                                      <TextField
                                          readonly={true}
                                          id="voucherPrinterName"
                                          name="voucherPrinterName"
                                          label="Voucher Printer Name"
                                          value={batchOrderHistoryDetails[id].voucherPrinterName}
                                          InputLabelProps={{shrink: true}}
                                          fullWidth
                                      />
                                    </Grid>
                                    :
                                    <Fragment/>
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

export default BatchOrderHistorySearchResult;