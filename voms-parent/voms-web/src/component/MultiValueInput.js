import ChipInput from "material-ui-chip-input";
import React, {Fragment} from "react";

class MultiValueInput extends React.Component {

  state = {
    items: []
  };

  handleChangeItems = (chips) => {
    this.setState({
      items: chips
    });
  };

  render = () => {

    const {field, type, defaultValue, label} = this.props;
    const {items} = this.state;

    return (
        <Fragment>
          {
            items.map(key => (
                <input type={"hidden"} name={field + "[]"} value={key}/>
            ))
          }
          <ChipInput
              label={label}
              fullWidth
              defaultValue={defaultValue}
              InputProps={{type: type}}
              onChange={(chips) => this.handleChangeItems(chips)}
          />
        </Fragment>
    )
  }
}

export default MultiValueInput;
