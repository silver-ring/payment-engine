import Grid from "@material-ui/core/Grid/Grid";
import React from "react";
import StatusInput from "../../../fragment/StatusInput";
import ValidateableTextField from "../../../component/ValidateableTextField";
import FileReader from "../../../component/FileReader";
import OptionalControl from "../../../component/OptionalControl";

class EditVoucherPrinter extends React.Component {

  render = () => {

    const {data} = this.props;

    return (
        <React.Fragment>
          <Grid item xs={12}>
            <ValidateableTextField
                defaultValue={data.name}
                id="name"
                name="name"
                label="Name"
                fullWidth
                validators={['required']}
            />
          </Grid>
          <Grid item xs={12}>
            <FileReader required label={"Encryption Key"}
                        fileNameField={"encryptionKeyFileName"}
                        fileDataField={"encryptionKeyFileData"}
                        defaultFileName={data.encryptionKeyFileName}
                        defaultFileData={data.encryptionKeyFileData}/>
          </Grid>
          <Grid item xs={12}>
            <OptionalControl defaultValue={data.remoteFileTransferUrl != null}
                             label={"Add Remote File Transfer Url"}
                             field={"addRemoteFileTransferUrl"}>
              <ValidateableTextField
                  id="remoteFileTransferUrl"
                  name="remoteFileTransferUrl"
                  label="Remote File Transfer Url"
                  defaultValue={data.remoteFileTransferUrl}
                  fullWidth
                  validators={['required']}
              />
            </OptionalControl>
          </Grid>
          <Grid item xs={12}>
            <StatusInput defaultValue={data.status}/>
          </Grid>
        </React.Fragment>
    )
  }
}

export default EditVoucherPrinter;
