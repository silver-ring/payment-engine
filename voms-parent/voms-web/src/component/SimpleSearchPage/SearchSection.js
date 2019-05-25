import React, {Fragment} from "react";
import ExpansionPanelSummary
  from "@material-ui/core/ExpansionPanelSummary/ExpansionPanelSummary";
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import Typography from "@material-ui/core/Typography/Typography";
import ExpansionPanelDetails
  from "@material-ui/core/ExpansionPanelDetails/ExpansionPanelDetails";
import {ValidatorForm} from "react-material-ui-form-validator";
import Divider from "@material-ui/core/Divider/Divider";
import ExpansionPanelActions
  from "@material-ui/core/ExpansionPanelActions/ExpansionPanelActions";
import Button from "@material-ui/core/Button/Button";
import ExpansionPanel from "@material-ui/core/ExpansionPanel/ExpansionPanel";
import CircularProgress
  from "@material-ui/core/CircularProgress/CircularProgress";
import Grid from "@material-ui/core/Grid/Grid";
import {
  convertFormToJson,
  convertJsonToQueryParam
} from "../../util/ConvertUtils";
import AlertMessage from "../AlertMessage";
import {withRouter} from "react-router";
import {getPageTitle} from "../../util/TitleUtils";
import {handleRemoteErrorResponse} from "../../util/ErrorHandlerUtils";
import restResourceInstance from "../../axios/RestResourceInstance";

class SearchSection extends React.Component {

  state = {
    loading: false,
    alertMessage: (<Fragment/>)
  };

  handleSearchFormSubmit = (e) => {
    e.preventDefault();

    if (!this.validationForm.isFormValid(false)) {
      return;
    }

    const {resource, search, onSearch} = this.props;

    const json = convertFormToJson(this.form);

    const searchParam = convertJsonToQueryParam(json);

    const url = resource + "/search/" + search + "?" + searchParam;

    restResourceInstance.get(url)
    .then((response) => {
      const data = response.data;
      this.setState({
        loading: false
      });
      onSearch(data);
    }).catch((error) => {
          const responseErrorMessage = handleRemoteErrorResponse(error);
          const errorAlertMessage = (
              <AlertMessage variant="error" message={responseErrorMessage}/>);
          this.setState({
            loading: false,
            alertMessage: errorAlertMessage
          })
        }
    );
    this.setState({
      loading: true,
      alertMessage: <Fragment/>
    })
  };

  render = () => {

    const {children} = this.props;
    const {alertMessage, loading} = this.state;
    const title = getPageTitle(this.props);

    return (
        <Fragment>
          {alertMessage}
          <ExpansionPanel defaultExpanded>
            <ExpansionPanelSummary expandIcon={<ExpandMoreIcon/>}>
              <Typography variant={"headline"}>Search For {title}</Typography>
            </ExpansionPanelSummary>
            <ExpansionPanelDetails>
              <ValidatorForm
                  ref={(form) => {
                    this.validationForm = form
                  }}
                  onSubmit={this.handleSearchFormSubmit}
                  instantValidate={true}
              >
                <form ref={(form) => {
                  this.form = form
                }}>
                  <Grid container spacing={24}>
                    {children}
                  </Grid>
                </form>
              </ValidatorForm>
            </ExpansionPanelDetails>
            <Divider/>
            <ExpansionPanelActions>
              <Button size="small" color="primary"
                      onClick={this.handleSearchFormSubmit}>
                {loading ? <CircularProgress size={15}/> : "Search"}
              </Button>
            </ExpansionPanelActions>
          </ExpansionPanel>
        </Fragment>
    );
  }

}

export default withRouter(SearchSection);
