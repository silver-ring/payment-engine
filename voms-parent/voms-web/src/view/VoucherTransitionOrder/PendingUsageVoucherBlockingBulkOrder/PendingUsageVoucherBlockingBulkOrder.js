import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewPendingUsageVoucherBlockingBulkOrder
  from "./NewPendingUsageVoucherBlockingBulkOrder";
import EditPendingUsageVoucherBlockingBulkOrder
  from "./EditPendingUsageVoucherBlockingBulkOrder";

class PendingUsageVoucherBlockingBulkOrder extends React.Component {

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
        <SimpleDataTableScreen resource="pendingUsageVoucherBlockingBulkOrders"
                               rows={this.rows}>
          <NewPendingUsageVoucherBlockingBulkOrder/>
          <EditPendingUsageVoucherBlockingBulkOrder/>
        </SimpleDataTableScreen>
    );
  }

}

export default PendingUsageVoucherBlockingBulkOrder;