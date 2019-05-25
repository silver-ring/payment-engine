import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewActiveVoucherBlockingListOrder
  from "./NewActiveVoucherBlockingListOrder";
import EditActiveVoucherBlockingListOrder
  from "./EditActiveVoucherBlockingListOrder";

class ActiveVoucherBlockingListOrder extends React.Component {

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
        <SimpleDataTableScreen resource="activeVoucherBlockingListOrders"
                               rows={this.rows}>
          <NewActiveVoucherBlockingListOrder/>
          <EditActiveVoucherBlockingListOrder/>
        </SimpleDataTableScreen>
    );
  }

}

export default ActiveVoucherBlockingListOrder;
