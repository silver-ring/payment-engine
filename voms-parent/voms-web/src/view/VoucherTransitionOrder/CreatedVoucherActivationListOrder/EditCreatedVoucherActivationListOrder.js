import Grid from "@material-ui/core/Grid/Grid";
import React from "react";
import * as halfred from "halfred";
import SingleValueSelect from "../../../component/SingleValueSelect";
import EditListOrderForm from "../../../fragment/EditListOrderForm";

class EditCreatedVoucherActivationListOrder extends React.Component {

  render = () => {
    const {data} = this.props;

    const parsedData = halfred.parse(data);

    const voucherProviderDefaultValue = parsedData.link(
        "voucherProvider")["href"];

    return (
        <EditListOrderForm data={data}>
          <Grid item xs={12}>
            <SingleValueSelect required
                               label={"Voucher Provider"}
                               resource={"voucherProviders"}
                               property={"name"}
                               field="voucherProvider"
                               defaultValue={voucherProviderDefaultValue}/>
          </Grid>
        </EditListOrderForm>
    )
  }
}

export default EditCreatedVoucherActivationListOrder;