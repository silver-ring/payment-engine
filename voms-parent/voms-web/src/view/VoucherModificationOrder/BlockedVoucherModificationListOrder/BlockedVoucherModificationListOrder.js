import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewBlockedVoucherModificationListOrder
  from "./NewBlockedVoucherModificationListOrder";
import EditBlockedVoucherModificationListOrder
  from "./EditBlockedVoucherModificationListOrder";

class BlockedVoucherModificationListOrder extends React.Component {

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
        <SimpleDataTableScreen resource="blockedVoucherModificationListOrders"
                               rows={this.rows}>
          <NewBlockedVoucherModificationListOrder/>
          <EditBlockedVoucherModificationListOrder/>
        </SimpleDataTableScreen>
    );
  }

}

export default BlockedVoucherModificationListOrder;
