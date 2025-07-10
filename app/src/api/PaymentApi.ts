import type { AxiosPromise } from "axios";
import { PaymentResourceApi } from "../generated/api-client";
import { ops, apiConfig } from "./config";

const api = new PaymentResourceApi(apiConfig);

const PaymentApi = {
  createCheckoutSession(productId: string): AxiosPromise {
    return api.createCheckoutSession(productId, ops());
  },

  createSetupIntent(productId: string): AxiosPromise {
    return api.createSetupIntent(productId, ops());
  },

  getSessionStatus(sessionId: string): AxiosPromise {
    return api.getSessionStatus(sessionId, ops());
  },
};

export default PaymentApi;
