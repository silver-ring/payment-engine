import {Link} from "react-router-dom";
import ListItemText from "@material-ui/core/ListItemText/ListItemText";
import ListItem from "@material-ui/core/ListItem/ListItem";
import React from "react";
import {getAuthorities} from "../store/Auth";

const Item = (props) => {
  const {to, active, name, onItemClick} = props;

  const authorities = getAuthorities();

  const render = authorities.includes(to);

  if (!render) {
    return (
        <React.Fragment/>
    );
  } else {
    return (<ListItem button component={Link} to={"/" + to}
                      selected={active === name}
                      onClick={() => onItemClick(name)}>
      <ListItemText inset secondary={name}/>
    </ListItem>)
  }
};

export default Item;
