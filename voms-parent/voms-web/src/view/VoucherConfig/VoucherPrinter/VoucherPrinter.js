import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewVoucherPrinter from "./NewVoucherPrinter";
import EditVoucherPrinter from "./EditVoucherPrinter";

class VoucherPrinter extends React.Component {

  rows = [
    {id: 'name', label: 'Name', type: "text"},
    {
      id: 'status',
      label: 'Status',
      options: [{id: "ACTIVE", label: "Active"},
        {id: "INACTIVE", label: "Inactive"}]
    }
  ];

  render = () => {
    return (
        <SimpleDataTableScreen resource="voucherPrinters" rows={this.rows}>
          <NewVoucherPrinter/>
          <EditVoucherPrinter/>
        </SimpleDataTableScreen>
    );
  }

}

export default VoucherPrinter;
