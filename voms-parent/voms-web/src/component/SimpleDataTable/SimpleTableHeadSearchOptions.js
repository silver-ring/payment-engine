import React from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';

const styles = theme => ({
  root: {
    display: 'flex',
    flexWrap: 'wrap',
  },
  formControl: {
    margin: theme.spacing.unit,
    minWidth: 120,
  },
  selectEmpty: {
    marginTop: theme.spacing.unit * 2,
  },
});

class SimpleTableHeadSearchOptions extends React.Component {
  state = {
    selectedValue: "",
  };

  handleChange = event => {
    const value = event.target.value;
    const id = this.props.id;
    this.setState({selectedValue: value});
    this.props.onChange(id, value);
  };

  render() {
    const {options, classes} = this.props;
    const {selectedValue} = this.state;

    return (
        <form className={classes.root} autoComplete="off">
          <FormControl className={classes.formControl}>
            <Select
                value={selectedValue}
                onChange={this.handleChange}
                inputProps={{
                  name: 'age',
                  id: 'age-simple',
                }}
            >
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
              {
                options.map((key) => (<MenuItem key={key["id"]}
                                                value={key["id"]}>{key["label"]}</MenuItem>))
              }
            </Select>
          </FormControl>
        </form>
    );
  }
}

SimpleTableHeadSearchOptions.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SimpleTableHeadSearchOptions);
