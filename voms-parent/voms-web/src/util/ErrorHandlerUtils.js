export function handleRemoteErrorResponse(error) {
  let responseErrorMessage;
  try {
    if (error.response.status === 401) {
      responseErrorMessage = handleSecurityErrors(error.response.data);
    } else if (error.response.status === 400) {
      responseErrorMessage = handleBadRequest(error.response.data);
    } else if (error.response.status === 409) {
      responseErrorMessage = handleConflictState(error.response.data);
    } else if (error.response.status === 404) {
      responseErrorMessage = handleNotFound();
    } else if (error.response.status === 500) {
      responseErrorMessage = handleInternalServerError(error.response.data);
    } else {
      responseErrorMessage = error.response.data.error;
    }
  } catch (ex) {
    responseErrorMessage = "Unknown Error";
  }
  return responseErrorMessage;
}

export function handleSecurityErrors(data) {
  if (data.error === "invalid_token") {
    window.location.href = "/login";
  }
  return data.error;
}

export function handleBadRequest(data) {
  return data.message;
}

export function handleConflictState(data) {
  const props = data.props;
  let responseErrorMessage = props.map((key) => (
      upperCaseFirstLetter(
          lowerCaseAllWordsExceptFirstLetters(replaceAll(key, "_", " ")))
  )).reduce((x = "", y) => (y + " and " + x)).replace(/and$/, "").trim();

  responseErrorMessage += " already exist";
  return responseErrorMessage;
}

export function handleInternalServerError() {
  return "System Error";
}

export function handleNotFound() {
  return "Not Found";
}

function replaceAll(target, search, replacement) {
  return target.split(search).join(replacement);
}

function upperCaseFirstLetter(string) {
  return string.charAt(0).toUpperCase() + string.slice(1);
}

function lowerCaseAllWordsExceptFirstLetters(string) {
  return string.replace(/\w\S*/g, function (word) {
    return word.charAt(0) + word.slice(1).toLowerCase();
  });
}
