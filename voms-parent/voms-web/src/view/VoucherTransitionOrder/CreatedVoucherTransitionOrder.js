import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import withStyles from "@material-ui/core/styles/withStyles";
import CreatedVoucherActivationBulkOrder
  from "./CreatedVoucherActivationBulkOrder/CreatedVoucherActivationBulkOrder";
import CreatedVoucherActivationListOrder
  from "./CreatedVoucherActivationListOrder/CreatedVoucherActivationListOrder";
import CreatedVoucherDestroyListOrder
  from "./CreatedVoucherDestroyListOrder/CreatedVoucherDestroyListOrder";
import CreatedVoucherDestroyBulkOrder
  from "./CreatedVoucherDestroyBulkOrder/CreatedVoucherDestroyBulkOrder";

const styles = theme => ({
  root: {
    flexGrow: 1,
    width: '100%',
    backgroundColor: theme.palette.background.paper,
  },
});

class CreatedVoucherTransitionOrder extends React.Component {
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
              <Tab value="1" label="Created Voucher Activation Bulk Order"/>
              <Tab value="2" label="Created Voucher Activation List Order"/>
              <Tab value="3" label="Created Voucher Destroy List Order"/>
              <Tab value="4" label="Created Voucher Destroy List Order"/>
            </Tabs>
          </AppBar>
          {value === '1' && <CreatedVoucherActivationBulkOrder/>}
          {value === '2' && <CreatedVoucherActivationListOrder/>}
          {value === '3' && <CreatedVoucherDestroyListOrder/>}
          {value === '4' && <CreatedVoucherDestroyBulkOrder/>}
        </div>
    );
  }
}

export default withStyles(styles)(CreatedVoucherTransitionOrder);
