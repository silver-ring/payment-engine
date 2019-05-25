import TablePagination from "@material-ui/core/TablePagination/TablePagination";
import React from "react";

class SimpleDataTablePagination extends React.Component {

  render = () => {

    const {totalElements, pageSize, pageNumber, onChangePage, onChangeRowsPerPage} = this.props;

    return (
        <TablePagination
            count={totalElements}
            rowsPerPage={pageSize}
            page={pageNumber}
            backIconButtonProps={{'aria-label': 'Previous Page'}}
            nextIconButtonProps={{'aria-label': 'Next Page'}}
            onChangePage={onChangePage}
            onChangeRowsPerPage={onChangeRowsPerPage}/>);
  }

}

export default SimpleDataTablePagination;
