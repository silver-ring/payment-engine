import serialize from "form-serialize";
import moment from "moment";

export function convertFormToJson(form) {
  if (form == null) {
    return null;
  }
  return serialize(form, {hash: true, empty: true});
}

export function convertJsonToQueryParam(json) {
  if (json == null) {
    return null;
  }
  return Object.keys(json).map((key) => {
    const val = json[key];
    return key + "=" + val + "&";
  }).reduce(((first, second) => first + second), "");
}

export function convertJsonDateToJsDate(jsonDate) {
  if (jsonDate == null) {
    return null;
  }
  return moment(jsonDate, "YYYY-MM-DDTHH:mm:ss.SSS+0000").format(
      "YYYY-MM-DDTHH:mm");
}

export function convertJsonDateToStringDate(jsonDate) {
  if (jsonDate == null) {
    return null;
  }
  return moment(jsonDate, "YYYY-MM-DDTHH:mm:ss.SSS+0000").format("YYYY-MM-DD");
}

export function convertJsonDateToStringDateTime(jsonDate) {
  if (jsonDate == null) {
    return null;
  }
  return moment(jsonDate, "YYYY-MM-DDTHH:mm:ss.SSS+0000").format(
      "YYYY-MM-DD HH:mm:ss");
}
