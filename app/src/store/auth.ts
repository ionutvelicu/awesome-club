import { atom } from "nanostores";
import type { AuthStatus } from "../generated/api-client";

export const authStore = atom({
  loggedIn: false,
  label: "",
  isAuthor: false,
});

export function setAuthStore(status: AuthStatus) {
  authStore.set({
    loggedIn: status.loggedIn,
    label: status.label,
    isAuthor: status.isAuthor,
  });
}
