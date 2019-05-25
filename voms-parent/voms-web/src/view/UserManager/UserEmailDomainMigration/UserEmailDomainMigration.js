import React from "react";
import SimpleDataTableScreen
  from "../../../component/SimpleDataTable/SimpleDataTableScreen";
import EditUserEmailDomainMigration from "./EditUserEmailDomainMigration";
import NewUserEmailDomainMigration from "./NewUserEmailDomainMigration";

class UserEmailDomainMigration extends React.Component {

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
        <SimpleDataTableScreen resource="userEmailDomainMigrations"
                               rows={this.rows}>
          <NewUserEmailDomainMigration/>
          <EditUserEmailDomainMigration/>
        </SimpleDataTableScreen>
    );
  }

}

export default UserEmailDomainMigration;
