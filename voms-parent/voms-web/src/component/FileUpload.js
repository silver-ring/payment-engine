import React from "react";
import Button from "@material-ui/core/Button/Button";
import CircularProgress
  from "@material-ui/core/CircularProgress/CircularProgress";
import CheckIcon from "@material-ui/icons/Check";
import CloudUploadIcon from "@material-ui/icons/CloudUpload";
import green from "@material-ui/core/colors/green";
import withStyles from "@material-ui/core/styles/withStyles";
import classNames from "classnames";
import Grid from "@material-ui/core/Grid/Grid";
import {TextValidator} from "react-material-ui-form-validator";
import temporaryFileResourceInstance from "../axios/FilesResourceInstance";

const styles = theme => ({

  buttonSuccess: {
    backgroundColor: green[500],
    '&:hover': {
      backgroundColor: green[700],
    },
  },

});

class FileUpload extends React.Component {

  state = {
    fileName: null,
    loading: false,
    success: false,
    originalFileName: null
  };

  componentWillMount = () => {
    const originalFileName = this.props.defaultOriginalFileName;
    const fileName = this.props.defaultFileName;
    if (fileName != null) {
      this.setState({
        originalFileName: originalFileName,
        fileName: fileName
      })
    }
  };

  handleUpload = (e) => {
    const file = e.target.files[0];
    if (file == null) {
      return;
    }
    const formData = new FormData();
    formData.append('file', file);
    temporaryFileResourceInstance.post("", formData)
    .then((response) => {
      const fileName = response.data;
      const originalFileName = file.name;
      this.setState({
        fileName: fileName,
        originalFileName: originalFileName,
        success: true,
        loading: false
      })
    })
    .catch((error) => {
      this.setState({
        fileName: null,
        originalFileName: null,
        loading: false,
        success: false
      })
    });
    this.setState({
      fileName: null,
      success: false,
      loading: true
    })
  };

  render = () => {

    const {fileName, originalFileName, loading, success, alertMessage} = this.state;
    const {fileNameField, originalFileNameField, label, required, classes, ...rest} = this.props;

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

    let textFieldPros = {};

    if (originalFileName != null) {
      textFieldPros = {shrink: true};
    }

    return (
        <Grid item xs={12}>
          <input type="hidden" id={fileNameField} name={fileNameField}
                 value={fileName}/>
          <TextValidator
              {...rest}
              readonly
              id={originalFileNameField}
              label={newLabel}
              value={originalFileName}
              margin="normal"
              name={originalFileNameField}
              validators={validatorsValue}
              errorMessages={errorMessagesValue}
              InputLabelProps={textFieldPros}
              fullWidth
          />
          <label htmlFor="contained-button-file">
            <input
                id="contained-button-file"
                multiple
                type="file"
                onChange={this.handleUpload}
                hidden={true}
            />
            <Button disabled={loading} variant="contained" color={"primary"}
                    className={buttonClassname}
                    component="span">
              {loading ? (<CircularProgress size={20}/>) : success ?
                  <CheckIcon/> : <CloudUploadIcon/>}
              {loading ? "" : "Upload"}
            </Button>
          </label>
        </Grid>
    )
  }

}

export default withStyles(styles)(FileUpload);
