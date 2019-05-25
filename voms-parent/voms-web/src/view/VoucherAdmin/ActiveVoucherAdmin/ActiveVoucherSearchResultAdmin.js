import React, {Fragment} from "react";
import AlertMessage from "../../../component/AlertMessage";
import Card from "@material-ui/core/Card/Card";
import CardContent from "@material-ui/core/CardContent/CardContent";
import Grid from "@material-ui/core/Grid/Grid";
import Typography from "@material-ui/core/Typography/Typography";
import TextField from "@material-ui/core/TextField/TextField";
import * as halfred from "halfred";
import {convertJsonDateToStringDate} from "../../../util/ConvertUtils";
import Button from '@material-ui/core/Button';
import CircularProgress from '@material-ui/core/CircularProgress';
import CardActions from "@material-ui/core/CardActions/CardActions";
import Divider from "@material-ui/core/Divider/Divider";
import {handleRemoteErrorResponse} from "../../../util/ErrorHandlerUtils";
import LinearProgress from "@material-ui/core/LinearProgress/LinearProgress";
import restResourceInstance from "../../../axios/RestResourceInstance";

class ActiveVoucherSearchResultAdmin extends React.Component {

  state = {
    voucherDetails: null,
    moreLoading: false,
    loading: false,
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
      const successMessage = "Save Active Voucher Success";
      const successAlert = (
          <AlertMessage variant="success" message={successMessage}/>);
      this.props.onAction(successAlert);
    }).catch((error) => {
          const responseErrorMessage = handleRemoteErrorResponse(error);
          const errorAlert = (
              <AlertMessage variant="error" message={responseErrorMessage}/>);
          this.setState({
            loading: false,
            alertMessage: errorAlert
          });
        }
    );
    this.setState({
      loading: true,
      alertMessage: <Fragment/>
    });

  };

  render = () => {

    const {voucherDetails, moreLoading, alertMessage, loading} = this.state;
    const {data} = this.props;

    let progress = <Fragment/>;
    if (loading) {
      progress = <LinearProgress/>
    }

    const expirationDate = convertJsonDateToStringDate(data.expirationDate);

    const blockLink = halfred.parse(data).link("block")["href"];
    const voucherDetailsLink = halfred.parse(data).link(
        "activeVoucherDetails")["href"];

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
              <Button color={"primary"} variant={"contained"}
                      disabled={loading}
                      onClick={() => this.handleOnAction(blockLink)}>
                {loading ?
                    <CircularProgress size={20}/>
                    :
                    "Block"
                }
              </Button>
            </CardActions>
          </Card>
        </Fragment>
    )
  }

}

export default ActiveVoucherSearchResultAdmin;