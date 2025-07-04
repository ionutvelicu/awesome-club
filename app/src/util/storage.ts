const TKN = "tkn";

const Storage = {
  setToken(tkn: string) {
    window.localStorage.setItem(TKN, tkn);
  },

  getToken(): string {
    return window.localStorage.getItem(TKN) || "";
  },

  removeToken() {
    window.localStorage.removeItem(TKN);
  },
};

export default Storage;
