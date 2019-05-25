import React, {Fragment} from 'react';
import classNames from 'classnames';
import {withStyles} from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import Drawer from '@material-ui/core/Drawer';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import AccountCircle from '@material-ui/icons/AccountCircle';
import MenuItem from '@material-ui/core/MenuItem';
import Menu from '@material-ui/core/Menu';
import MainListItems from "./MainListItems";
import Divider from "@material-ui/core/Divider/Divider";
import {Link} from "react-router-dom";
import {withRouter} from "react-router";
import ChangePassword from "../view/ChangePassword";
import ContentRouter from "../router/ContentRouter";
import Avatar from "@material-ui/core/Avatar/Avatar";
import logo from "./../asset/img/logo.jpg";
import {getPageTitle} from "../util/TitleUtils";
import UserProfile from "../view/UserProfile";
import restInstance from "../axios/RestInstance";
import LinearProgress
  from "../view/VoucherAdmin/PendingUsageVoucherAdmin/PendingUsageVoucherSearchResultAdmin";
import {clearAuth} from "../store/Auth";

const drawerWidth = 350;

const styles = theme => ({
  root: {
    display: 'flex',
  },
  toolbar: {
    paddingRight: 24, // keep right padding when drawer closed
  },
  toolbarIcon: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: '0 8px',
    ...theme.mixins.toolbar,
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
  },
  appBarShift: {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  menuButton: {
    marginLeft: 12,
    marginRight: 36,
  },
  menuButtonHidden: {
    display: 'none',
  },
  title: {
    flexGrow: 1,
  },
  drawerPaper: {
    position: 'relative',
    whiteSpace: 'nowrap',
    width: drawerWidth,
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
  },
  drawerPaperClose: {
    overflowX: 'hidden',
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    width: theme.spacing.unit * 7,
    [theme.breakpoints.up('sm')]: {
      width: theme.spacing.unit * 9,
    },
  },
  appBarSpacer: theme.mixins.toolbar,
  content: {
    flexGrow: 1,
    padding: theme.spacing.unit * 3,
    height: '100vh',
    overflow: 'auto',
  },
  chartContainer: {
    marginLeft: -22,
  },
  tableContainer: {
    height: 320,
  },
  avatar: {
    width: "65%",
    height: "100%"
  },
});

class AppContainer extends React.Component {
  state = {
    open: true,
    anchorEl: null,
    openChangePassword: false,
    openUserProfile: false,
    alertMessage: <React.Fragment/>,
    loading: false
  };

  handleDrawerOpen = () => {
    this.setState({open: true});
  };

  handleDrawerClose = () => {
    this.setState({open: false});
  };

  handleMenu = event => {
    this.setState({anchorEl: event.currentTarget});
  };

  handleClose = () => {
    this.setState({anchorEl: null});
  };

  handleChangePassword = () => {
    const newState = !this.state.openChangePassword;
    this.setState({openChangePassword: newState});
  };

  handleUserProfile = () => {
    const newState = !this.state.openUserProfile;
    this.setState({openUserProfile: newState});
  };

  handleLogout = () => {
    restInstance.post("/authentication/logout")
    .then(() => {
    }).catch((error) => {
    }).finally(() => {
      clearAuth();
      window.location.href = "/login";
    });
  };

  render() {
    const {classes} = this.props;
    const {anchorEl, alertMessage, loading} = this.state;
    const open = Boolean(anchorEl);
    const title = getPageTitle(this.props);

    let progress = <Fragment/>;
    if (loading) {
      progress = <LinearProgress/>
    }

    return (
        <React.Fragment>
          {alertMessage}
          {progress}
          <CssBaseline/>
          <div className={classes.root}>
            <AppBar
                position="absolute"
                className={classNames(classes.appBar,
                    this.state.open && classes.appBarShift)}
            >
              <Toolbar disableGutters={!this.state.open}
                       className={classes.toolbar}>
                <IconButton
                    color="inherit"
                    aria-label="Open drawer"
                    onClick={this.handleDrawerOpen}
                    className={classNames(
                        classes.menuButton,
                        this.state.open && classes.menuButtonHidden,
                    )}
                >
                  <MenuIcon/>
                </IconButton>
                <Typography variant="title" color="inherit" noWrap
                            className={classes.title}>
                  {title}
                </Typography>
                <div>
                  <IconButton
                      aria-owns={open ? 'menu-appbar' : null}
                      aria-haspopup="true"
                      onClick={this.handleMenu}
                      color="inherit"
                  >
                    <AccountCircle/>
                  </IconButton>
                  <Menu
                      id="menu-appbar"
                      anchorEl={anchorEl}
                      anchorOrigin={{
                        vertical: 'top',
                        horizontal: 'right',
                      }}
                      transformOrigin={{
                        vertical: 'top',
                        horizontal: 'right',
                      }}
                      onClose={this.handleClose}
                      open={open}
                  >
                    <MenuItem onClick={() => {
                      this.handleUserProfile();
                      this.handleClose();
                    }}>Profile</MenuItem>
                    <MenuItem onClick={() => {
                      this.handleChangePassword();
                      this.handleClose();
                    }}>Change Password</MenuItem>
                    <MenuItem onClick={() => {
                      this.handleLogout();
                      this.handleClose();
                    }}>Logout</MenuItem>
                  </Menu>
                </div>
              </Toolbar>
            </AppBar>
            <Drawer
                variant="permanent"
                classes={{
                  paper: classNames(classes.drawerPaper,
                      !this.state.open && classes.drawerPaperClose),
                }}
                open={this.state.open}
            >
              <div className={classes.toolbarIcon}>
                <Avatar className={classes.avatar} src={logo}
                        component={Link} to="/"/>
                <IconButton onClick={this.handleDrawerClose}>
                  <ChevronLeftIcon/>
                </IconButton>
              </div>
              <Divider/>
              {/*---- menu items -----*/}
              <MainListItems/>
              {/*---------------*/}
            </Drawer>
            <main className={classes.content}>
              <div className={classes.appBarSpacer}/>
              {/*------------------ Main content ---------------*/}
              <ContentRouter/>
              <UserProfile open={this.state.openUserProfile}
                           onClose={this.handleUserProfile}/>
              <ChangePassword open={this.state.openChangePassword}
                              onClose={this.handleChangePassword}/>
              {/*------------------ Main content ---------------*/}

            </main>
          </div>
        </React.Fragment>
    );
  };
}

export default withRouter(withStyles(styles)(AppContainer));
