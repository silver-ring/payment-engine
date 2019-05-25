import React from "react";
import Grid from "@material-ui/core/Grid/Grid";
import ValidateableTextField from "../../../component/ValidateableTextField";
import EditBulkOrderForm from "../../../fragment/EditBulkOrderForm";
import * as halfred from "halfred";
import OptionalControl from "../../../component/OptionalControl";
import SingleValueSelect from "../../../component/SingleValueSelect";

class EditCreatedVoucherModificationBulkOrder extends React.Component {

  render = () => {

    const {data} = this.props;

    const parsedData = halfred.parse(data).allLinkArrays();

    const voucherTypeDefault = parsedData["voucherType"][0]["href"];
    const rechargeValueDefault = parsedData["rechargeValue"][0]["href"];

    const modifyVoucherType = parsedData["modifyVoucherType"];
    const modifyRechargeValue = parsedData["modifyRechargeValue"];

    return (
        <EditBulkOrderForm data={data}>
          <Grid item xs={12}>
            <ValidateableTextField
                id="startSerialNumber"
                name="startSerialNumber"
                label="Start Serial Number"
                defaultValue={data.startSerialNumber}
                type="text"
                fullWidth
                validators={['required', 'isNumber']}
            />
          </Grid>
          <Grid item xs={12}>
            <ValidateableTextField
                id="endSerialNumber"
                name="endSerialNumber"
                label="End Serial Number"
                type="text"
                defaultValue={data.endSerialNumber}
                fullWidth
                validators={['required', 'isNumber']}
            />
          </Grid>
          <Grid item xs={12}>
            <OptionalControl defaultValue={data.expirationDate != null}
                             label={"Modify Expiration Date"}>
              <ValidateableTextField
                  type="date"
                  id="expirationDate"
                  name="expirationDate"
                  label="Expiration Date"
                  InputLabelProps={{
                    shrink: true,
                  }}
                  fullWidth
                  validators={['required']}
                  defaultValue={data.expirationDate}
              />
            </OptionalControl>
          </Grid>
          <Grid item xs={12}>
            <OptionalControl defaultValue={data.rechargePeriod != null}
                             label={"Modify Recharge Period"}>
              <ValidateableTextField
                  id="rechargePeriod"
                  name="rechargePeriod"
                  label="Recharge Period"
                  fullWidth
                  validators={['required', 'isNumber']}
                  defaultValue={data.rechargePeriod}
              />
            </OptionalControl>
          </Grid>
          <Grid item xs={12}>
            <OptionalControl defaultValue={modifyVoucherType != null}
                             label={"Modify Voucher Type"}>
              <SingleValueSelect required
                                 label={"Voucher Type"}
                                 resource={"voucherTypes"} property={"name"}
                                 field="voucherType"
                                 defaultValue={voucherTypeDefault}/>
            </OptionalControl>
          </Grid>
          <Grid item xs={12}>
            <OptionalControl defaultValue={modifyRechargeValue != null}
                             label={"Modify Recharge Value"}>
              <SingleValueSelect required
                                 label={"Recharge Value"}
                                 resource={"rechargeValues"} property={"name"}
                                 field="rechargeValue"
                                 defaultValue={rechargeValueDefault}/>
            </OptionalControl>
          </Grid>
        </EditBulkOrderForm>
    )
  }
}

export default EditCreatedVoucherModificationBulkOrder;
