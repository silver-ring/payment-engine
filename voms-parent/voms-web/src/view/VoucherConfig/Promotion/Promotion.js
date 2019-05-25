import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewPromotion from "./NewPromotion";
import EditPromotion from "./EditPromotion";

class Promotion extends React.Component {

  rows = [
    {id: 'name', label: 'Name', type: "text"},
    {id: 'amount', label: 'Amount', type: "number"},
    {
      id: 'amountType',
      label: 'Amount Type',
      options: [{id: "FIXED", label: "Fixed"},
        {id: "PERCENTAGE", label: "Percentage"}]
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
        <SimpleDataTableScreen resource={"promotions"} rows={this.rows}>
          <NewPromotion/>
          <EditPromotion/>
        </SimpleDataTableScreen>
    );
  }

}

export default Promotion;
