import React, {Fragment} from "react";
import Button from "@material-ui/core/Button";
import ShowUserRolesDialog from "./ShowUserRolesDialog";
import Typography from "@material-ui/core/Typography";

class UserAccountActions extends React.Component {

  state = {
    open: false
  };

  handleOpen = () => {
    this.setState({
      open: true
    })
  };

  handleClose = () => {
    this.setState({
      open: false
    })
  };

  render = () => {

    const {data} = this.props;
    const {open} = this.state;

    let dialog = <Fragment/>;

    if (open) {
      dialog = <ShowUserRolesDialog data={data} open={open}
                                    onClose={this.handleClose}/>
    }

    return (
        <Fragment>
          {dialog}
          <Button
              onClick={this.handleOpen}
              fullWidth>
            <Typography
                variant={"caption"}>user roles</Typography>
          </Button>
        </Fragment>
    )
  }

}

export default UserAccountActions;
