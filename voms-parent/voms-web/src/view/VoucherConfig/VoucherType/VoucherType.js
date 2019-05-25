import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewVoucherType from "./NewVoucherType";
import EditVoucherType from "./EditVoucherType";

class VoucherType extends React.Component {

  rows = [
    {id: 'name', label: 'Name', type: "text"},
    {id: 'idAtIn', label: 'ID At IN', type: "number"},
    {
      id: 'status',
      label: 'Status',
      options: [{id: "ACTIVE", label: "Active"},
        {id: "INACTIVE", label: "Inactive"}]
    }
  ];

  render = () => {
    return (
        <SimpleDataTableScreen resource="voucherTypes" rows={this.rows}>
          <NewVoucherType/>
          <EditVoucherType/>
        </SimpleDataTableScreen>
    );
  }

}

export default VoucherType;
