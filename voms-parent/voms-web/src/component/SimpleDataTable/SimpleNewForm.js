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
import Button from "@material-ui/core/Button/Button";
import CircularProgress
  from "@material-ui/core/CircularProgress/CircularProgress";
import {convertFormToJson} from "../../util/ConvertUtils";
import AlertMessage from "../AlertMessage";
import {withRouter} from "react-router";
import Grid from "@material-ui/core/Grid/Grid";
import {ValidatorForm} from 'react-material-ui-form-validator';
import {handleRemoteErrorResponse} from "../../util/ErrorHandlerUtils";
import {getPageTitle} from "../../util/TitleUtils";
import restResourceInstance from "../../axios/RestResourceInstance";

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

class SimpleNewForm extends React.Component {

  state = {
    loading: false,
    error: false,
    alertMessage: <Fragment/>,
    title: ""
  };

  handleClose = () => {
    this.props.onClose();
  };

  componentWillMount = () => {
    const title = getPageTitle(this.props);
    this.setState({title: title});
  };

  handleFormSubmit = (e) => {
    e.preventDefault();

    if (!this.validationForm.isFormValid(false)) {
      return;
    }

    const json = convertFormToJson(this.form);

    restResourceInstance.post(this.props.resource, json)
    .then((response) => {
      const successMessage = "Save " + this.state.title + " Success";
      const successAlert = (<AlertMessage key={json} variant="success"
                                          message={successMessage}/>);
      this.props.onSave(successAlert);
      this.setState({
        loading: false
      });
    }).catch((error) => {
          const responseErrorMessage = handleRemoteErrorResponse(error);
          const errorAlert = (
              <AlertMessage variant="error" message={responseErrorMessage}/>);
          this.setState({
            loading: false,
            error: true,
            alertMessage: errorAlert
          });
        }
    );
    this.setState({
      loading: true,
      error: false,
      alertMessage: ""
    });

  };

  render() {
    const {children, classes, open} = this.props;
    const {title} = this.state;
    const newTitle = "New " + title;

    return (
        <Fragment>
          <Dialog
              fullScreen
              open={open}
              onClose={this.handleClose}
              TransitionComponent={Transition}>
            <AppBar className={classes.appBar}>
              <Toolbar>
                <IconButton color="inherit" onClick={this.handleClose}
                            aria-label="Close">
                  <CloseIcon/>
                </IconButton>
                <Typography variant="title" color="inherit"
                            className={classes.flex}>
                  {newTitle}
                </Typography>
              </Toolbar>
            </AppBar>
            <main className={classes.layout}>
              <Paper className={classes.paper}>
                {this.state.alertMessage}
                <ValidatorForm
                    ref={(form) => {
                      this.validationForm = form
                    }}
                    onSubmit={this.handleFormSubmit}
                    instantValidate={true}
                >
                  <form ref={(form) => {
                    this.form = form
                  }}>
                    <React.Fragment>
                      <Grid container spacing={24}>
                        {children}
                      </Grid>
                    </React.Fragment>
                    <React.Fragment>
                      <div className={classes.buttons}>
                        <Button
                            onClick={this.handleClose}
                            className={classes.button}
                            disabled={this.state.loading}>
                          Cancel
                        </Button>
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={this.handleFormSubmit}
                            className={classes.button}
                            disabled={this.state.loading}
                            autoFocus>
                          {this.state.loading ? (
                              <CircularProgress size={20}/>) : "Save"}
                        </Button>
                      </div>
                    </React.Fragment>
                  </form>
                </ValidatorForm>
              </Paper>
            </main>
          </Dialog>
        </Fragment>
    );
  }
}

SimpleNewForm.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withRouter(withStyles(styles)(SimpleNewForm));
