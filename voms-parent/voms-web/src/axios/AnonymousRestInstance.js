import axios from 'axios';
import {PROXY_URL} from "./AxiosConfig";

export const anonymousRestInstance = axios.create({
  baseURL: PROXY_URL + '/rest',
  headers: {'Content-Type': 'application/json'}
});

anonymousRestInstance.interceptors.request.use((config) => {
  return config;
}, (err) => {
  if (err.status === 401) {
    window.location.href = "/login";
  }
  return Promise.reject(err);
});

export default anonymousRestInstance;
