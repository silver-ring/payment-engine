import DialogTitle from "@material-ui/core/DialogTitle";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import Dialog from "@material-ui/core/Dialog";
import React from "react";

function ListDetails(props) {

  const {data} = props;

  return Object.keys(data).map(dataDetailsKey => {
    return (
        <ListItem button
                  key={dataDetailsKey}>
          <ListItemText
              inset
              primary={dataDetailsKey}
              secondary={data[dataDetailsKey]}/>
        </ListItem>
    )
  })
}

class UserActivityObjectElementDetails extends React.Component {

  render = () => {

    const {open, onClose, id, title, data} = this.props;

    return (
        <Dialog fullWidth={true}
                maxWidth={"sm"}
                onClose={onClose}
                open={open}>
          <DialogTitle id={id}>{title}</DialogTitle>
          <List aria-orientation={"horizontal"}>
            {data == null ?
                <ListItem button>
                  <ListItemText
                      inset
                      primary={"No Data"}/>
                </ListItem>
                :
                <ListDetails data={data}/>
            }
          </List>
        </Dialog>
    )
  }
}

export default UserActivityObjectElementDetails;