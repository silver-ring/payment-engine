import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewCreatedVoucherDestroyBulkOrder
  from "./NewCreatedVoucherDestroyBulkOrder";
import EditCreatedVoucherDestroyBulkOrder
  from "./EditCreatedVoucherDestroyBulkOrder";

class CreatedVoucherDestroyBulkOrder extends React.Component {

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
        <SimpleDataTableScreen resource="createdVoucherDestroyBulkOrders"
                               rows={this.rows}>
          <NewCreatedVoucherDestroyBulkOrder/>
          <EditCreatedVoucherDestroyBulkOrder/>
        </SimpleDataTableScreen>
    );
  }

}

export default CreatedVoucherDestroyBulkOrder;