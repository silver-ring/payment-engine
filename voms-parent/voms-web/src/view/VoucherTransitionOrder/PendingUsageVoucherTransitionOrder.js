import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import withStyles from "@material-ui/core/styles/withStyles";
import PendingUsageVoucherActivationBulkOrder
  from "./PendingUsageVoucherActivationBulkOrder/PendingUsageVoucherActivationBulkOrder";
import PendingUsageVoucherActivationListOrder
  from "./PendingUsageVoucherActivationListOrder/PendingUsageVoucherActivationListOrder";
import PendingUsageVoucherBlockingBulkOrder
  from "./PendingUsageVoucherBlockingBulkOrder/PendingUsageVoucherBlockingBulkOrder";
import PendingUsageVoucherBlockingListOrder
  from "./PendingUsageVoucherBlockingListOrder/PendingUsageVoucherBlockingListOrder";
import PendingUsageVoucherUsingBulkOrder
  from "./PendingUsageVoucherUsingBulkOrder/PendingUsageVoucherUsingBulkOrder";
import PendingUsageVoucherUsingListOrder
  from "./PendingUsageVoucherUsingListOrder/PendingUsageVoucherUsingListOrder";

const styles = theme => ({
  root: {
    flexGrow: 1,
    width: '100%',
    backgroundColor: theme.palette.background.paper,
  },
});

class PendingUsageVoucherTransitionOrder extends React.Component {
  state = {
    value: '1',
  };

  handleChange = (event, value) => {
    this.setState({value});
  };

  render() {
    const {classes} = this.props;
    const {value} = this.state;

    return (
        <div className={classes.root}>
          <AppBar position="static">
            <Tabs value={value}
                  onChange={this.handleChange}
                  scrollable>
              <Tab value="1"
                   label="Pending Usage Voucher Activation Bulk Order"/>
              <Tab value="2"
                   label="Pending Usage Voucher Activation List Order"/>
              <Tab value="3" label="Pending Usage Voucher Blocking Bulk Order"/>
              <Tab value="4" label="Pending Usage Voucher Blocking List Order"/>
              <Tab value="5" label="Pending Usage Voucher Using Bulk Order"/>
              <Tab value="6" label="Pending Usage Voucher Using List Order"/>
            </Tabs>
          </AppBar>
          {value === '1' && <PendingUsageVoucherActivationBulkOrder/>}
          {value === '2' && <PendingUsageVoucherActivationListOrder/>}
          {value === '3' && <PendingUsageVoucherBlockingBulkOrder/>}
          {value === '4' && <PendingUsageVoucherBlockingListOrder/>}
          {value === '5' && <PendingUsageVoucherUsingBulkOrder/>}
          {value === '6' && <PendingUsageVoucherUsingListOrder/>}
        </div>
    );
  }
}

export default withStyles(styles)(PendingUsageVoucherTransitionOrder);
