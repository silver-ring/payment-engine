import React from 'react';
import SearchScreen from "../../../component/SimpleSearchPage/SearchScreen";
import CreatedVoucherSearchAdmin from "./CreatedVoucherSearchAdmin";
import CreatedVoucherSearchResultAdmin from "./CreatedVoucherSearchResultAdmin";

class CreatedVoucherAdmin extends React.Component {

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
        <SearchScreen resource={"createdVouchers"}
                      search={search}>
          <CreatedVoucherSearchAdmin onChange={this.handleOnChange}/>
          <CreatedVoucherSearchResultAdmin/>
        </SearchScreen>
    );
  }

}

export default CreatedVoucherAdmin;