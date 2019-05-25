import React, {Fragment} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import Dialog from '@material-ui/core/Dialog';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import CloseIcon from '@material-ui/icons/Close';
import Slide from '@material-ui/core/Slide';
import Paper from "@material-ui/core/Paper/Paper";

const styles = theme => ({
  appBar: {
    position: 'relative',
  },
  flex: {
    flex: 1,
  },
  layout: {
    width: 'auto',
    marginLeft: theme.spacing.unit * 2,
    marginRight: theme.spacing.unit * 2,
    [theme.breakpoints.up(600 + theme.spacing.unit * 2 * 2)]: {
      width: 600,
      marginLeft: 'auto',
      marginRight: 'auto',
    },
  },
  paper: {
    marginTop: theme.spacing.unit * 3,
    marginBottom: theme.spacing.unit * 3,
    padding: theme.spacing.unit * 2,
    [theme.breakpoints.up(600 + theme.spacing.unit * 3 * 2)]: {
      marginTop: theme.spacing.unit * 6,
      marginBottom: theme.spacing.unit * 6,
      padding: theme.spacing.unit * 3,
    },
  },
  buttons: {
    display: 'flex',
    justifyContent: 'flex-end',
  },
  button: {
    marginTop: theme.spacing.unit * 3,
    marginLeft: theme.spacing.unit,
  },
  appBarSpacer: theme.mixins.toolbar
});

function Transition(props) {
  return <Slide direction="up" {...props} />
}

class FullScreenForm extends React.Component {

  handleClose = () => {
    this.props.onClose();
  };

  render() {
    const {children, classes, open, title} = this.props;

    return (
        <Fragment>
          <Dialog
              fullScreen
              open={open}
              onClose={this.handleClose}
              TransitionComponent={Transition}
          >
            <AppBar className={classes.appBar}>
              <Toolbar>
                <Typography variant="title" color="inherit"
                            className={classes.flex}>
                  {title}
                </Typography>
                <IconButton color="inherit" onClick={this.handleClose}
                            aria-label="Close">
                  <CloseIcon/>
                </IconButton>
              </Toolbar>
            </AppBar>
            <main className={classes.layout}>
              <Paper className={classes.paper}>
                {children}
              </Paper>
            </main>
          </Dialog>
        </Fragment>
    );
  }
}

FullScreenForm.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(FullScreenForm);
