import React, {Fragment} from "react";
import Card from "@material-ui/core/Card/Card";
import CardContent from "@material-ui/core/CardContent/CardContent";
import Grid from "@material-ui/core/Grid/Grid";
import Typography from "@material-ui/core/Typography/Typography";
import TextField from "@material-ui/core/TextField/TextField";
import * as halfred from "halfred";
import Divider from "@material-ui/core/Divider/Divider";
import {convertJsonDateToStringDateTime} from "../../../util/ConvertUtils";
import Button from "@material-ui/core/Button";
import DialogTitle from "@material-ui/core/DialogTitle";
import Dialog from "@material-ui/core/Dialog";
import List from "@material-ui/core/List";
import UserActivityDetails from "./UserActivityDetails";

class UserActivitySearchResult extends React.Component {

  state = {
    open: []
  };

  handleDetailsClick = (id) => {
    const open = this.state.open;
    open[id] = true;
    this.setState({
      open: open
    })
  };

  handleClose = (id) => {
    const open = this.state.open;
    open[id] = false;
    this.setState({
      open: open
    })
  };

  render = () => {

    const {data} = this.props;
    const {open} = this.state;

    const userActivities = halfred.parse(data).embeddedArray("userActivities");

    return (
        <Fragment>
          {userActivities.map(row => {

            const eventTimestamp = convertJsonDateToStringDateTime(
                row.eventTimestamp);

            const data = row["data"];

            const id = halfred.parse(row).link("self")["href"];

            return (
                <Card>
                  <CardContent>
                    <Grid container justify="space-evenly"
                          spacing={16}>
                      <Grid item>
                        <Typography variant={"title"} color={"primary"}>
                          {row.email + " (" + eventTimestamp + ")"}
                        </Typography>
                      </Grid>
                      <Grid item>
                        <Grid container spacing={24}>
                          <Grid item xs={12}>
                            <Divider/>
                          </Grid>
                          <Grid item xs={12}>
                            <TextField
                                readonly={true}
                                id="email"
                                name="email"
                                label="Email"
                                value={row.email}
                                InputLabelProps={{shrink: true}}
                                fullWidth
                            />
                          </Grid>
                          <Grid item xs={12}>
                            <TextField
                                readonly={true}
                                id="target"
                                name="target"
                                label="Target"
                                value={row.target}
                                InputLabelProps={{shrink: true}}
                                fullWidth
                            />
                          </Grid>
                          <Grid item xs={12}>
                            <TextField
                                readonly={true}
                                id="userActivityType"
                                name="userActivityType"
                                label="User Activity Type"
                                value={row.userActivityType}
                                InputLabelProps={{shrink: true}}
                                fullWidth
                            />
                          </Grid>

                          <Grid item xs={12}>
                            <Button
                                onClick={() => this.handleDetailsClick(id)}
                                fullWidth>
                              <Typography
                                  variant={"caption"}>details...</Typography>
                            </Button>
                          </Grid>

                          <Dialog fullWidth={true} maxWidth={"sm"}
                                  onClose={() => this.handleClose(id)}
                                  open={open[id]}>
                            <DialogTitle id={"dialog-title"
                            + id}>{row.target}</DialogTitle>
                            <div>
                              <List aria-orientation={"horizontal"}>
                                <UserActivityDetails id={id} data={data}/>
                              </List>
                            </div>
                          </Dialog>

                        </Grid>
                      </Grid>
                    </Grid>
                  </CardContent>
                </Card>
            )
          })
          }
        </Fragment>
    )
  }
}

export default UserActivitySearchResult;
