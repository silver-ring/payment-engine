import axios from 'axios';
import {getAccessToken, getTokenType} from "../store/Auth";
import {PROXY_URL} from "./AxiosConfig";

export const restResourceInstance = axios.create({
  baseURL: PROXY_URL + '/resources/rest',
  headers: {'Content-Type': 'application/json'}
});

restResourceInstance.interceptors.request.use((config) => {
  config.headers.common['Authorization'] = getTokenType() + " "
      + getAccessToken();
  return config;
}, (err) => {
  if (err.status === 401) {
    window.location.href = "/login";
  }
  return Promise.reject(err);
});

export default restResourceInstance;
