import React from 'react';
import Button from '@material-ui/core/Button';
import DialogContentText
  from "@material-ui/core/DialogContentText/DialogContentText";
import DialogContent from "@material-ui/core/DialogContent/DialogContent";
import DialogTitle from "@material-ui/core/DialogTitle/DialogTitle";
import Dialog from "@material-ui/core/Dialog/Dialog";
import DialogActions from "@material-ui/core/DialogActions/DialogActions";
import Avatar from "@material-ui/core/Avatar/Avatar";

class ActionDialog extends React.Component {

  render = () => {
    const {open, icon, message, onCancel, onOk} = this.props;

    return (
        <Dialog
            open={open}
            onClose={onCancel}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
          <DialogTitle id="alert-dialog-title" disableTypography>
            <Avatar>
              {icon}
            </Avatar>
          </DialogTitle>
          <DialogContent>
            <DialogContentText>
              {message}
            </DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button onClick={onCancel} color="inherit" autoFocus>
              Cancel
            </Button>
            <Button onClick={onOk} color="inherit">
              Ok
            </Button>
          </DialogActions>
        </Dialog>
    )
  }
}

export default ActionDialog;
