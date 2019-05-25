import React, {Fragment} from 'react';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Collapse from '@material-ui/core/Collapse';
import ExpandLess from '@material-ui/icons/ExpandLess';
import ExpandMore from '@material-ui/icons/ExpandMore';
import Divider from "@material-ui/core/Divider/Divider";
import {getAuthorities} from "../store/Auth";

class ListMenuItem extends React.Component {
  state = {
    open: false,
  };

  handleClick = () => {
    this.setState(state => ({open: !state.open}));
  };

  render = () => {

    const {icon, title, children} = this.props;
    const {open} = this.state;

    const authorities = getAuthorities();

    let shouldRender = false;

    if (children.length == null) {
      const to = children.props.to;
      if (authorities.includes(to)) {
        shouldRender = true;
      }
    } else {
      for (let i = 0; i < children.length; i++) {
        const to = children[i].props.to;
        if (authorities.includes(to)) {
          shouldRender = true;
        }
      }
    }

    if (!shouldRender) {
      return (<Fragment/>)
    }

    return (
        <Fragment>
          <ListItem button onClick={this.handleClick}>
            <ListItemIcon>
              {icon}
            </ListItemIcon>
            <ListItemText inset primary={title}/>
            {open ? <ExpandLess/> : <ExpandMore/>}
          </ListItem>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <List component="div" disablePadding>
              {children}
            </List>
          </Collapse>
          <Divider inset component="li"/>
        </Fragment>
    );
  }
}

export default ListMenuItem;
