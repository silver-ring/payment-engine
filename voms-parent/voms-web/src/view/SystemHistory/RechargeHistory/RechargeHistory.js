import React from 'react';
import RechargeHistorySearchResult from "./RechargeHistorySearchResult";
import RechargeHistorySearch from "./RechargeHistorySearch";
import SearchScreen from "../../../component/SimpleSearchPage/SearchScreen";

class RechargeHistory extends React.Component {

  state = {
    search: "findAllBySerialNumber"
  };

  handleOnChange = (search) => {
    this.setState({
      search: search
    })
  };

  render = () => {

    const {search} = this.state;
    const newSearch = search + "OrderByEventTimestampDesc";

    return (
        <SearchScreen resource={"rechargeHistories"}
                      search={newSearch}>
          <RechargeHistorySearch onChange={this.handleOnChange}/>
          <RechargeHistorySearchResult/>
        </SearchScreen>
    );
  }

}

export default RechargeHistory;