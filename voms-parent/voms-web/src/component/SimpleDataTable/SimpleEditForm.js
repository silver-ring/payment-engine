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
import LinearProgress from "@material-ui/core/LinearProgress/LinearProgress";
import Grid from "@material-ui/core/Grid/Grid";
import {ValidatorForm} from "react-material-ui-form-validator";
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

class SimpleEditForm extends React.Component {

  state = {
    loading: false,
    error: false,
    alertMessage: <Fragment/>,
    title: "",
    screenLoading: false,
    data: {},
    children: <Fragment/>
  };

  static getDerivedStateFromProps = (props, state) => {
    if (props.open && !state.screenLoading && !state.loading) {
      return {screenLoading: true, prevSelected: props.selected};
    } else {
      return {screenLoading: false};
    }
  };

  componentDidMount = () => {
    const title = getPageTitle(this.props);
    this.setState({title: title});
  };

  componentDidUpdate(prevProps, prevState) {
    if (this.props.open && this.state.screenLoading) {
      this.handleLoadData();
    }
  }

  handleClose = () => {
    this.props.onClose();
  };

  handleLoadData = () => {
    restResourceInstance.get(this.props.selected)
    .then((response) => {
      const data = response.data;
      const children = React.cloneElement(this.props.children, {data});
      this.setState({
        children: children
      });
    }).catch((error) => {
          const responseErrorMessage = handleRemoteErrorResponse(error);
          const errorAlertMessage = (
              <AlertMessage variant="error" message={responseErrorMessage}/>);
          this.props.onLoadError(errorAlertMessage);
          this.setState({
            screenLoading: false
          });
        }
    );
  };

  handleFormSubmit = (e) => {
    e.preventDefault();

    if (!this.validationForm.isFormValid(false)) {
      return;
    }

    const json = convertFormToJson(this.form);
    restResourceInstance.patch(this.props.selected, json)
    .then((response) => {
      const successMessage = "Save " + this.state.title + " Success";
      const successAlert = (
          <AlertMessage variant="success" message={successMessage}/>);
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
    const {classes, open} = this.props;
    const {screenLoading, children, title, loading} = this.state;
    const EditTitle = "Edit " + title;

    const formChild = (screenLoading) ? <LinearProgress/> : (
        <Grid container spacing={24}>{children}</Grid>);

    return (
        <Fragment>
          {this.state.alertMessage}
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
                  {EditTitle}
                </Typography>
              </Toolbar>
            </AppBar>
            <main className={classes.layout}>
              <Paper className={classes.paper}>
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
                      {formChild}
                    </React.Fragment>
                    <React.Fragment>
                      <div className={classes.buttons}>
                        <Button
                            onClick={this.handleClose}
                            className={classes.button}
                            disabled={loading}>
                          Cancel
                        </Button>
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={this.handleFormSubmit}
                            className={classes.button}
                            disabled={loading}
                            autoFocus>
                          {loading ? (
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

SimpleEditForm.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withRouter(withStyles(styles)(SimpleEditForm));
