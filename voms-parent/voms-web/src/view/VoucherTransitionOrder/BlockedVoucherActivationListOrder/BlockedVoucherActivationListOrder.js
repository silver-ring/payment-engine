import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewBlockedVoucherActivationListOrder
  from "./NewBlockedVoucherActivationListOrder";
import EditBlockedVoucherActivationListOrder
  from "./EditBlockedVoucherActivationListOrder";

class BlockedVoucherActivationListOrder extends React.Component {

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
        <SimpleDataTableScreen resource="blockedVoucherActivationListOrders"
                               rows={this.rows}>
          <NewBlockedVoucherActivationListOrder/>
          <EditBlockedVoucherActivationListOrder/>
        </SimpleDataTableScreen>
    );
  }

}

export default BlockedVoucherActivationListOrder;
