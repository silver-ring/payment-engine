import React from 'react';
import ActiveVoucherSearchResultAdmin from "./ActiveVoucherSearchResultAdmin";
import ActiveVoucherSearchAdmin from "./ActiveVoucherSearchAdmin";
import SearchScreen from "../../../component/SimpleSearchPage/SearchScreen";

class ActiveVoucherAdmin extends React.Component {

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
        <SearchScreen resource={"activeVouchers"}
                      search={search}>
          <ActiveVoucherSearchAdmin onChange={this.handleOnChange}/>
          <ActiveVoucherSearchResultAdmin/>
        </SearchScreen>
    );
  }

}

export default ActiveVoucherAdmin;