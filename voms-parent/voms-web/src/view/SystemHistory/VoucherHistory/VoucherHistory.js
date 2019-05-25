import React from 'react';
import VoucherHistorySearchResult from "./VoucherHistorySearchResult";
import VoucherHistorySearch from "./VoucherHistorySearch";
import SearchScreen from "../../../component/SimpleSearchPage/SearchScreen";

class VoucherHistory extends React.Component {

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
        <SearchScreen resource={"voucherHistories"}
                      search={newSearch}>
          <VoucherHistorySearch onChange={this.handleOnChange}/>
          <VoucherHistorySearchResult/>
        </SearchScreen>
    );
  }

}

export default VoucherHistory;