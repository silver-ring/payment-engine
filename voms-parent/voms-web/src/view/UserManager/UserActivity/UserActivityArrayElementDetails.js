import React from "react";
import DialogTitle from "@material-ui/core/DialogTitle";
import Dialog from "@material-ui/core/Dialog";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import ListItemText from "@material-ui/core/ListItemText";
import ListItem from "@material-ui/core/ListItem";

function DataTable(props) {

  const {data} = props;

  return (<Table>
        <TableHead>
          <TableRow>
            <HeadersColumns data={data[0]}/>
          </TableRow>
        </TableHead>
        <TableBody>
          <RowsDetails data={data}/>
        </TableBody>
      </Table>
  )
}

function HeadersColumns(props) {

  const {data} = props;

  return Object.keys(data).map(
      dataHeaderKey => {
        return (
            <TableCell
                key={dataHeaderKey}>{dataHeaderKey}</TableCell>
        )
      }
  );
}

function RowsDetails(props) {

  const {data} = props;

  return Object.keys(data).map(
      dataRowKey => {
        return (<TableRow key={data[dataRowKey]["id"]}>
              {Object.keys(
                  data[dataRowKey]).map(
                  dataColumnKey => {
                    return (
                        <TableCell
                            row={data[dataRowKey][dataColumnKey]}
                            component="th"
                            scope="row">
                          {data[dataRowKey][dataColumnKey]}
                        </TableCell>
                    )
                  })}
            </TableRow>
        )
      })
}

class UserActivityArrayElementDetails extends React.Component {

  render = () => {

    const {open, onClose, id, data, title} = this.props;

    return (
        <Dialog fullWidth={true} maxWidth={"sm"} onClose={onClose}
                open={open}>
          <DialogTitle id={id}>{title}</DialogTitle>
          {data == null || data.length === 0 ?
              <ListItem button>
                <ListItemText
                    inset
                    primary={"No Data"}/>
              </ListItem>
              :
              <DataTable data={data}/>
          }

        </Dialog>
    )

  }
}

export default UserActivityArrayElementDetails;
