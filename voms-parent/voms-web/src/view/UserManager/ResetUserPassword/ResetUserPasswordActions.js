import React, {Fragment} from "react";
import {handleRemoteErrorResponse} from "../../../util/ErrorHandlerUtils";
import restInstance from "../../../axios/RestInstance";
import Button from "@material-ui/core/Button";
import CircularProgress from "@material-ui/core/CircularProgress";
import AlertMessage from "../../../component/AlertMessage";

class ResetUserPasswordActions extends React.Component {

  state = {
    actionLoading: false,
    alertMessage: <Fragment/>
  };

  handleOnAction = (email) => {

    const json = {"email": email};

    restInstance.post("resetUserPassword", json)
    .then((response) => {
      const successMessage = "Rest Password Success!";
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

    const {data} = this.props;
    const {actionLoading} = this.props;

    return (
        <Fragment>
          <Button color={"primary"} variant={"contained"}
                  disabled={actionLoading}
                  onClick={() => {
                    this.handleOnAction(data.email)
                  }}>
            {actionLoading ?
                <CircularProgress size={20}/>
                :
                "Send Rest Link"
            }
          </Button>
        </Fragment>
    )
  }

}

export default ResetUserPasswordActions;
