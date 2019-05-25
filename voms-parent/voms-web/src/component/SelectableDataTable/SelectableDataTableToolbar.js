import Toolbar from "@material-ui/core/Toolbar/Toolbar";
import classNames from "classnames";
import Typography from "@material-ui/core/Typography/Typography";
import Grid from "@material-ui/core/Grid/Grid";
import SearchIcon from "@material-ui/icons/Search";
import TextField from "@material-ui/core/TextField/TextField";
import Tooltip from "@material-ui/core/Tooltip/Tooltip";
import IconButton from "@material-ui/core/IconButton/IconButton";
import Button from "@material-ui/core/Button/Button";
import PropTypes from "prop-types";
import {withStyles} from "@material-ui/core";
import React, {Fragment} from "react";
import {lighten} from "@material-ui/core/styles/colorManipulator";
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import AddIcon from '@material-ui/icons/Add';

const toolbarStyles = theme => ({
  flexGrow: 1,
  highlight:
      theme.palette.type === 'light'
          ? {
            color: theme.palette.secondary.main,
            backgroundColor: lighten(theme.palette.secondary.light, 0.85),
          }
          : {
            color: theme.palette.text.primary,
            backgroundColor: theme.palette.secondary.dark,
          },
  spacer: {
    flex: '1 1 50%',
  },
  actions: {
    color: theme.palette.text.secondary,
  },
  title: {
    flex: '0 0 auto',
  },
});

let SelectableDataTableToolbar = props => {
  const {numSelected, classes, onClickNew, onClickEdit, onClickDelete, onSearch} = props;

  return (
      <Toolbar
          className={classNames(classes.root, {
            [classes.highlight]: numSelected > 0,
          })}>
        <div className={classes.title}>
          {numSelected > 0 ? (
              <Typography color="inherit" variant="subheading">
                {numSelected} selected
              </Typography>
          ) : (
              <Grid container spacing={24} alignItems="flex-end">
                <Grid item>
                  <SearchIcon/>
                </Grid>
                <Grid item>
                  <TextField id="input-with-icon-grid" label="search" fullWidth
                             onChange={onSearch}/>
                </Grid>
              </Grid>
          )}
        </div>
        <div className={classes.spacer}/>
        <div className={classes.actions}>
          {numSelected > 0 ? (
              <Fragment>
                <Tooltip title="Activate">
                  <IconButton aria-label="Activate" onClick={onClickDelete}>
                    <DeleteIcon/>
                  </IconButton>
                </Tooltip>
                <Tooltip title="Inactivate">
                  <IconButton aria-label="Inactivate" onClick={onClickDelete}>
                    <DeleteIcon/>
                  </IconButton>
                </Tooltip>
                {numSelected === 1 ? (
                    <Tooltip title="Edit">
                      <IconButton aria-label="Edit" onClick={onClickEdit}>
                        <EditIcon/>
                      </IconButton>
                    </Tooltip>) : null
                }
              </Fragment>
          ) : (
              <Tooltip title="Create">
                <Button variant="fab" color="primary" onClick={onClickNew}
                        aria-label="Add" mini={true}>
                  <AddIcon/>
                </Button>
              </Tooltip>
          )}
        </div>
      </Toolbar>
  );
};

SelectableDataTableToolbar.propTypes = {
  classes: PropTypes.object.isRequired,
  numSelected: PropTypes.number.isRequired,
};

export default withStyles(toolbarStyles)(SelectableDataTableToolbar);
