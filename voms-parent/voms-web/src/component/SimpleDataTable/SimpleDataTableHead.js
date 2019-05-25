import React from "react";
import TableHead from "@material-ui/core/TableHead/TableHead";
import TableRow from "@material-ui/core/TableRow/TableRow";
import TableCell from "@material-ui/core/TableCell/TableCell";
import Tooltip from "@material-ui/core/Tooltip/Tooltip";
import TableSortLabel from "@material-ui/core/TableSortLabel/TableSortLabel";
import TextField from "@material-ui/core/TextField/TextField";
import Grid from "@material-ui/core/Grid/Grid";
import SimpleTableHeadSearchOptions from "./SimpleTableHeadSearchOptions";

class SimpleDataTableHead extends React.Component {

  render() {
    const {sortDirection, sortProperty, rows, onSort, onSearch} = this.props;

    return (
        <TableHead>
          <TableRow>
            {rows.map(row => {
              return (
                  <TableCell
                      key={row.id}
                      sortDirection={sortProperty === row.id ? sortDirection
                          : false}
                  >
                    <div>
                      <Grid container spacing={8} alignItems="flex-end">
                        <Grid item>
                          <Tooltip
                              title="Sort"
                              placement={"bottom-end"}
                              enterDelay={300}
                          >
                            <TableSortLabel
                                active={sortProperty === row.id}
                                direction={sortDirection}
                                onClick={() => onSort(row.id)}
                            >
                              {row.label}
                            </TableSortLabel>
                          </Tooltip>
                        </Grid>
                        <Grid item>
                          {(row.options) ?
                              (
                                  <SimpleTableHeadSearchOptions id={row.id}
                                                                options={row.options}
                                                                onChange={onSearch}/>
                              )
                              : (<TextField type={row.type}
                                            onChange={(event) => onSearch(
                                                row.id, event.target.value)}/>)
                          }
                        </Grid>
                      </Grid>
                    </div>
                  </TableCell>
              );
            }, this)}
          </TableRow>
        </TableHead>
    );
  }
}

export default SimpleDataTableHead;
