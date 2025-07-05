import type { AxiosPromise } from "axios";
import {
  ProductResourceApi,
  type MemberProductStatusDto,
  type ProductDto,
} from "../generated/api-client";
import { ops, apiConfig } from "./config";

const api = new ProductResourceApi(apiConfig);

const ProductApi = {
  getProductsForAuthor(): AxiosPromise<ProductDto[]> {
    return api.getProductsForAuthor(ops());
  },

  getProduct(id: string): AxiosPromise<ProductDto> {
    return api.getProduct(id, ops());
  },

  createProduct(): AxiosPromise<ProductDto> {
    return api.createProduct(ops());
  },

  updateProduct(id: string, body: Partial<ProductDto>): AxiosPromise<void> {
    return api.updateProduct(id, body as ProductDto, ops());
  },

  deleteProduct(id: string): AxiosPromise<void> {
    return api.deleteProduct(id, ops());
  },

  checkProductStatus(productId: string): AxiosPromise<MemberProductStatusDto> {
    return api.checkProductStatus(productId, ops());
  },
};

export default ProductApi;
