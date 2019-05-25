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
import SimpleEditForm from "../../../component/SimpleDataTable/SimpleEditForm";
import ModifyCreatedVoucherAdmin from "./ModifyCreatedVoucherAdmin";
import restResourceInstance from "../../../axios/RestResourceInstance";

class CreatedVoucherSearchResultAdmin extends React.Component {

  state = {
    voucherDetails: null,
    moreLoading: false,
    loading: false,
    alertMessage: <Fragment/>,
    openEditDialog: false,
    selected: null,
    messages: []
  };

  pushMessage = (message) => {
    const messages = this.state.messages;
    messages.push(message);
    this.setState({messages: messages});
  };

  handleExpandClick = (voucherDetailsLink) => {
    restResourceInstance.get(voucherDetailsLink)
    .then((response) => {
      const data = response.data;

      const voucherDetails = {};
      voucherDetails["voucherTypeName"] = data.voucherType.name;
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

  handleEditDialogSave = (message) => {
    this.pushMessage(message);
    this.setState({
      openEditDialog: false
    });
    this.handleRefresh();
  };

  handleEditDialogLoadError = (message) => {
    this.pushMessage(message);
    this.setState({
      openEditDialog: false
    });
    this.handleRefresh();
  };

  handleEditDialogClose = () => {
    this.setState({
      openEditDialog: false
    });
  };

  handleOnModify = (selected) => {
    this.setState({
      selected: selected,
      openEditDialog: true
    })
  };

  handleRefresh = () => {
    const successMessage = "Save Created Voucher Success";
    const successAlert = (
        <AlertMessage variant="success" message={successMessage}/>);
    this.props.onAction(successAlert);
  };

  render = () => {

    const {voucherDetails, moreLoading, alertMessage, loading, selected} = this.state;
    const {data} = this.props;

    let progress = <Fragment/>;
    if (loading) {
      progress = <LinearProgress/>;
    }

    const expirationDate = convertJsonDateToStringDate(data.expirationDate);

    const selfLink = halfred.parse(data).link("self")["href"];
    const voucherDetailsLink = halfred.parse(data).link(
        "createdVoucherDetails")["href"];

    return (
        <Fragment>
          {alertMessage}
          {progress}
          <SimpleEditForm onClose={this.handleEditDialogClose}
                          open={this.state.openEditDialog}
                          selected={selected}
                          onSave={this.handleEditDialogSave}
                          onLoadError={this.handleEditDialogLoadError}
          >
            <ModifyCreatedVoucherAdmin/>
          </SimpleEditForm>
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
                      onClick={() => this.handleOnModify(selfLink)}>
                {loading ?
                    <CircularProgress size={20}/>
                    :
                    "Modify"
                }
              </Button>
            </CardActions>
          </Card>
        </Fragment>
    )
  }

}

export default CreatedVoucherSearchResultAdmin;
