import React, {Fragment} from 'react';
import withStyles from '@material-ui/core/styles/withStyles';
import CssBaseline from '@material-ui/core/CssBaseline';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import * as halfred from "halfred";
import restResourceInstance from "../../../axios/RestResourceInstance";
import {handleRemoteErrorResponse} from "../../../util/ErrorHandlerUtils";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import AlertMessage from "../../../component/AlertMessage";
import LinearProgress from "@material-ui/core/LinearProgress/LinearProgress";

const styles = theme => ({
  buttons: {
    display: 'flex',
    justifyContent: 'flex-end',
  },
  button: {
    marginTop: theme.spacing.unit * 3,
    marginLeft: theme.spacing.unit,
  },
});

function DataTable(props) {

  const {data} = props;

  return (
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Name</TableCell>
            <TableCell>Status</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {data.map(row => (
              <TableRow key={row.id}>
                <TableCell component="th" scope="row">
                  {row.name}
                </TableCell>
                <TableCell component="th" scope="row">
                  {row.status}
                </TableCell>
              </TableRow>
          ))
          }
        </TableBody>
      </Table>
  )
}

class ShowUserRolesDialog extends React.Component {

  state = {
    userRoles: [],
    alertMessage: <Fragment/>,
    loading: false
  };

  handleClose = () => {
    this.props.onClose();
  };

  componentWillMount = () => {

    const {data} = this.props;

    const userRoles = halfred.parse(data).link("userRole")["href"];

    restResourceInstance.get(userRoles)
    .then((response) => {

      const userRoles = halfred.parse(response.data).embeddedArray(
          "userRoles");

      this.setState({
        userRoles: userRoles,
        alertMessage: <Fragment/>,
        loading: false
      })
    }).catch((error) => {
          const responseErrorMessage = handleRemoteErrorResponse(error);
          const errorAlertMessage = (
              <AlertMessage variant="error" message={responseErrorMessage}/>);
          this.setState({
            alertMessage: errorAlertMessage,
            loading: false
          });
        }
    );
    this.setState({
      alertMessage: <Fragment/>,
      loading: true
    })
  };

  render() {
    const {open} = this.props;
    const {userRoles, alertMessage, loading} = this.state;

    return (
        <React.Fragment>
          <CssBaseline/>
          {alertMessage}
          <Dialog open={open} onClose={this.handleClose}
                  aria-labelledby="form-dialog-title" fullWidth>
            <DialogTitle id="form-dialog-title">User Roles</DialogTitle>
            <DialogContent>
              {loading ?
                  <LinearProgress/>
                  :
                  <DataTable data={userRoles}/>
              }
            </DialogContent>
            <DialogActions>
              <Button color="primary"
                      onClick={this.handleClose}
                      autoFocus>
                {"Cancel"}
              </Button>
            </DialogActions>
          </Dialog>
        </React.Fragment>
    );
  }
}

export default withStyles(styles)(ShowUserRolesDialog);
