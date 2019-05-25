import React, {Fragment} from "react";
import Card from "@material-ui/core/Card/Card";
import CardContent from "@material-ui/core/CardContent/CardContent";
import Grid from "@material-ui/core/Grid/Grid";
import Typography from "@material-ui/core/Typography/Typography";
import TextField from "@material-ui/core/TextField/TextField";
import * as halfred from "halfred";
import {convertJsonDateToStringDateTime} from "../../../util/ConvertUtils";

class RechargeHistorySearchResult extends React.Component {

  render = () => {

    const {data} = this.props;

    const rechargeHistories = halfred.parse(data).embeddedArray(
        "rechargeHistories");

    return (
        <Fragment>
          {rechargeHistories.map(row => {
            const id = halfred.parse(row).link("self")["href"];
            const eventTimestamp = convertJsonDateToStringDateTime(
                row.eventTimestamp);

            return (
                <Card>
                  <CardContent>
                    <Grid container justify="space-evenly"
                          spacing={16}>
                      <Grid item>
                        <Typography variant={"title"} color={"primary"}>
                          {row.msisdn + " (" + eventTimestamp + ")"}
                        </Typography>
                      </Grid>
                      <Grid item>
                        <Grid container spacing={24}>
                          <Grid item xs={12}>
                            <TextField
                                readonly={true}
                                id="msisdn"
                                name="msisdn"
                                label="Msisdn"
                                value={row.msisdn}
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
                          {row.serialNumber != null ?
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
                          {row.msisdnProviderId != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="msisdnProviderId"
                                    name="msisdnProviderId"
                                    label="Msisdn Provider Id"
                                    value={row.msisdnProviderId}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              : <Fragment/>
                          }
                          {row.ccid != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="ccid"
                                    name="ccid"
                                    label="CCID"
                                    value={row.ccid}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              : <Fragment/>
                          }
                          {row.amount != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="amount"
                                    name="amount"
                                    label="Amount"
                                    value={row.amount}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              : <Fragment/>
                          }
                          {row.currency != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="currency"
                                    name="currency"
                                    label="Currency"
                                    value={row.currency}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              : <Fragment/>
                          }
                          {row.providerIdVoucher != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="providerIdVoucher"
                                    name="providerIdVoucher"
                                    label="Provider Id Voucher"
                                    value={row.providerIdVoucher}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              : <Fragment/>
                          }
                          {row.balanceExtension != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="balanceExtension"
                                    name="balanceExtension"
                                    label="Balance Extension"
                                    value={row.balanceExtension}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              : <Fragment/>
                          }
                          {row.voucherType != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="voucherType"
                                    name="voucherType"
                                    label="Voucher Type"
                                    value={row.voucherType}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              : <Fragment/>
                          }
                          {row.errorCode != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="errorCode"
                                    name="errorCode"
                                    label="Error Code"
                                    value={row.errorCode}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              : <Fragment/>
                          }
                          {row.voucherExpiryDate != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="voucherExpiryDate"
                                    name="voucherExpiryDate"
                                    label="Voucher Expiry Date"
                                    value={row.voucherExpiryDate}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              : <Fragment/>
                          }
                          {row.accountId != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="accountId"
                                    name="accountId"
                                    label="Account Id"
                                    value={row.accountId}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              : <Fragment/>
                          }
                          {row.rechargeHistoryStatus != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="rechargeHistoryStatus"
                                    name="rechargeHistoryStatus"
                                    label="Recharge History Status"
                                    value={row.rechargeHistoryStatus}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              : <Fragment/>
                          }
                          {row.errorMessage != null ?
                              <Grid item xs={12}>
                                <TextField
                                    readonly={true}
                                    id="errorMessage"
                                    name="errorMessage"
                                    label="Error Message"
                                    value={row.errorMessage}
                                    InputLabelProps={{shrink: true}}
                                    fullWidth
                                />
                              </Grid>
                              : <Fragment/>
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

export default RechargeHistorySearchResult;