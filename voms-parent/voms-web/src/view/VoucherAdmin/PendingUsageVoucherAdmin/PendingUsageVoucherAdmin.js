import React from 'react';
import PendingUsageVoucherSearchAdmin from "./PendingUsageVoucherSearchAdmin";
import SearchScreen from "../../../component/SimpleSearchPage/SearchScreen";
import PendingUsageVoucherSearchResult
  from "./PendingUsageVoucherSearchResultAdmin";

class PendingUsageVoucherAdmin extends React.Component {

  state = {
    search: "findBySerialNumber"
  };

  handleOnChange = (search) => {
    this.setState({
      search: search
    })
  };

  render = () => {

    const {search} = this.state;

    return (
        <SearchScreen resource={"pendingUsageVouchers"}
                      search={search}>
          <PendingUsageVoucherSearchAdmin onChange={this.handleOnChange}/>
          <PendingUsageVoucherSearchResult/>
        </SearchScreen>
    );
  }

}

export default PendingUsageVoucherAdmin;