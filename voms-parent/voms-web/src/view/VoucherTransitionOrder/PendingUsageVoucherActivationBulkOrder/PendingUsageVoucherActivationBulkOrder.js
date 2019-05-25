import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewPendingUsageVoucherActivationBulkOrder
  from "./NewPendingUsageVoucherActivationBulkOrder";
import EditPendingUsageVoucherActivationBulkOrder
  from "./EditPendingUsageVoucherActivationBulkOrder";

class PendingUsageVoucherActivationBulkOrder extends React.Component {

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
            resource="pendingUsageVoucherActivationBulkOrders" rows={this.rows}>
          <NewPendingUsageVoucherActivationBulkOrder/>
          <EditPendingUsageVoucherActivationBulkOrder/>
        </SimpleDataTableScreen>
    );
  }

}

export default PendingUsageVoucherActivationBulkOrder;
