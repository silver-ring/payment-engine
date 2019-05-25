import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewPendingUsageVoucherActivationListOrder
  from "./NewPendingUsageVoucherActivationListOrder";
import EditPendingUsageVoucherActivationListOrder
  from "./EditPendingUsageVoucherActivationListOrder";

class PendingUsageVoucherActivationListOrder extends React.Component {

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
            resource="pendingUsageVoucherActivationListOrders"
            rows={this.rows}>
          <NewPendingUsageVoucherActivationListOrder/>
          <EditPendingUsageVoucherActivationListOrder/>
        </SimpleDataTableScreen>
    );
  }

}

export default PendingUsageVoucherActivationListOrder;
