import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewBlockedVoucherActivationBulkOrder
  from "./NewBlockedVoucherActivationBulkOrder";
import EditBlockedVoucherActivationBulkOrder
  from "./EditBlockedVoucherActivationBulkOrder";

class BlockedVoucherActivationBulkOrder extends React.Component {

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
        <SimpleDataTableScreen resource="blockedVoucherActivationBulkOrders"
                               rows={this.rows}>
          <NewBlockedVoucherActivationBulkOrder/>
          <EditBlockedVoucherActivationBulkOrder/>
        </SimpleDataTableScreen>
    );
  }

}

export default BlockedVoucherActivationBulkOrder;
