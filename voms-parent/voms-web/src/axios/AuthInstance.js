import axios from "axios";
import {PROXY_URL} from "./AxiosConfig";

const authInstance = axios.create({
  baseURL: PROXY_URL + '/security',
  headers: {'Content-Type': 'application/json'}
});

export default authInstance;
