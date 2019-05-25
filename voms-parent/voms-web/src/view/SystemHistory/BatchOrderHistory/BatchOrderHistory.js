import React from 'react';
import SearchScreen from "../../../component/SimpleSearchPage/SearchScreen";
import BatchOrderHistorySearch from "./BatchOrderHistorySearch";
import BatchOrderHistorySearchResult from "./BatchOrderHistorySearchResult";

class BatchOrderHistory extends React.Component {

  render = () => {
    return (
        <SearchScreen resource={"batchOrderHistories"}
                      search={"findAllByTagIdOrderByEventTimestampDesc"}>
          <BatchOrderHistorySearch onChange={this.handleOnChange}/>
          <BatchOrderHistorySearchResult/>
        </SearchScreen>
    );
  }

}

export default BatchOrderHistory;