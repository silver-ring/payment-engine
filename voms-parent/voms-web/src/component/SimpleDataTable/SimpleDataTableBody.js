import TableRow from "@material-ui/core/TableRow/TableRow";
import TableCell from "@material-ui/core/TableCell/TableCell";
import TableBody from "@material-ui/core/TableBody/TableBody";
import React, {Component} from "react";
import * as halfred from "halfred";
import LinearProgress from "@material-ui/core/LinearProgress/LinearProgress";

class SimpleDataTableBody extends Component {

  render = () => {

    const {selected, rows, data, pageSize, onRowClick} = this.props;

    if (this.props.loading) {
      const emptyRows = pageSize * 48;
      const rowsLength = Object.keys(rows).length;
      return (
          <TableBody>
            <TableRow style={{verticalAlign: "top", height: emptyRows}}>
              <TableCell colSpan={rowsLength}><LinearProgress/></TableCell>
            </TableRow>
          </TableBody>
      )
    }

    return (<TableBody>
      {data.map(n => {
        const id = halfred.parse(n).link("self")["href"];
        const isSelectedRow = (id === selected);
        return (
            <TableRow
                hover
                onClick={event => onRowClick(id)}
                selected={isSelectedRow}
                tabIndex={-1}
                key={id}>
              {
                rows.map((key) => {
                  const value = key["id"].split('.').reduce((o, i) => o[i], n);
                  return (<TableCell padding="dense"
                                     key={id + key["id"]}>{value}</TableCell>);
                })
              }
            </TableRow>
        );
      })}
    </TableBody>)
  }
}

export default SimpleDataTableBody;
