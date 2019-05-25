import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import CheckCircleIcon from '@material-ui/icons/CheckCircle';
import ErrorIcon from '@material-ui/icons/Error';
import InfoIcon from '@material-ui/icons/Info';
import CloseIcon from '@material-ui/icons/Close';
import green from '@material-ui/core/colors/green';
import amber from '@material-ui/core/colors/amber';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import SnackbarContent from '@material-ui/core/SnackbarContent';
import WarningIcon from '@material-ui/icons/Warning';
import {withStyles} from '@material-ui/core/styles';
import ClickAwayListener
  from "@material-ui/core/ClickAwayListener/ClickAwayListener";

const variantIcon = {
  success: CheckCircleIcon,
  warning: WarningIcon,
  error: ErrorIcon,
  info: InfoIcon,
};

const styles1 = theme => ({
  success: {
    backgroundColor: green[600],
  },
  error: {
    backgroundColor: theme.palette.error.dark,
  },
  info: {
    backgroundColor: theme.palette.primary.dark,
  },
  warning: {
    backgroundColor: amber[700],
  },
  icon: {
    fontSize: 20,
  },
  iconVariant: {
    opacity: 0.9,
    marginRight: theme.spacing.unit,
  },
  message: {
    display: 'flex',
    alignItems: 'center',
  },
});

class AlerMessage extends React.Component {

  state = {
    open: true,
  };

  handleClose = (event) => {
    this.setState({open: false});
  };

  render = () => {
    const {classes, className, message, variant, ...other} = this.props;
    const Icon = variantIcon[variant];

    return (
        <Snackbar anchorOrigin={{vertical: 'top', horizontal: 'center'}}
                  open={this.state.open}
                  ContentProps={{'aria-describedby': 'message-id',}}
                  autoHideDuration={10}
        >
          <SnackbarContent
              className={classNames(classes[variant], className)}
              aria-describedby="client-snackbar"
              message={
                <span id="client-snackbar" className={classes.message}>
                            <Icon className={classNames(classes.icon,
                                classes.iconVariant)}/>
                  {message}</span>
              }
              action={[
                <ClickAwayListener
                    key="clickAway"
                    onClickAway={this.handleClose}>
                  <IconButton
                      key="close"
                      aria-label="Close"
                      color="inherit"
                      className={classes.close}
                      onClick={this.handleClose}>
                    <CloseIcon className={classes.icon}/>
                  </IconButton>
                </ClickAwayListener>
              ]}
              {...other}
          />
        </Snackbar>
    );
  };
}

AlerMessage.propTypes = {
  classes: PropTypes.object.isRequired,
  className: PropTypes.string,
  message: PropTypes.node,
  onClose: PropTypes.func,
  variant: PropTypes.oneOf(['success', 'warning', 'error', 'info']).isRequired,
};

export default withStyles(styles1)(AlerMessage);
