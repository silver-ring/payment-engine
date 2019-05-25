import Toolbar from "@material-ui/core/Toolbar/Toolbar";
import Tooltip from "@material-ui/core/Tooltip/Tooltip";
import Button from "@material-ui/core/Button/Button";
import {withStyles} from "@material-ui/core";
import React from "react";
import AddIcon from '@material-ui/icons/Add';
import EditIcon from '@material-ui/icons/Edit';
import RefreshIcon from '@material-ui/icons/Refresh';

const toolbarStyles = theme => ({
  flexGrow: 1,
  spacer: {
    flex: '1 1 50%',
  },
  title: {
    flex: '0 0 auto',
  },
  button: {
    margin: '5px'
  }
});

let SimpleDataTableToolbar = props => {
  const {classes, onClickNew, onClickEdit, onClickRefresh} = props;

  return (
      <Toolbar>
        <div>
          <Button variant="fab" color="primary" onClick={onClickRefresh}
                  mini={true} className={classes.button}>
            <Tooltip title="Refresh">
              <RefreshIcon/>
            </Tooltip>
          </Button>
        </div>
        <div>
          <Button variant="fab" color="primary" onClick={onClickNew} mini={true}
                  className={classes.button}>
            <Tooltip title="Create">
              <AddIcon/>
            </Tooltip>
          </Button>
        </div>
        <div>
          <Button variant="fab" color="primary" onClick={onClickEdit}
                  mini={true} className={classes.button}>
            <Tooltip title="Edit">
              <EditIcon/>
            </Tooltip>
          </Button>
        </div>
      </Toolbar>
  );
};

export default withStyles(toolbarStyles)(SimpleDataTableToolbar);
