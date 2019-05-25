import React, {Fragment} from 'react';
import PropTypes from 'prop-types';
import Select from 'react-select';
import {withStyles} from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import MenuItem from '@material-ui/core/MenuItem';
import {emphasize} from '@material-ui/core/styles/colorManipulator';
import * as halfred from "halfred";
import AlertMessage from "../component/AlertMessage";
import LinearProgress from "@material-ui/core/LinearProgress/LinearProgress";
import {TextValidator} from "react-material-ui-form-validator";
import {convertJsonToQueryParam} from "../util/ConvertUtils";
import {handleRemoteErrorResponse} from "../util/ErrorHandlerUtils";
import restResourceInstance from "../axios/RestResourceInstance";

const styles = theme => ({
  root: {
    flexGrow: 1,
    height: 250,
  },
  input: {
    display: 'flex',
    padding: 0,
  },
  valueContainer: {
    display: 'flex',
    flexWrap: 'wrap',
    flex: 1,
    alignItems: 'center',
  },
  chip: {
    margin: `${theme.spacing.unit / 2}px ${theme.spacing.unit / 4}px`,
  },
  chipFocused: {
    backgroundColor: emphasize(
        theme.palette.type === 'light' ? theme.palette.grey[300]
            : theme.palette.grey[700],
        0.08,
    ),
  },
  noOptionsMessage: {
    padding: `${theme.spacing.unit}px ${theme.spacing.unit * 2}px`,
  },
  singleValue: {
    fontSize: 16,
  },
  placeholder: {
    position: 'absolute',
    left: 2,
    fontSize: 16,
  },
  paper: {
    position: 'absolute',
    zIndex: 1,
    marginTop: theme.spacing.unit,
    left: 0,
    right: 0,
  },
  divider: {
    height: theme.spacing.unit * 2,
  },
});

function NoOptionsMessage(props) {
  return (
      <Typography
          color="textSecondary"
          className={props.selectProps.classes.noOptionsMessage}
          {...props.innerProps}
      >
        {props.children}
      </Typography>
  );
}

function inputComponent({inputRef, ...props}) {
  return <div ref={inputRef} {...props} />
}

function Control(props) {
  return (
      <TextValidator
          fullWidth
          InputProps={{
            inputComponent,
            inputProps: {
              className: props.selectProps.classes.input,
              inputRef: props.innerRef,
              children: props.children,
              ...props.innerProps,
            },
          }}
          {...props.selectProps.textFieldProps}
      />
  );
}

function Option(props) {
  return (
      <MenuItem
          buttonRef={props.innerRef}
          selected={props.isFocused}
          component="div"
          style={{
            fontWeight: props.isSelected ? 500 : 400,
          }}
          {...props.innerProps}
      >
        {props.children}
      </MenuItem>
  );
}

function Placeholder(props) {
  return (
      <Typography
          color="textSecondary"
          className={props.selectProps.classes.placeholder}
          {...props.innerProps}
      >
        {props.children}
      </Typography>
  );
}

function SingleValue(props) {
  return (
      <Typography
          className={props.selectProps.classes.singleValue} {...props.innerProps}>
        {props.children}
      </Typography>
  );
}

function ValueContainer(props) {
  return <div
      className={props.selectProps.classes.valueContainer}>{props.children}</div>
}

function Menu(props) {
  return (
      <Paper square
             className={props.selectProps.classes.paper} {...props.innerProps}>
        {props.children}
      </Paper>
  );
}

const components = {
  Control,
  Menu,
  NoOptionsMessage,
  Option,
  Placeholder,
  SingleValue,
  ValueContainer,
};

class SingleValueSelect extends React.Component {

  state = {
    selected: null,
    suggestions: [],
    alertMessage: (<Fragment/>)
  };

  componentDidMount = () => {
    this.handleLoadData();
  };

  handleChange = selected => {
    this.setState({
      selected: selected
    });
  };

  handleLoadData = () => {

    const {
      resource, property, defaultValue, page,
      size, sort, search, embedded
    } = this.props;

    let newEmbedded = [resource];

    if (embedded != null) {
      newEmbedded = embedded;
    }

    let newPage = 0;
    if (page != null) {
      newPage = page;
    }

    let newSize = 100;
    if (size != null) {
      newSize = size;
    }

    let newSort = {property: property, direction: "asc"};

    if (sort != null) {
      newSort = sort;
    }

    let newSearch = {status: "ACTIVE"};
    if (search != null) {
      newSearch = search;
    }

    const pageNumberParm = "page=" + newPage + "&";
    const pageSizeParm = "size=" + newSize + "&";
    const sortByParm = "sort=" + newSort.property + "," + newSort.direction;

    const searchParm = convertJsonToQueryParam(newSearch);

    const url = resource + "?" + searchParm + pageNumberParm + pageSizeParm
        + sortByParm;

    const suggestions = [];
    let selected = null;
    let errorAlertMessage = (<Fragment/>);

    restResourceInstance.get(url)
    .then((suggestionsResponse) => {
      newEmbedded.map((key) => {
        const suggestionsResources = halfred.parse(
            suggestionsResponse.data).embeddedArray(key);
        suggestionsResources.map(suggestionsResource => {
          const suggestionValue = halfred.parse(suggestionsResource).link(
              "self")["href"];
          const suggestionLabel = suggestionsResource[property];
          suggestions.push({value: suggestionValue, label: suggestionLabel});
        });
      });
      if (defaultValue != null) {
        return restResourceInstance.get(defaultValue);
      }
    })
    .then((defaultResponse) => {
      if (defaultResponse != null) {
        const defaultResource = defaultResponse.data;
        const defaultValue = halfred.parse(defaultResource).link(
            "self")["href"];
        const defaultLabel = defaultResource[property];
        const status = defaultResource["status"];

        if ((status == null) || (status != null && status === "ACTIVE")) {
          selected = {value: defaultValue, label: defaultLabel};
        }
      }
    })
    .catch((error) => {
      // in case of default is empty do nothing
      if (error.response != null && error.response.status === 404) {
        return;
      }
      const responseErrorMessage = handleRemoteErrorResponse(error);
      errorAlertMessage = (
          <AlertMessage variant="error" message={responseErrorMessage}/>);
    })
    .finally(() => {
      this.setState({
        suggestions: suggestions,
        loading: false,
        selected: selected,
        alertMessage: errorAlertMessage
      });
    });
    this.setState({
      loading: true,
      alertMessage: ""
    });
  };

  render() {

    if (this.state.loading) {
      return (<LinearProgress/>);
    }

    const {classes, theme, field, label, required} = this.props;
    const {selected, suggestions, alertMessage} = this.state;

    const selectStyles = {
      input: base => ({
        ...base,
        color: theme.palette.text.primary,
        '& input': {
          font: 'inherit',
        },
      }),
    };

    const inputValue = selected == null ? null : selected.value;

    const validatorsValue = [];
    const errorMessagesValue = [];
    let newLabel = label;

    if (required != null) {
      validatorsValue.push("required");
      errorMessagesValue.push("field is required");
      newLabel = label + " *";
    }

    let textFieldPros = {
      label: newLabel, name: field, value: inputValue,
      validators: validatorsValue, errorMessages: errorMessagesValue
    };

    if (inputValue != null && inputValue !== []) {
      textFieldPros = {
        label: newLabel,
        InputLabelProps: {shrink: true},
        name: field,
        value: inputValue,
        validators: validatorsValue,
        errorMessages: errorMessagesValue
      };
    }

    return (
        <Fragment>
          {alertMessage}
          <input type="hidden" name={field} value={inputValue}/>
          <Select
              textFieldProps={textFieldPros}
              classes={classes}
              styles={selectStyles}
              options={suggestions}
              components={components}
              value={selected}
              onChange={this.handleChange}
              placeholder={""}
          />
        </Fragment>
    );
  }
}

SingleValueSelect.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired,
};

export default withStyles(styles, {withTheme: true})(SingleValueSelect);
