import React from "react";
import Typography from "@material-ui/core/Typography";
import ActiveRechargeChart from "./ActiveRechargeChart";
import AllVouchersChart from "./AllVouchersChart";

class Dashboard extends React.Component {

  render = () => {
    return (
        <React.Fragment>
          <Typography variant="h4" gutterBottom component="h2">
            Active Vouchers/Recharge
          </Typography>
          <Typography component="div">
            <ActiveRechargeChart/>
          </Typography>

          <Typography variant="h4" gutterBottom component="h2">
            All Vouchers
          </Typography>
          <Typography component="div">
            <AllVouchersChart/>
          </Typography>

        </React.Fragment>
    )
  }

}

export default Dashboard;
