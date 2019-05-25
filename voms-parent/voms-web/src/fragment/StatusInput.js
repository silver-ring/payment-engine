import InputLabel from "@material-ui/core/InputLabel/InputLabel";
import FormControlLabel
  from "@material-ui/core/FormControlLabel/FormControlLabel";
import Radio from "@material-ui/core/Radio/Radio";
import React, {Fragment} from "react";

class StatusInput extends React.Component {

  state = {
    selectedStatus: 'ACTIVE',
  };

  componentWillMount = () => {
    const selectedStatus = this.props.defaultValue;
    if (selectedStatus != null) {
      this.setState({
        selectedStatus: selectedStatus
      })
    }
  };

  handleChangeStatus = event => {
    this.setState({selectedStatus: event.target.value});
  };

  render = () => {
    const {selectedStatus} = this.state;

    return (
        <Fragment>
          <input type="hidden" name="status" value={selectedStatus}/>
          <InputLabel required style={{margin: "10px"}}>Status</InputLabel>
          <FormControlLabel
              value="ACTIVE"
              control={<Radio checked={selectedStatus === 'ACTIVE'}
                              value="ACTIVE"
                              onChange={this.handleChangeStatus}
                              color="primary"/>}
              label="Active"
          />
          <FormControlLabel
              value="INACTIVE"
              control={<Radio checked={selectedStatus === 'INACTIVE'}
                              value="INACTIVE"
                              onChange={this.handleChangeStatus}/>}
              label="Inactive"
          />
        </Fragment>
    );
  }

}

export default StatusInput;
