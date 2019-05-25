import React from "react";
import {
  Bar,
  BarChart,
  CartesianGrid,
  Legend,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis
} from "recharts";
import restInstance from "../../axios/RestInstance";
import LinearProgress from "./ActiveRechargeChart";

class AllVouchersChart extends React.Component {

  state = {
    data: [],
    loading: false
  };

  componentDidMount = () => {

    restInstance.get("/allVouchersChart")
    .then((response) => {
      const data = response.data;
      this.setState({
        data: data,
        loading: false
      })
    })
    .catch((error) => {
      this.setState({
        data: [],
        loading: false
      })
    });

    this.setState({
      loading: true
    });

  };

  render = () => {

    const {data, loading} = this.state;

    return (
        <React.Fragment>
          {loading ? <LinearProgress/> : <React.Fragment/>}
          <ResponsiveContainer width="99%" height={320}>
            <BarChart data={data}>
              <CartesianGrid strokeDasharray="3 3"/>
              <XAxis dataKey="day"/>
              <YAxis/>
              <Tooltip/>
              <Legend/>
              <Bar dataKey="created" stackId="a" fill="#001871"/>
              <Bar dataKey="active" stackId="a" fill="	#ff585d"/>
              <Bar dataKey="blocked" stackId="a" fill="#ffb549"/>
              <Bar dataKey="pendingUsage" stackId="a" fill="#41b6e6"/>
              <Bar dataKey="used" stackId="a" fill="#279899"/>
              <Bar dataKey="deleted" stackId="a" fill="#d04814"/>
              <Bar dataKey="archived" stackId="a" fill="#4dccbd"/>
            </BarChart>
          </ResponsiveContainer>
        </React.Fragment>
    )
  }

}

export default AllVouchersChart;
