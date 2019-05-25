import React, {Fragment} from "react";
import Grid from "@material-ui/core/Grid/Grid";
import Typography from "@material-ui/core/Typography/Typography";
import UserAccountSection from "./UserAccountSection";

class UserAccountCard extends React.Component {

  render = () => {

    const {data} = this.props;
    return (
        <Fragment>
          <Grid container justify="space-evenly"
                spacing={16}>
            <Grid item>
              <Typography variant={"title"} color={"primary"}>
                {data.email}
              </Typography>
            </Grid>
            <Grid item>
              <Grid container spacing={24}>
                <UserAccountSection data={data}/>
              </Grid>
            </Grid>
          </Grid>
        </Fragment>
    )
  }

}

export default UserAccountCard;
