import React, {Fragment} from "react";
import SimpleDataTable from "./SimpleDataTable";
import AlertMessage from "../AlertMessage";
import SimpleDataTableToolbar from "./SimpleDataTableToolbar";
import {withRouter} from "react-router";
import SimpleNewForm from "./SimpleNewForm";
import SimpleEditForm from "./SimpleEditForm";

class SimpleDataTableScreen extends React.Component {

  state = {
    openNewDialog: false,
    openEditDialog: false,
    selected: null,
    storedData: [],
    data: [],
    messages: []
  };

  pushMessage = (message) => {
    const messages = this.state.messages;
    messages.push(message);
    this.setState({messages: messages});
  };

  handleNewDialogClose = () => {
    this.setState({
      openNewDialog: false
    })
  };

  handleNewDialogSave = (message) => {
    this.pushMessage(message);
    this.setState(({
      openNewDialog: false
    }));
    this.handleRefresh();
  };

  handleOpenNewDialog = () => {
    this.setState(({
      openNewDialog: true
    }))
  };

  handleEditDialogSave = (message) => {
    this.pushMessage(message);
    this.setState({
      openEditDialog: false
    });
    this.handleRefresh();
  };

  handleEditDialogLoadError = (message) => {
    this.pushMessage(message);
    this.setState({
      openEditDialog: false
    });
    this.handleRefresh();
  };

  handleEditDialogClose = () => {
    this.setState({
      openEditDialog: false
    });
  };

  handleOpenEditDialog = (openEditInfoMessage) => {
    if (this.state.selected == null) {

      const title = (this.props.location.pathname).substring(1).split(
          /(?=[A-Z])/).join(" ");

      const openEditInfoMessage = "Please Select " + title + " to Edit!";

      const infoAlert = (
          <AlertMessage variant="info" message={openEditInfoMessage}/>);
      this.pushMessage(infoAlert);
    } else {
      this.setState(({
        openEditDialog: true
      }))
    }
  };

  handleOnSelect = (selected) => {
    this.setState({
      selected: selected
    })
  };

  handleRefresh = () => {
    this.simpleDataTable.handleRefresh();
  };

  render = () => {

    const {resource, rows} = this.props;
    const {selected} = this.state;

    return (
        <Fragment>
          {this.state.messages.map(message => (message))}
          <SimpleDataTable resource={resource} onSelect={this.handleOnSelect}
                           selected={this.state.selected}
                           rows={rows}
                           ref={(simpleDataTable) => this.simpleDataTable = simpleDataTable}>
            <SimpleNewForm onClose={this.handleNewDialogClose}
                           resource={resource}
                           open={this.state.openNewDialog}
                           onSave={this.handleNewDialogSave}
            >
              {this.props.children[0]}
            </SimpleNewForm>
            <SimpleEditForm onClose={this.handleEditDialogClose}
                            open={this.state.openEditDialog} selected={selected}
                            onSave={this.handleEditDialogSave}
                            onLoadError={this.handleEditDialogLoadError}
            >
              {this.props.children[1]}
            </SimpleEditForm>
            <SimpleDataTableToolbar onClickEdit={this.handleOpenEditDialog}
                                    onClickNew={this.handleOpenNewDialog}
                                    onClickRefresh={this.handleRefresh}/>
          </SimpleDataTable>
        </Fragment>
    );
  }

}

export default withRouter(SimpleDataTableScreen);
