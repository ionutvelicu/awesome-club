import Storage from "../util/storage.ts";
import { Routes } from "../util/routes.ts";

export function logout() {
  Storage.removeToken();
  window.location.href = Routes.Root;
}
