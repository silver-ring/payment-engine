import axios from 'axios';
import {getAccessToken, getTokenType} from "../store/Auth";
import {PROXY_URL} from "./AxiosConfig";

export const restInstance = axios.create({
  baseURL: PROXY_URL + '/rest',
  headers: {'Content-Type': 'application/json'}
});

restInstance.interceptors.request.use((config) => {
  config.headers.common['Authorization'] = getTokenType() + " "
      + getAccessToken();
  return config;
}, (err) => {
  if (err.status === 401) {
    window.location.href = "/login";
  }
  return Promise.reject(err);
});

export default restInstance;
