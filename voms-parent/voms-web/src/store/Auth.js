const authParameter = "auth";
const accessTokenParameter = "access_token";
const tokenTypeParameter = "token_type";
const guiUserAuthoritiesParameter = "guiUserAuthorities";

export function getAuth() {
  return JSON.parse(sessionStorage.getItem(authParameter));
}

export function getAccessToken() {
  return getAuth()[accessTokenParameter];
}

export function getTokenType() {
  return getAuth()[tokenTypeParameter];
}

export function getAuthorities() {
  return JSON.parse(
      sessionStorage.getItem(authParameter))[guiUserAuthoritiesParameter];
}

export function clearAuth() {
  sessionStorage.clear()
}

export function saveAuth(auth) {
  const jsonAuthObject = JSON.stringify(auth);
  sessionStorage.setItem(authParameter, jsonAuthObject);
}
