import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import UserActivityArrayElementDetails from "./UserActivityArrayElementDetails";
import UserActivityObjectElementDetails
  from "./UserActivityObjectElementDetails";
import React from "react";

function ArrayElementListItems(props) {

  const {onClick, onClose, open, id, title, data} = props;

  return (
      <ListItem button>
        <ListItemText
            inset
            primary={title}
            secondary={"click for details"}
            onClick={onClick}
        />
        <UserActivityArrayElementDetails
            onClose={onClose}
            open={open}
            title={title}
            id={"dialog-title" + id}
            data={data}
        />
      </ListItem>
  )

}

function ObjectElementListItems(props) {

  const {title, data, onClick, onClose, open} = props;

  return (
      <ListItem button>
        <ListItemText inset primary={title}
                      secondary={"click for details"}
                      onClick={onClick}
        />
        <UserActivityObjectElementDetails
            onClose={onClose}
            open={open}
            title={title}
            id={"dialog-title" + title}
            data={data}
        />
      </ListItem>
  )
}

function PrimitiveElementListItem(props) {

  const {title, data} = props;

  return (<ListItem button>
        <ListItemText
            inset
            primary={title}
            secondary={data}/>
      </ListItem>
  )
}

class UserActivityDetails extends React.Component {

  state = {
    open: []
  };

  handleDetailsClick = (id) => {
    const open = this.state.open;
    open[id] = true;
    this.setState({
      open: open
    })
  };

  handleClose = (id) => {
    const open = this.state.open;
    open[id] = false;
    this.setState({
      open: open
    })
  };

  isArrayKey = (data, key) => {
    return data[key] !== null && Array.isArray(data[key])
  };

  isObjectKey = (data, key) => {
    return data[key] !== null && typeof data[key] === 'object';
  };

  render = () => {

    const {data, id} = this.props;
    const {open} = this.state;

    return Object.keys(data).map(key => {
          if (this.isArrayKey(data, key)) {
            const elementId = id + "[" + key + "]" + "[" + key["id"] + "]";
            return (
                <ArrayElementListItems id={elementId}
                                       onClick={() => this.handleDetailsClick(
                                           elementId)}
                                       data={data[key]}
                                       onClose={() => this.handleClose(elementId)}
                                       open={open[elementId]}
                                       title={key}/>
            )
          }
          if (this.isObjectKey(data, key)) {
            const elementId = id + "[" + key + "]" + "[" + key["id"] + "]";
            return (
                <ObjectElementListItems id={elementId}
                                        onClick={() => this.handleDetailsClick(
                                            elementId)}
                                        data={data[key]}
                                        onClose={() => this.handleClose(elementId)}
                                        open={open[elementId]}
                                        title={key}/>
            )
          }
          return (
              <PrimitiveElementListItem title={key} data={data[key]}/>
          )
        }
    )
  }
}

export default UserActivityDetails;
