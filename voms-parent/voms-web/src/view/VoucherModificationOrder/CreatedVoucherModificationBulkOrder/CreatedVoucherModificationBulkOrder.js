import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewCreatedVoucherModificationBulkOrder
  from "./NewCreatedVoucherModificationBulkOrder";
import EditCreatedVoucherModificationBulkOrder
  from "./EditCreatedVoucherModificationBulkOrder";

class CreatedVoucherModificationBulkOrder extends React.Component {

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
        <SimpleDataTableScreen resource="createdVoucherModificationBulkOrders"
                               rows={this.rows}>
          <NewCreatedVoucherModificationBulkOrder/>
          <EditCreatedVoucherModificationBulkOrder/>
        </SimpleDataTableScreen>
    );
  }

}

export default CreatedVoucherModificationBulkOrder;
