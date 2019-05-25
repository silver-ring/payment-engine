import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewCreatedVoucherActivationListOrder
  from "./NewCreatedVoucherActivationListOrder";
import EditCreatedVoucherActivationListOrder
  from "./EditCreatedVoucherActivationListOrder";

class CreatedVoucherActivationListOrder extends React.Component {

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
        <SimpleDataTableScreen resource="createdVoucherActivationListOrders"
                               rows={this.rows}>
          <NewCreatedVoucherActivationListOrder/>
          <EditCreatedVoucherActivationListOrder/>
        </SimpleDataTableScreen>
    );
  }

}

export default CreatedVoucherActivationListOrder;