import Cookies from "js-cookie";

const TokenKey = "Token";

export function getToken() {
  return Cookies.get(TokenKey);
}

export function setToken(token: string) {
  return Cookies.set(TokenKey, token);
}

export function removeToken(): void {
  return Cookies.remove(TokenKey);
}
