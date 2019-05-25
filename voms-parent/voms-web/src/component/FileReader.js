import React, {Component, Fragment} from "react";
import FileReaderInput from "react-file-reader-input";
import Button from "@material-ui/core/Button/Button";
import {TextValidator} from "react-material-ui-form-validator";
import CircularProgress
  from "@material-ui/core/CircularProgress/CircularProgress";
import CheckIcon from '@material-ui/icons/Check';
import CloudUploadIcon from '@material-ui/icons/CloudUpload';
import withStyles from "@material-ui/core/styles/withStyles";
import green from '@material-ui/core/colors/green';
import classNames from 'classnames';
import Grid from "@material-ui/core/Grid/Grid";

const styles = theme => ({
  buttonSuccess: {
    backgroundColor: green[500],
    '&:hover': {
      backgroundColor: green[700],
    },
  },
});

class FileReader extends Component {

  state = {
    fileData: [],
    fileName: "",
    loading: false,
    success: false
  };

  componentWillMount = () => {
    const fileName = this.props.defaultFileName;
    const fileData = this.props.defaultFileData;
    if (fileData != null) {
      this.setState({
        fileName: fileName,
        fileData: fileData
      })
    }
  };

  handleChange = (e, results) => {
    results.forEach(result => {
      if (result == null) {
        return;
      }
      const [e, file] = result;
      const fileData = new Buffer(e.target.result).toString('base64');
      const fileName = file.name;
      this.setState({
        fileName: fileName,
        fileData: fileData,
        loading: false,
        success: true
      });
    });
  };

  render = () => {

    const {fileDataField, fileNameField, label, required, classes, ...rest} = this.props;
    const {fileData, fileName, loading, success} = this.state;

    const validatorsValue = [];
    const errorMessagesValue = [];
    let newLabel = label;

    if (required != null) {
      validatorsValue.push("required");
      errorMessagesValue.push("field is required");
      newLabel = label + " *";
    }

    const buttonClassname = classNames({
      [classes.buttonSuccess]: success
    });

    return (
        <Fragment>
          <input type="hidden" id={fileDataField} name={fileDataField}
                 value={fileData}/>
          <FileReaderInput as="text" id={"fileReaderInput-" + fileNameField}
                           onChange={this.handleChange}>
            <Grid item xs={12}>
              <TextValidator
                  {...rest}
                  readonly
                  id={fileNameField}
                  label={newLabel}
                  value={fileName}
                  margin="normal"
                  name={fileNameField}
                  validators={validatorsValue}
                  errorMessages={errorMessagesValue}
                  fullWidth
              />
            </Grid>
            <Grid item xs={12}>
              <Button disabled={loading} variant="contained" color={"primary"}
                      className={buttonClassname}
                      component="span">
                {loading ? (<CircularProgress size={20}/>) : success ?
                    <CheckIcon/> : <CloudUploadIcon/>}
                {loading ? "" : "Upload"}
              </Button>
            </Grid>
          </FileReaderInput>
        </Fragment>
    )
  }
}

export default withStyles(styles)(FileReader);
