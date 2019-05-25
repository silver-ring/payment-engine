import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewTaxation from "./NewTaxation";
import EditTaxation from "./EditTaxation";

class Taxation extends React.Component {

  rows = [
    {id: 'name', label: 'Name', type: "text"},
    {id: 'amount', label: 'Amount', type: "number"},
    {
      id: 'amountType',
      label: 'Amount Type',
      options: [{id: "Fixed", label: "Fixed"},
        {id: "Percentage", label: "Percentage"}]
    },
    {
      id: 'status',
      label: 'Status',
      options: [{id: "ACTIVE", label: "Active"},
        {id: "INACTIVE", label: "Inactive"}]
    }
  ];

  render = () => {
    return (
        <SimpleDataTableScreen resource={"taxations"} rows={this.rows}>
          <NewTaxation/>
          <EditTaxation/>
        </SimpleDataTableScreen>
    );
  }

}

export default Taxation;
