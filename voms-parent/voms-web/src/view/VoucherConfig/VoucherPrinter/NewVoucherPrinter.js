import Grid from "@material-ui/core/Grid/Grid";
import React from "react";
import StatusInput from "../../../fragment/StatusInput";
import ValidateableTextField from "../../../component/ValidateableTextField";
import FileReader from "../../../component/FileReader";
import OptionalControl from "../../../component/OptionalControl";

class NewVoucherPrinter extends React.Component {

  render = () => {
    return (
        <React.Fragment>
          <Grid item xs={12}>
            <ValidateableTextField
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
                        fileDataField={"encryptionKeyFileData"}/>
          </Grid>
          <Grid item xs={12}>
            <OptionalControl label={"Add Remote File Transfer Url"}
                             field={"addRemoteFileTransferUrl"}>
              <ValidateableTextField
                  id="remoteFileTransferUrl"
                  name="remoteFileTransferUrl"
                  label="Remote File Transfer Url"
                  fullWidth
                  validators={['required']}
              />
            </OptionalControl>
          </Grid>
          <Grid item xs={12}>
            <StatusInput/>
          </Grid>
        </React.Fragment>
    )
  }
}

export default NewVoucherPrinter;
