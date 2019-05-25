import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewCreatedVoucherActivationBulkOrder
  from "./NewCreatedVoucherActivationBulkOrder";
import EditCreatedVoucherActivationBulkOrder
  from "./EditCreatedVoucherActivationBulkOrder";

class CreatedVoucherActivationBulkOrder extends React.Component {

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
        <SimpleDataTableScreen resource="createdVoucherActivationBulkOrders"
                               rows={this.rows}>
          <NewCreatedVoucherActivationBulkOrder/>
          <EditCreatedVoucherActivationBulkOrder/>
        </SimpleDataTableScreen>
    );
  }

}

export default CreatedVoucherActivationBulkOrder;