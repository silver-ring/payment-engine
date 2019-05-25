import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewCreatedVoucherDestroyListOrder
  from "./NewCreatedVoucherDestroyListOrder";
import EditCreatedVoucherDestroyListOrder
  from "./EditCreatedVoucherDestroyListOrder";

class CreatedVoucherDestroyListOrder extends React.Component {

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
        <SimpleDataTableScreen resource="createdVoucherDestroyListOrders"
                               rows={this.rows}>
          <NewCreatedVoucherDestroyListOrder/>
          <EditCreatedVoucherDestroyListOrder/>
        </SimpleDataTableScreen>
    );
  }

}

export default CreatedVoucherDestroyListOrder;