import React, {Fragment} from 'react';
import {withRouter} from "react-router";
import Paper from "@material-ui/core/Paper/Paper";
import withStyles from "@material-ui/core/styles/withStyles";
import SearchSection from "./SearchSection";

const styles = theme => ({
  layout: {
    width: 'auto',
    padding: theme.spacing.unit * 4,
    marginTop: theme.spacing.unit * 2,
    marginDown: theme.spacing.unit * 2,
    marginLeft: theme.spacing.unit * 2,
    marginRight: theme.spacing.unit * 2,
    minHeight: 500,
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
  }
});

class SearchScreen extends React.Component {

  state = {
    searchResult: <Fragment/>,
    messages: []
  };

  handleOnSearch = (data) => {
    const searchResult = React.cloneElement(this.props.children[1],
        {data, onAction: this.handleOnAction});
    this.setState({
      searchResult: searchResult
    })
  };

  handleOnAction = (message) => {
    this.pushMessage(message);
    this.setState({
      searchResult: <Fragment/>
    })
  };

  pushMessage = (message) => {
    const messages = this.state.messages;
    messages.push(message);
    this.setState({messages: messages});
  };

  render = () => {

    const {classes, service, resource, search} = this.props;
    const {searchResult} = this.state;

    return (
        <Fragment>
          {this.state.messages.map(message => (message))}
          <SearchSection resource={resource}
                         search={search}
                         onSearch={this.handleOnSearch}
          >
            {this.props.children[0]}
          </SearchSection>
          <Paper>
            <main className={classes.layout}>
              <Paper classes={classes.paper}>
                {searchResult}
              </Paper>
            </main>
          </Paper>
        </Fragment>
    );
  }

}

export default withRouter(withStyles(styles)(SearchScreen));
