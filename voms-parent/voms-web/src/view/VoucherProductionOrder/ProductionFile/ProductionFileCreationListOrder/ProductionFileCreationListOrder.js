import React from "react";
import SimpleDataTableScreen
  from "../../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewProductionFileCreationListOrder
  from "./NewProductionFileCreationListOrder";
import EditProductionFileCreationListOrder
  from "./EditProductionFileCreationListOrder";

class ProductionFileCreationListOrder extends React.Component {

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
        <SimpleDataTableScreen resource="productionFileCreationListOrders"
                               rows={this.rows}>
          <NewProductionFileCreationListOrder/>
          <EditProductionFileCreationListOrder/>
        </SimpleDataTableScreen>
    );
  }

}

export default ProductionFileCreationListOrder;
