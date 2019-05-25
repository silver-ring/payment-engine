import React from 'react';
import UserActivitySearchResult from "./UserActivitySearchResult";
import UserActivitySearch from "./UserActivitySearch";
import SearchScreen from "../../../component/SimpleSearchPage/SearchScreen";

class UserActivity extends React.Component {

  render = () => {
    return (
        <SearchScreen resource={"userActivities"}
                      search={"findAllByEmailOrderByEventTimestampDesc"}>
          <UserActivitySearch/>
          <UserActivitySearchResult/>
        </SearchScreen>
    );
  }

}

export default UserActivity;