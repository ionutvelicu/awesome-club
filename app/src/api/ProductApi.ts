import type { AxiosPromise } from "axios";
import {
  ProductResourceApi,
  type BuyProductResponse,
  type MemberProductDto,
  type MemberProductLightDto,
  type MemberProductStatusDto,
  type ProductDto,
} from "../generated/api-client";
import { ops, apiConfig } from "./config";

const api = new ProductResourceApi(apiConfig);

const ProductApi = {
  getForAuthor(): AxiosPromise<ProductDto[]> {
    return api.getProductsForAuthor(ops());
  },

  get(id: string): AxiosPromise<ProductDto> {
    return api.getProduct(id, ops());
  },

  create(): AxiosPromise<ProductDto> {
    return api.createProduct(ops());
  },

  update(id: string, body: Partial<ProductDto>): AxiosPromise<void> {
    return api.updateProduct(id, body as ProductDto, ops());
  },

  delete(id: string): AxiosPromise<void> {
    return api.deleteProduct(id, ops());
  },

  checkStatus(productId: string): AxiosPromise<MemberProductStatusDto> {
    return api.checkProductStatus(productId, ops());
  },

  buy(productId: string): AxiosPromise<BuyProductResponse> {
    return api.buyProduct(productId, ops());
  },

  getPurchased(): AxiosPromise<MemberProductLightDto[]> {
    return api.getPurchasedProducts(ops());
  },

  getPurchasedProductDetails(id: string): AxiosPromise<MemberProductDto> {
    return api.getPurchasedProductDetails(id, ops());
  },

  deleteAssetFromSection(productId: string, sectionId: string, assetId: number): AxiosPromise<void> {
    return api.deleteAsset(productId, sectionId, assetId);
  }
};

export default ProductApi;
