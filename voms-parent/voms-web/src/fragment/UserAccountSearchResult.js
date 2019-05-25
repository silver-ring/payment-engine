import React, {Fragment} from "react";
import Card from "@material-ui/core/Card/Card";
import CardContent from "@material-ui/core/CardContent/CardContent";
import * as halfred from "halfred";
import CardActions from "@material-ui/core/CardActions";
import UserAccountCard from "./UserAccountCard";

class UserAccountSearchResult extends React.Component {

  render = () => {

    const {data, onAction} = this.props;
    const userAccounts = halfred.parse(data).embeddedArray(
        "userAccountDtoes");

    return (
        <Fragment>
          {userAccounts.map(row => {
            const id = halfred.parse(row).link("self")["href"];
            const actions = React.cloneElement(this.props.children,
                {data: row, onAction: onAction});
            return (
                <Card>
                  <CardContent>
                    <UserAccountCard data={row}/>
                  </CardContent>
                  <CardActions style={{float: 'right'}}>
                    {actions}
                  </CardActions>
                </Card>
            )
          })
          }
        </Fragment>
    )
  }

}

export default UserAccountSearchResult;
