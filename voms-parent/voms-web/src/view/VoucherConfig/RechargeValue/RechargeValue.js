import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import EditRechargeValue from "./EditRechargeValue";
import NewRechargeValue from "./NewRechargeValue";

class RechargeValue extends React.Component {

  rows = [
    {id: 'name', label: 'Name', type: "text"},
    {id: 'value', label: 'Value', type: "number"},
    {
      id: 'status',
      label: 'Status',
      options: [{id: "ACTIVE", label: "Active"},
        {id: "INACTIVE", label: "Inactive"}]
    }
  ];

  render = () => {
    return (
        <SimpleDataTableScreen resource={"rechargeValues"} rows={this.rows}>
          <NewRechargeValue/>
          <EditRechargeValue/>
        </SimpleDataTableScreen>
    );
  }

}

export default RechargeValue;
