import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewVoucherProvider from "./NewVoucherProvider";
import EditVoucherProvider from "./EditVoucherProvider";

class VoucherProvider extends React.Component {

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
        <SimpleDataTableScreen resource="voucherProviders" rows={this.rows}>
          <NewVoucherProvider/>
          <EditVoucherProvider/>
        </SimpleDataTableScreen>
    );
  }

}

export default VoucherProvider;
