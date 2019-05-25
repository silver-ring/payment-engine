import React from 'react';
import SearchScreen from "../component/SimpleSearchPage/SearchScreen";
import UserAccountSearch from "./UserAccountSearch";
import UserAccountSearchResult from "./UserAccountSearchResult";

class UserAccountSearchScreen extends React.Component {

  render = () => {
    const {children} = this.props;

    return (
        <SearchScreen resource={"userAccount"}
                      search={"findAllByEmail"}>
          <UserAccountSearch/>
          <UserAccountSearchResult>
            {children}
          </UserAccountSearchResult>
        </SearchScreen>
    );
  }

}

export default UserAccountSearchScreen;
