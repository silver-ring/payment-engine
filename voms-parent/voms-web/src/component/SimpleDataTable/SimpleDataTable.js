import React, {Fragment} from 'react';
import Table from '@material-ui/core/Table';
import Paper from '@material-ui/core/Paper';
import SimpleDataTableBody from "./SimpleDataTableBody";
import SimpleDataTableHead from "./SimpleDataTableHead";
import * as halfred from "halfred";
import AlertMessage from "../AlertMessage";
import SimpleDataTablePagination from "./SimpleDataTablePagination";
import {handleRemoteErrorResponse} from "../../util/ErrorHandlerUtils";
import restResourceInstance from "../../axios/RestResourceInstance";

function mapRowsToSearch(rows) {
  const result = rows.map((key) => {
    const id = key["id"];
    const opt = [];
    opt[id] = "";
    return opt;
  }).reduce((first, second) => ({...first, ...second}), {});
  return Object.assign({}, result);
}

class SimpleDataTable extends React.Component {
  state = {
    messages: [],
    loading: false,
    data: [],
    search: [],
    page: {size: 0, totalElements: 0, totalPages: 0, number: 0},
    sort: {property: "", direction: "asc"}
  };

  pushMessage = (message) => {
    const messages = this.state.messages;
    messages.push(message);
    this.setState({messages: messages});
  };

  componentDidMount = () => {
    const search = mapRowsToSearch(this.props.rows);
    const pageSize = 5;
    const pageNumber = 0;
    const property = this.props.rows[0].id;
    const direction = "asc";
    const sort = {property: property, direction: direction};
    this.handleLoadData(pageNumber, pageSize, sort, search);
  };

  handleOnSearch = (property, value) => {
    const search = this.state.search;
    search[property] = value;
    const pageNumber = this.state.page.number;
    const pageSize = this.state.page.size;
    const sort = this.state.sort;
    this.handleLoadData(pageNumber, pageSize, sort, search);
  };

  handleSort = (property) => {
    const oldSort = this.state.sort;
    let direction = 'asc';
    if (oldSort.property === property && oldSort.direction === 'asc') {
      direction = 'desc';
    }
    const newSort = {property: property, direction: direction};
    const pageNumber = this.state.page.number;
    const pageSize = this.state.page.size;
    const search = this.state.search;
    this.handleLoadData(pageNumber, pageSize, newSort, search);
  };

  handleChangePageNumber = (pageNumber) => {
    const pageSize = this.state.page.size;
    const sort = this.state.sort;
    const search = this.state.search;
    this.handleLoadData(pageNumber, pageSize, sort, search);
  };

  handleChangePageSize = event => {
    const pageNumber = this.state.page.number;
    const sort = this.state.sort;
    const pageSize = event.target.value;
    const search = this.state.search;
    this.handleLoadData(pageNumber, pageSize, sort, search);
  };

  handleClick = (id) => {
    let selected = this.props.selected;
    if (selected === id) {
      selected = null;
    } else {
      selected = id;
    }
    this.props.onSelect(selected);
  };

  handleRefresh = () => {
    const pageNumber = this.state.page.number;
    const pageSize = this.state.page.size;
    const sort = this.state.sort;
    const search = this.state.search;
    this.handleLoadData(pageNumber, pageSize, sort, search);
  };

  handleLoadData = (pageNumber, pageSize, sort, search) => {

    const resource = this.props.resource;

    const pageNumberParm = "page=" + pageNumber + "&";
    const pageSizeParm = "size=" + pageSize + "&";

    const sortByParm = "sort=" + sort.property + "," + sort.direction;

    const searchParm = Object.keys(search).map((key) => {
      const val = search[key];
      return key + "=" + val + "&";
    }).reduce(((first, second) => first + second), "");

    const url = resource + "?" + searchParm + pageNumberParm + pageSizeParm
        + sortByParm;

    this.setState({
      loading: true
    });

    restResourceInstance.get(url)
    .then((response) => {
      const data = halfred.parse(response.data).embeddedArray(resource);
      const page = response.data.page;
      this.setState({
        loading: false,
        data: data,
        page: page,
        sort: sort,
        search: search
      });
    }).catch((error) => {
      const responseErrorMessage = handleRemoteErrorResponse(error);
      const errorAlertMessage = (
          <AlertMessage variant="error" message={responseErrorMessage}/>);
      this.pushMessage(errorAlertMessage);
      this.setState({
        loading: false
      });
    });
  };

  render() {

    const {selected, rows} = this.props;
    const {sort, page, data, loading} = this.state;

    const paperWidth = (rows.length * 30) + "%";

    return (
        <Fragment>
          {this.state.messages.map(message => (message))}
          <Paper style={{width: paperWidth}}>
            {
              this.props.children
            }
            <Table aria-labelledby="tableTitle">
              <SimpleDataTableHead rows={rows}
                                   sortDirection={sort.direction}
                                   sortProperty={sort.property}
                                   onSort={this.handleSort}
                                   onSearch={this.handleOnSearch}
              />
              <SimpleDataTableBody
                  loading={loading}
                  selected={selected}
                  rows={rows}
                  data={data}
                  pageSize={page.size}
                  emptyRows={0}
                  onRowClick={this.handleClick}
              />
            </Table>
            <SimpleDataTablePagination
                totalElements={page.totalElements}
                pageSize={page.size}
                pageNumber={page.number}
                onChangePage={(event,
                    pageNumber) => this.handleChangePageNumber(
                    pageNumber)}
                onChangeRowsPerPage={this.handleChangePageSize}
            />
          </Paper>
        </Fragment>
    );
  }
}

export default SimpleDataTable;
