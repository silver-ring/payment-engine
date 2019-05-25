import React from "react";
import SimpleDataTableScreen
  from "../../../../component/SimpleDataTable/SimpleDataTableScreen";
import NewProductionFileCreationBulkOrder
  from "./NewProductionFileCreationBulkOrder";
import EditProductionFileCreationBulkOrder
  from "./EditProductionFileCreationBulkOrder";

class ProductionFileCreationBulkOrder extends React.Component {

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
        <SimpleDataTableScreen resource="productionFileCreationBulkOrders"
                               rows={this.rows}>
          <NewProductionFileCreationBulkOrder/>
          <EditProductionFileCreationBulkOrder/>
        </SimpleDataTableScreen>
    );
  }

}

export default ProductionFileCreationBulkOrder;
