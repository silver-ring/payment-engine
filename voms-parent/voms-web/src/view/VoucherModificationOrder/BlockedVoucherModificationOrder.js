import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import withStyles from "@material-ui/core/styles/withStyles";
import BlockedVoucherModificationBulkOrder
  from "./BlockedVoucherModificationBulkOrder/BlockedVoucherModificationBulkOrder";
import BlockedVoucherModificationListOrder
  from "./BlockedVoucherModificationListOrder/BlockedVoucherModificationListOrder";

const styles = theme => ({
  root: {
    flexGrow: 1,
    width: '100%',
    backgroundColor: theme.palette.background.paper,
  },
});

class BlockedVoucherModificationOrder extends React.Component {
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
              <Tab value="1" label="Blocked Voucher Modification Bulk Order"/>
              <Tab value="2" label="Blocked Voucher Modification List Order"/>
            </Tabs>
          </AppBar>
          {value === '1' && <BlockedVoucherModificationBulkOrder/>}
          {value === '2' && <BlockedVoucherModificationListOrder/>}
        </div>
    );
  }
}

export default withStyles(styles)(BlockedVoucherModificationOrder);
