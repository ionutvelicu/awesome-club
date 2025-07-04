import { env } from "../env/env";
import Storage from "../util/storage";
import { Configuration } from "../generated/api-client";

export const ops = (tokenOverride?: string) => {
  const token = tokenOverride ?? Storage.getToken();

  if (!token) return {};
  return {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  };
};

export const apiConfig = new Configuration({ basePath: env.apiPath });
