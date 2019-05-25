import React from 'react';
import List from '@material-ui/core/List';
import VoucherConfig from "./menu/VoucherConfig";
import VoucherAdmin from "./menu/VoucherAdmin";
import UserManager from "./menu/UserManager";
import VoucherPolicy from "./menu/VoucherPolicy";
import VoucherProductionOrder from "./menu/VoucherProductionOrder";
import VoucherTransitionOrder from "./menu/VoucherTransitionOrder";
import VoucherModificationOrder from "./menu/VoucherModificationOrder";
import ConfigParameter from "./menu/ConfigParameter";
import SystemHistory from "./menu/SystemHistory";

class MainListItems extends React.Component {

  state = {
    active: ""
  };

  handelClick = (title, e) => {
    this.setState({
      active: title
    });
  };

  render = () => {

    const {active} = this.state;

    return (
        <div>
          <List component="nav">
            <ConfigParameter onItemClick={this.handelClick}
                             active={active}/>
            <SystemHistory onItemClick={this.handelClick}
                           active={active}/>
            <UserManager onItemClick={this.handelClick}
                         active={active}/>
            <VoucherPolicy onItemClick={this.handelClick}
                           active={active}/>
            <VoucherConfig onItemClick={this.handelClick}
                           active={active}/>
            <VoucherProductionOrder onItemClick={this.handelClick}
                                    active={active}/>
            <VoucherTransitionOrder onItemClick={this.handelClick}
                                    active={active}/>
            <VoucherModificationOrder onItemClick={this.handelClick}
                                      active={active}/>
            <VoucherAdmin onItemClick={this.handelClick}
                          active={active}/>
          </List>
        </div>
    );
  }
}

export default MainListItems;
