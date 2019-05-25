import React, {Fragment} from "react";
import Button from "@material-ui/core/Button";
import UpdateUserRolesDialog from "./UpdateUserRolesDialog";

class UpdateUserRolesActions extends React.Component {

  state = {
    open: false
  };

  handleClose = () => {
    this.setState({open: false});
  };

  handleOpen = () => {
    this.setState({open: true});
  };

  render = () => {

    const {data} = this.props;
    const {open} = this.state;

    return (
        <Fragment>
          <UpdateUserRolesDialog
              open={open}
              onClose={this.handleClose}
              data={data}
          />
          <Button color={"primary"} variant={"contained"}
                  onClick={this.handleOpen}>
            {"Update User Roles"}
          </Button>
        </Fragment>
    )
  }
}

export default UpdateUserRolesActions;
