import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewPendingUsageVoucherUsingListOrder
  from "./NewPendingUsageVoucherUsingListOrder";
import EditPendingUsageVoucherUsingListOrder
  from "./EditPendingUsageVoucherUsingListOrder";

class PendingUsageVoucherUsingListOrder extends React.Component {

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
        <SimpleDataTableScreen resource="pendingUsageVoucherUsingListOrders"
                               rows={this.rows}>
          <NewPendingUsageVoucherUsingListOrder/>
          <EditPendingUsageVoucherUsingListOrder/>
        </SimpleDataTableScreen>
    );
  }

}

export default PendingUsageVoucherUsingListOrder;