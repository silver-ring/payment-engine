import React from "react";
import EditMsisdnProvider from "./EditMsisdnProvider";
import NewMsisdnProvider from "./NewMsisdnProvider";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";

class MsisdnProvider extends React.Component {

  rows = [
    {id: 'name', label: 'Name', type: "text"},
    {id: 'idAtIn', label: 'ID At In', type: "number"},
    {
      id: 'status',
      label: 'Status',
      options: [{id: "ACTIVE", label: "Active"},
        {id: "INACTIVE", label: "Inactive"}]
    }
  ];

  render = () => {
    return (
        <SimpleDataTableScreen resource={"msisdnProviders"} rows={this.rows}>
          <NewMsisdnProvider/>
          <EditMsisdnProvider/>
        </SimpleDataTableScreen>
    );
  }

}

export default MsisdnProvider;
