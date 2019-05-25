import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewPendingUsageVoucherBlockingListOrder
  from "./NewPendingUsageVoucherBlockingListOrder";
import EditPendingUsageVoucherBlockingListOrder
  from "./EditPendingUsageVoucherBlockingListOrder";

class PendingUsageVoucherBlockingListOrder extends React.Component {

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
        <SimpleDataTableScreen resource="pendingUsageVoucherBlockingListOrders"
                               rows={this.rows}>
          <NewPendingUsageVoucherBlockingListOrder/>
          <EditPendingUsageVoucherBlockingListOrder/>
        </SimpleDataTableScreen>
    );
  }

}

export default PendingUsageVoucherBlockingListOrder;