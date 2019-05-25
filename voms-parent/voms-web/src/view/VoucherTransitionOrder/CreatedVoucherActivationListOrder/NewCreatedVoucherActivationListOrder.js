import Grid from "@material-ui/core/Grid/Grid";
import React from "react";
import SingleValueSelect from "../../../component/SingleValueSelect";
import NewListOrderForm from "../../../fragment/NewListOrderForm";

class NewCreatedVoucherActivationListOrder extends React.Component {

  render = () => {
    return (
        <NewListOrderForm>
          <Grid item xs={12}>
            <SingleValueSelect required
                               label={"Voucher Provider"}
                               resource={"voucherProviders"}
                               property={"name"}
                               field="voucherProvider"/>
          </Grid>
        </NewListOrderForm>
    )
  }
}

export default NewCreatedVoucherActivationListOrder;