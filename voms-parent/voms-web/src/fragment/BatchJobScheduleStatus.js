import React from "react";
import SelectInput from "../component/SelectInput";

class BatchJobScheduleStatus extends React.Component {

  render = () => {
    let {defaultValue, value} = this.props;

    const options = {};

    if (defaultValue == null) {
      defaultValue = "CREATED";
      options["CREATED"] = "Created";
      options["SCHEDULED"] = "Scheduled";
    } else if (defaultValue === "CREATED") {
      options["CREATED"] = "Created";
      options["SCHEDULED"] = "Scheduled";
      options["CANCELED"] = "Canceled";
    } else {
      options["SCHEDULED"] = "Scheduled";
      options["PAUSED"] = "Paused";
      options["CANCELED"] = "Canceled";
    }

    return (
        <SelectInput
            options={options}
            field="batchJobScheduleStatus"
            label="Batch Job Schedule Status"
            defaultValue={defaultValue}
        />
    )
  }

}

export default BatchJobScheduleStatus;