import React, {Fragment} from "react";
import {handleRemoteErrorResponse} from "../../../util/ErrorHandlerUtils";
import restInstance from "../../../axios/RestInstance";
import Button from "@material-ui/core/Button";
import CircularProgress from "@material-ui/core/CircularProgress";
import AlertMessage from "../../../component/AlertMessage";

class ChangeUserAccountStatusActions extends React.Component {

  state = {
    actionLoading: false,
    alertMessage: <Fragment/>
  };

  handleOnAction = (email, newUserAccountStatus) => {

    const json = {"email": email, "newUserAccountStatus": newUserAccountStatus};

    restInstance.post("changeUserAccountStatus", json)
    .then((response) => {
      const successMessage = "Change User Account Status Success!";
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
    const {actionLoading} = this.state;

    return (
        <Fragment>
          {data.userAccountStatus !== "ACTIVE" ?
              (<Button color={"primary"} variant={"contained"}
                       disabled={actionLoading}
                       onClick={() => {
                         this.handleOnAction(data.email, "ACTIVE")
                       }}>
                {actionLoading ?
                    <CircularProgress size={20}/>
                    :
                    "Activate"
                }
              </Button>)
              :
              (<Fragment/>)
          }
          {data.userAccountStatus !== "INACTIVE" ?
              (<Button color={"primary"} variant={"contained"}
                       disabled={actionLoading}
                       onClick={() => {
                         this.handleOnAction(data.email, "INACTIVE")
                       }}>
                {actionLoading ?
                    <CircularProgress size={20}/>
                    :
                    "Deactivate"
                }
              </Button>)
              :
              (<Fragment/>)
          }
        </Fragment>
    )
  }

}

export default ChangeUserAccountStatusActions;
