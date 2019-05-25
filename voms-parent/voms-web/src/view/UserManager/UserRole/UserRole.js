import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewUserRole from "./NewUserRole";
import EditUserRole from "./EditUserRole";

class ConsumableVoucherType extends React.Component {

  rows = [
    {id: 'name', label: 'Name'},
    {
      id: 'status',
      label: 'Status',
      options: [{id: "ACTIVE", label: "Active"},
        {id: "INACTIVE", label: "Inactive"}]
    }
  ];

  render = () => {
    return (
        <SimpleDataTableScreen resource={"userRoles"} rows={this.rows}>
          <NewUserRole/>
          <EditUserRole/>
        </SimpleDataTableScreen>
    );
  }

}

export default ConsumableVoucherType;
