import type { AxiosPromise } from "axios";
import {
  AuthResourceApi,
  type AuthResponse,
  type AuthStatus,
} from "../generated/api-client";
import { ops, apiConfig } from "./config";

const api = new AuthResourceApi(apiConfig);

const AuthApi = {
  getStatus(): AxiosPromise<AuthStatus> {
    return api.getStatus(ops());
  },

  login(email: string, password: string): AxiosPromise<AuthResponse> {
    return api.login({ email, password }, ops());
  },

  register(
    email: string,
    password: string,
    isAuthor: boolean = false,
  ): AxiosPromise<AuthResponse> {
    return api.register({ email, password, isAuthor }, ops());
  },
};

export default AuthApi;
