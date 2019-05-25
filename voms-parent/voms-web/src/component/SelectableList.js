import React, {Fragment} from 'react';
import {withStyles} from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import Checkbox from '@material-ui/core/Checkbox';
import ListSubheader from "@material-ui/core/ListSubheader/ListSubheader";
import axios from "axios";
import * as halfred from "halfred";
import AlertMessage from "./AlertMessage";
import LinearProgress from "@material-ui/core/LinearProgress/LinearProgress";
import {handleRemoteErrorResponse} from "../util/ErrorHandlerUtils";
import restResourceInstance from "../axios/RestResourceInstance";

const styles = theme => ({
  root: {
    width: '100%',
    maxWidth: 360,
    backgroundColor: theme.palette.background.paper,
    position: 'relative',
    overflow: 'auto',
    maxHeight: 300,
  }
});

class SelectableList extends React.Component {
  state = {
    checked: [],
    items: []
  };

  componentWillMount = () => {
    this.handleLoadData();
  };

  handleToggle = value => () => {
    const {checked} = this.state;
    const currentIndex = checked.indexOf(value);
    const newChecked = [...checked];

    if (currentIndex === -1) {
      newChecked.push(value);
    } else {
      newChecked.splice(currentIndex, 1);
    }

    this.setState({
      checked: newChecked,
    });
  };

  handleLoadData = () => {

    const {
      resource, property, defaultValue, page,
      size, sort, search, embedded
    } = this.props;

    let newEmbedded = [resource];

    if (embedded != null) {
      newEmbedded = embedded;
    }

    let newPage = 0;
    if (page != null) {
      newPage = page;
    }

    let newSize = 100;
    if (size != null) {
      newSize = size;
    }

    let newSort = {property: property, direction: "asc"};

    if (sort != null) {
      newSort = sort;
    }

    let newSearch = {status: "ACTIVE"};
    if (search != null) {
      newSearch = search;
    }

    const pageNumberParam = "page=" + newPage + "&";
    const pageSizeParam = "size=" + newSize + "&";
    const sortByParam = "sort=" + newSort.property + "," + newSort.direction;

    const searchParam = Object.keys(newSearch).map((key) => {
      const val = newSearch[key];
      return key + "=" + val + "&";
    }).reduce(((first, second) => first + second), "");

    const url = resource + "?" + searchParam + pageNumberParam + pageSizeParam
        + sortByParam;

    const request = [restResourceInstance.get(url)];
    if (defaultValue != null) {
      request.push(restResourceInstance.get(defaultValue));
    }

    axios.all(request)
    .then(axios.spread((itemsResponse, defaultResponse) => {
      let checked = [];
      if (defaultResponse != null) {
        newEmbedded.map((key) => {
          const defaultItemsResources = halfred.parse(
              defaultResponse.data).embeddedArray(key);
          const items = defaultItemsResources.map(defaultItemResource => {
            const defaultItemValue = halfred.parse(defaultItemResource).link(
                "self")["href"];
            checked.push(defaultItemValue);
          });
        });
      }
      let items = [];
      newEmbedded.map((key) => {
        const itemsResources = halfred.parse(itemsResponse.data).embeddedArray(
            key);
        const resourceItems = itemsResources.map(itemResource => {
          const itemValue = halfred.parse(itemResource).link("self")["href"];
          const itemLabel = itemResource[property];
          items.push({value: itemValue, label: itemLabel});
        });
      });
      this.setState({
        items: items,
        loading: false,
        checked: checked
      });
    })).catch((error) => {
      const responseErrorMessage = handleRemoteErrorResponse(error);
      const errorAlertMessage = (
          <AlertMessage variant="error" message={responseErrorMessage}/>);
      this.setState({
        loading: false,
        alertMessage: errorAlertMessage
      });
    });
    this.setState({
      loading: true,
      alertMessage: ""
    });
  };

  render() {
    const {classes, title, field} = this.props;
    const {items, checked, loading} = this.state;

    if (loading) {
      return (<LinearProgress/>);
    }

    let inputData = (<input type={"hidden"} name={field + "[]"} value={[]}/>);
    if (checked.length !== 0) {
      inputData = checked.map(key => (
          <input key={key} type={"hidden"} name={field + "[]"}
                 value={key}/>
      ));
    }

    return (
        <Fragment>
          {this.state.alertMessage}
          {inputData}
          <List className={classes.root} subheader={<li/>}>
            <ListSubheader>{title}</ListSubheader>
            {items.map(key => (
                <ListItem
                    key={key.value}
                    role={undefined}
                    dense
                    button
                    onClick={this.handleToggle(key.value)}
                    className={classes.listItem}
                >
                  <Checkbox
                      checked={checked.includes(key.value)}
                      tabIndex={-1}
                      disableRipple
                  />
                  <ListItemText primary={key.label}/>
                </ListItem>
            ))}
          </List>
        </Fragment>
    );
  }
}

export default withStyles(styles)(SelectableList);
