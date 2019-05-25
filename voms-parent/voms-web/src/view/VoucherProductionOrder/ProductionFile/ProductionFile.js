import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import ProductionFileCreationBulkOrder
  from "./ProductionFileCreationBulkOrder/ProductionFileCreationBulkOrder";
import ProductionFileCreationListOrder
  from "./ProductionFileCreationListOrder/ProductionFileCreationListOrder";
import withStyles from "@material-ui/core/styles/withStyles";

const styles = theme => ({
  root: {
    flexGrow: 1,
    width: '100%',
    backgroundColor: theme.palette.background.paper,
  },
});

class ProductionFile extends React.Component {
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
              <Tab value="1" label="Production File Creation Bulk Order"/>
              <Tab value="2" label="Production File Creation List Order"/>
            </Tabs>
          </AppBar>
          {value === '1' && <ProductionFileCreationBulkOrder/>}
          {value === '2' && <ProductionFileCreationListOrder/>}
        </div>
    );
  }
}

export default withStyles(styles)(ProductionFile);
