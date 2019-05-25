import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewOcsAccount from "./NewOcsAccount";
import EditOcsAccount from "./EditOcsAccount";

class OcsAccount extends React.Component {

  rows = [
    {id: 'name', label: 'Name', type: "text"},
    {id: 'idAtOcs', label: 'ID At OCS', type: "number"},
    {id: 'currency.base', label: 'Currency Base', type: "number"},
    {id: 'currency.unit', label: 'Currency Unit', type: "text"},
    {
      id: 'status',
      label: 'Status',
      options: [{id: "ACTIVE", label: "Active"},
        {id: "INACTIVE", label: "Inactive"}]
    }
  ];

  render = () => {
    return (
        <SimpleDataTableScreen resource="ocsAccounts" rows={this.rows}>
          <NewOcsAccount/>
          <EditOcsAccount/>
        </SimpleDataTableScreen>
    );
  }

}

export default OcsAccount;
