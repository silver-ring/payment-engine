import React from 'react';
import BlockedVoucherSearchAdmin from "./BlockedVoucherSearchAdmin";
import SearchScreen from "../../../component/SimpleSearchPage/SearchScreen";
import BlockedVoucherSearchResultAdmin from "./BlockedVoucherSearchResultAdmin";

class BlockedVoucherAdmin extends React.Component {

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
        <SearchScreen resource={"blockedVouchers"}
                      search={search}>
          <BlockedVoucherSearchAdmin onChange={this.handleOnChange}/>
          <BlockedVoucherSearchResultAdmin/>
        </SearchScreen>
    );
  }

}

export default BlockedVoucherAdmin;