import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewVoucherCreationBatchOrder from "./NewVoucherCreationBatchOrder";
import EditVoucherCreationBatchOrder from "./EditVoucherCreationBatchOrder";

class VoucherCreationBatchOrder extends React.Component {

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
        <SimpleDataTableScreen resource="voucherCreationBatchOrders"
                               rows={this.rows}>
          <NewVoucherCreationBatchOrder/>
          <EditVoucherCreationBatchOrder/>
        </SimpleDataTableScreen>
    );
  }

}

export default VoucherCreationBatchOrder;
