import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewBlockedVoucherModificationBulkOrder
  from "./NewBlockedVoucherModificationBulkOrder";
import EditBlockedVoucherModificationBulkOrder
  from "./EditBlockedVoucherModificationBulkOrder";

class BlockedVoucherModificationBulkOrder extends React.Component {

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
        <SimpleDataTableScreen resource="blockedVoucherModificationBulkOrders"
                               rows={this.rows}>
          <NewBlockedVoucherModificationBulkOrder/>
          <EditBlockedVoucherModificationBulkOrder/>
        </SimpleDataTableScreen>
    );
  }

}

export default BlockedVoucherModificationBulkOrder;
