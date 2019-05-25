import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewCreatedVoucherModificationListOrder
  from "./NewCreatedVoucherModificationListOrder";
import EditCreatedVoucherModificationListOrder
  from "./EditCreatedVoucherModificationListOrder";

class CreatedVoucherModificationListOrder extends React.Component {

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
        <SimpleDataTableScreen resource="createdVoucherModificationListOrders"
                               rows={this.rows}>
          <NewCreatedVoucherModificationListOrder/>
          <EditCreatedVoucherModificationListOrder/>
        </SimpleDataTableScreen>
    );
  }

}

export default CreatedVoucherModificationListOrder;
