import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewActiveVoucherBlockingBulkOrder
  from "./NewActiveVoucherBlockingBulkOrder";
import EditActiveVoucherBlockingBulkOrder
  from "./EditActiveVoucherBlockingBulkOrder";

class ActiveVoucherBlockingBulkOrder extends React.Component {

  rows = [
    {id: 'tagId', label: 'Tag Id', type: "text"},
    {
      id: 'batchJobScheduleStatus', label: 'Status',
      options: [
        {id: "CREATED", label: "Created"},
        {id: "SCHEDULED", label: "Scheduled"},
        {id: "PAUSED", label: "Paused"},
        {id: "CANCELED", label: "Canceled"}
      ]
    }
  ];

  render = () => {
    return (
        <SimpleDataTableScreen resource="activeVoucherBlockingBulkOrders"
                               rows={this.rows}>
          <NewActiveVoucherBlockingBulkOrder/>
          <EditActiveVoucherBlockingBulkOrder/>
        </SimpleDataTableScreen>
    );
  }

}

export default ActiveVoucherBlockingBulkOrder;
