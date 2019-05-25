import React from "react";
import {
  CartesianGrid,
  Legend,
  Line,
  LineChart,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis
} from "recharts";
import restInstance from "../../axios/RestInstance";
import LinearProgress from "@material-ui/core/LinearProgress/LinearProgress";

class ActiveRechargeChart extends React.Component {

  state = {
    data: [],
    loading: false
  };

  componentDidMount = () => {

    restInstance.get("/activeRechargeChart")
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
    })

  };

  render = () => {

    const {data, loading} = this.state;

    return (
        <React.Fragment>
          {loading ? <LinearProgress/> : <React.Fragment/>}
          <ResponsiveContainer width="99%" height={320}>
            <LineChart data={data}>
              <XAxis dataKey="day"/>
              <YAxis/>
              <CartesianGrid vertical={false} strokeDasharray="3 3"/>
              <Tooltip/>
              <Legend/>
              <Line type="monotone" dataKey="activeVouchers"
                    stroke="#82ca9d"/>
              <Line type="monotone" dataKey="recharges" stroke="#8884d8"
                    activeDot={{r: 8}}/>
            </LineChart>
          </ResponsiveContainer>
        </React.Fragment>
    )
  }

}

export default ActiveRechargeChart;
