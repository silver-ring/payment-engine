import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewPendingUsageVoucherUsingBulkOrder
  from "./NewPendingUsageVoucherUsingBulkOrder";
import EditPendingUsageVoucherUsingBulkOrder
  from "./EditPendingUsageVoucherUsingBulkOrder";

class PendingUsageVoucherUsingBulkOrder extends React.Component {

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
        <SimpleDataTableScreen
            resource="pendingUsageVoucherUsingBulkOrders"
            rows={this.rows}>
          <NewPendingUsageVoucherUsingBulkOrder/>
          <EditPendingUsageVoucherUsingBulkOrder/>
        </SimpleDataTableScreen>
    );
  }

}

export default PendingUsageVoucherUsingBulkOrder;