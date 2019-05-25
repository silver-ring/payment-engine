import TableRow from "@material-ui/core/TableRow/TableRow";
import TableCell from "@material-ui/core/TableCell/TableCell";
import Checkbox from "@material-ui/core/Checkbox/Checkbox";
import TableBody from "@material-ui/core/TableBody/TableBody";
import React, {Component} from "react";
import * as halfred from "halfred";

function desc(a, b, orderBy) {
  if (b[orderBy] < a[orderBy]) {
    return -1;
  }
  if (b[orderBy] > a[orderBy]) {
    return 1;
  }
  return 0;
}

function stableSort(array, cmp) {
  const stabilizedThis = array.map((el, index) => [el, index]);
  stabilizedThis.sort((a, b) => {
    const order = cmp(a[0], b[0]);
    if (order !== 0) {
      return order;
    }
    return a[1] - b[1];
  });
  return stabilizedThis.map(el => el[0]);
}

function getSorting(order, orderBy) {
  return order === 'desc' ? (a, b) => desc(a, b, orderBy) : (a, b) => -desc(a,
      b, orderBy);
}

class SelectableDataTableBody extends Component {

  render = () => {

    const {rows, data, order, orderBy, page, rowsPerPage, emptyRows, onRowSelected, onRowClick} = this.props;

    return (<TableBody>
      {stableSort(data, getSorting(order, orderBy))
      .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
      .map(n => {
        const id = halfred.parse(n).link("self")["href"];
        const selected = onRowSelected(id);
        return (
            <TableRow
                hover
                onClick={event => onRowClick(event, id)}
                role="checkbox"
                aria-checked={selected}
                tabIndex={-1}
                key={id}
                selected={selected}>
              <TableCell padding="checkbox"><Checkbox
                  checked={selected}/></TableCell>
              {
                rows.map((key) => (<TableCell padding="dense" key={id
                + key["id"]}>{n[key["id"]]}</TableCell>))
              }
            </TableRow>
        );
      })}
      {emptyRows > 0 && (
          <TableRow style={{height: 49 * emptyRows}}>
            <TableCell colSpan={6}/>
          </TableRow>
      )}
    </TableBody>)
  }
}

export default SelectableDataTableBody;
