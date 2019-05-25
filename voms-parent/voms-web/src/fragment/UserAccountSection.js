import React, {Fragment} from "react";
import Grid from "@material-ui/core/Grid/Grid";
import TextField from "@material-ui/core/TextField/TextField";

class UserAccountSection extends React.Component {

  render = () => {

    const {data} = this.props;
    return (
        <Fragment>
          <Grid item xs={12}>
            <TextField
                readonly={true}
                id="email"
                name="email"
                label="Email"
                value={data.email}
                InputLabelProps={{shrink: true}}
                fullWidth
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
                readonly={true}
                id="firstName"
                name="firstName"
                label="First Name"
                value={data.firstName}
                InputLabelProps={{shrink: true}}
                fullWidth
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
                readonly={true}
                id="lastName"
                name="lastName"
                label="Last Name"
                value={data.lastName}
                InputLabelProps={{shrink: true}}
                fullWidth
            />
          </Grid>
          <Grid item xs={12}>
            <TextField
                readonly={true}
                id="userAccountStatus"
                name="userAccountStatus"
                label="User Account Status"
                value={data.userAccountStatus}
                InputLabelProps={{shrink: true}}
                fullWidth
            />
          </Grid>
        </Fragment>
    )
  }

}

export default UserAccountSection;
