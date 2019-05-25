import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewConsumableVoucherType from "./NewConsumableVoucherType";
import EditConsumableVoucherType from "./EditConsumableVoucherType";

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
        <SimpleDataTableScreen resource={"consumableVoucherTypes"}
                               rows={this.rows}>
          <NewConsumableVoucherType/>
          <EditConsumableVoucherType/>
        </SimpleDataTableScreen>
    );
  }

}

export default ConsumableVoucherType;
