import React from 'react';
import PropTypes from 'prop-types';
import Table from '@material-ui/core/Table';
import TablePagination from '@material-ui/core/TablePagination';
import Paper from '@material-ui/core/Paper';
import SelectableDataTableHead from "./SelectableDataTableHead";
import SelectableDataTableBody from "./SelectableDataTableBody";

class SelectableDataTable extends React.Component {
  state = {
    order: 'asc',
    orderBy: 'calories',
    page: 0,
    rowsPerPage: 5,
  };

  handleRequestSort = (event, property) => {
    const orderBy = property;
    let order = 'desc';

    if (this.state.orderBy === property && this.state.order === 'desc') {
      order = 'asc';
    }

    this.setState({order, orderBy});
  };

  handleSelectAllClick = event => {
    if (event.target.checked) {
      const allDataIds = this.props.getData().map(n => n.id);
      this.props.setSelected(allDataIds);
      return;
    }
    this.props.setSelected([]);
  };

  handleClick = (event, id) => {
    const selected = this.props.getSelected();
    const selectedIndex = selected.indexOf(id);
    let newSelected = [];

    if (selectedIndex === -1) {
      newSelected = newSelected.concat(selected, id);
    } else if (selectedIndex === 0) {
      newSelected = newSelected.concat(selected.slice(1));
    } else if (selectedIndex === selected.length - 1) {
      newSelected = newSelected.concat(selected.slice(0, -1));
    } else if (selectedIndex > 0) {
      newSelected = newSelected.concat(
          selected.slice(0, selectedIndex),
          selected.slice(selectedIndex + 1),
      );
    }
    this.props.setSelected(newSelected);
  };

  handleChangePage = (event, page) => {
    this.setState({page});
  };

  handleChangeRowsPerPage = event => {
    this.setState({rowsPerPage: event.target.value});
  };

  handlerRawSelect = id => {
    return this.props.getSelected().indexOf(id) !== -1
  };

  render() {

    const {getData, getSelected, rows} = this.props;
    const {order, orderBy, rowsPerPage, page} = this.state;

    const data = getData();
    const emptyRows = rowsPerPage - Math.min(rowsPerPage,
        data.length - page * rowsPerPage);

    const selected = getSelected();

    return (
        <Paper>
          {
            this.props.children
          }
          <Table aria-labelledby="tableTitle">
            <SelectableDataTableHead
                rows={rows}
                numSelected={selected.length}
                order={order}
                orderBy={orderBy}
                onSelectAllClick={this.handleSelectAllClick}
                onRequestSort={this.handleRequestSort}
                rowCount={data.length}
            />
            <SelectableDataTableBody
                rows={rows}
                data={data}
                order={order}
                orderBy={orderBy}
                page={page}
                rowsPerPage={rowsPerPage}
                emptyRows={emptyRows}
                onRowSelected={this.handlerRawSelect}
                onRowClick={this.handleClick}
            />
          </Table>
          <TablePagination
              component="div"
              count={data.length}
              rowsPerPage={rowsPerPage}
              page={page}
              backIconButtonProps={{
                'aria-label': 'Previous Page',
              }}
              nextIconButtonProps={{
                'aria-label': 'Next Page',
              }}
              onChangePage={this.handleChangePage}
              onChangeRowsPerPage={this.handleChangeRowsPerPage}/>
        </Paper>
    );
  }
}

SelectableDataTable.propTypes = {
  setSelected: PropTypes.func.isRequired,
  getSelected: PropTypes.func.isRequired,
  getData: PropTypes.func.isRequired,
};

export default SelectableDataTable;
