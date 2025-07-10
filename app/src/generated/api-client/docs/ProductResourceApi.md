# ProductResourceApi

All URIs are relative to *http://localhost:8080/api*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**buyProduct**](#buyproduct) | **POST** /products/{id}/buy | |
|[**checkProductStatus**](#checkproductstatus) | **GET** /products/{id}/status | |
|[**createProduct**](#createproduct) | **POST** /products | |
|[**deleteProduct**](#deleteproduct) | **DELETE** /products/{id} | |
|[**getProduct**](#getproduct) | **GET** /products/{id} | |
|[**getProductsForAuthor**](#getproductsforauthor) | **GET** /products | |
|[**getPurchasedProductDetails**](#getpurchasedproductdetails) | **GET** /products/purchased/{purchasedId} | |
|[**getPurchasedProducts**](#getpurchasedproducts) | **GET** /products/purchased | |
|[**updateProduct**](#updateproduct) | **POST** /products/{id} | |
|[**uploadProductSectionContent**](#uploadproductsectioncontent) | **POST** /products/{productId}/section/{sectionId}/content | |

# **buyProduct**
> BuyProductResponse buyProduct()


### Example

```typescript
import {
    ProductResourceApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ProductResourceApi(configuration);

let id: string; // (default to undefined)

const { status, data } = await apiInstance.buyProduct(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**string**] |  | defaults to undefined|


### Return type

**BuyProductResponse**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |
|**400** | Bad Request |  -  |
|**500** | Internal Server Error |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **checkProductStatus**
> MemberProductStatusDto checkProductStatus()


### Example

```typescript
import {
    ProductResourceApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ProductResourceApi(configuration);

let id: string; // (default to undefined)

const { status, data } = await apiInstance.checkProductStatus(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**string**] |  | defaults to undefined|


### Return type

**MemberProductStatusDto**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |
|**400** | Bad Request |  -  |
|**500** | Internal Server Error |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **createProduct**
> ProductDto createProduct()


### Example

```typescript
import {
    ProductResourceApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ProductResourceApi(configuration);

const { status, data } = await apiInstance.createProduct();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**ProductDto**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |
|**400** | Bad Request |  -  |
|**500** | Internal Server Error |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **deleteProduct**
> deleteProduct()


### Example

```typescript
import {
    ProductResourceApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ProductResourceApi(configuration);

let id: string; // (default to undefined)

const { status, data } = await apiInstance.deleteProduct(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**string**] |  | defaults to undefined|


### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |
|**400** | Bad Request |  -  |
|**500** | Internal Server Error |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **getProduct**
> ProductDto getProduct()


### Example

```typescript
import {
    ProductResourceApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ProductResourceApi(configuration);

let id: string; // (default to undefined)

const { status, data } = await apiInstance.getProduct(
    id
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **id** | [**string**] |  | defaults to undefined|


### Return type

**ProductDto**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |
|**400** | Bad Request |  -  |
|**500** | Internal Server Error |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **getProductsForAuthor**
> Array<ProductDto> getProductsForAuthor()


### Example

```typescript
import {
    ProductResourceApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ProductResourceApi(configuration);

const { status, data } = await apiInstance.getProductsForAuthor();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**Array<ProductDto>**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |
|**400** | Bad Request |  -  |
|**500** | Internal Server Error |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **getPurchasedProductDetails**
> MemberProductDto getPurchasedProductDetails()


### Example

```typescript
import {
    ProductResourceApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ProductResourceApi(configuration);

let purchasedId: string; // (default to undefined)

const { status, data } = await apiInstance.getPurchasedProductDetails(
    purchasedId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **purchasedId** | [**string**] |  | defaults to undefined|


### Return type

**MemberProductDto**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |
|**400** | Bad Request |  -  |
|**500** | Internal Server Error |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **getPurchasedProducts**
> Array<MemberProductLightDto> getPurchasedProducts()


### Example

```typescript
import {
    ProductResourceApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ProductResourceApi(configuration);

const { status, data } = await apiInstance.getPurchasedProducts();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**Array<MemberProductLightDto>**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |
|**400** | Bad Request |  -  |
|**500** | Internal Server Error |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **updateProduct**
> updateProduct(productDto)


### Example

```typescript
import {
    ProductResourceApi,
    Configuration,
    ProductDto
} from './api';

const configuration = new Configuration();
const apiInstance = new ProductResourceApi(configuration);

let id: string; // (default to undefined)
let productDto: ProductDto; //

const { status, data } = await apiInstance.updateProduct(
    id,
    productDto
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **productDto** | **ProductDto**|  | |
| **id** | [**string**] |  | defaults to undefined|


### Return type

void (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |
|**400** | Bad Request |  -  |
|**500** | Internal Server Error |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **uploadProductSectionContent**
> string uploadProductSectionContent()


### Example

```typescript
import {
    ProductResourceApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new ProductResourceApi(configuration);

let productId: string; // (default to undefined)
let sectionId: string; // (default to undefined)

const { status, data } = await apiInstance.uploadProductSectionContent(
    productId,
    sectionId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **productId** | [**string**] |  | defaults to undefined|
| **sectionId** | [**string**] |  | defaults to undefined|


### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |
|**400** | Bad Request |  -  |
|**500** | Internal Server Error |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

