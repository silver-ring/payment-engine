import React from "react";
import TableHead from "@material-ui/core/TableHead/TableHead";
import TableRow from "@material-ui/core/TableRow/TableRow";
import TableCell from "@material-ui/core/TableCell/TableCell";
import Checkbox from "@material-ui/core/Checkbox/Checkbox";
import Tooltip from "@material-ui/core/Tooltip/Tooltip";
import TableSortLabel from "@material-ui/core/TableSortLabel/TableSortLabel";
import PropTypes from "prop-types";

class SelectableDataTableHead extends React.Component {
  createSortHandler = property => event => {
    this.props.onRequestSort(event, property);
  };

  render() {
    const {onSelectAllClick, order, orderBy, numSelected, rowCount, rows} = this.props;

    return (
        <TableHead>
          <TableRow>
            <TableCell padding="checkbox">
              <Checkbox
                  indeterminate={numSelected > 0 && numSelected < rowCount}
                  checked={numSelected === rowCount}
                  onChange={onSelectAllClick}
              />
            </TableCell>
            {rows.map(row => {
              return (
                  <TableCell
                      key={row.id}
                      sortDirection={orderBy === row.id ? order : false}
                  >
                    <Tooltip
                        title="Sort"
                        placement={"bottom-end"}
                        enterDelay={300}
                    >
                      <TableSortLabel
                          active={orderBy === row.id}
                          direction={order}
                          onClick={this.createSortHandler(row.id)}
                      >
                        {row.label}
                      </TableSortLabel>
                    </Tooltip>
                  </TableCell>
              );
            }, this)}
          </TableRow>
        </TableHead>
    );
  }
}

SelectableDataTableHead.propTypes = {
  numSelected: PropTypes.number.isRequired,
  onRequestSort: PropTypes.func.isRequired,
  onSelectAllClick: PropTypes.func.isRequired,
  order: PropTypes.string.isRequired,
  orderBy: PropTypes.string.isRequired,
  rowCount: PropTypes.number.isRequired,
};

export default SelectableDataTableHead;
