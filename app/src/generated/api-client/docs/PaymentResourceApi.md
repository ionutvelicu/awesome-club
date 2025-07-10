# PaymentResourceApi

All URIs are relative to _http://localhost:8080/api_

| Method                                              | HTTP request                                   | Description |
| --------------------------------------------------- | ---------------------------------------------- | ----------- |
| [**createCheckoutSession**](#createcheckoutsession) | **POST** /payment/{id}/create-checkout-session |             |
| [**createSetupIntent**](#createsetupintent)         | **POST** /payment/{id}/create-setup-intent     |             |
| [**getSessionStatus**](#getsessionstatus)           | **GET** /payment/session/{id}/status           |             |

# **createCheckoutSession**

> { [key: string]: string; } createCheckoutSession()

### Example

```typescript
import { PaymentResourceApi, Configuration } from "./api";

const configuration = new Configuration();
const apiInstance = new PaymentResourceApi(configuration);

let id: string; // (default to undefined)

const { status, data } = await apiInstance.createCheckoutSession(id);
```

### Parameters

| Name   | Type         | Description | Notes                 |
| ------ | ------------ | ----------- | --------------------- |
| **id** | [**string**] |             | defaults to undefined |

### Return type

**{ [key: string]: string; }**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: _/_

### HTTP response details

| Status code | Description           | Response headers |
| ----------- | --------------------- | ---------------- |
| **200**     | OK                    | -                |
| **400**     | Bad Request           | -                |
| **500**     | Internal Server Error | -                |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **createSetupIntent**

> { [key: string]: string; } createSetupIntent()

### Example

```typescript
import { PaymentResourceApi, Configuration } from "./api";

const configuration = new Configuration();
const apiInstance = new PaymentResourceApi(configuration);

let id: string; // (default to undefined)

const { status, data } = await apiInstance.createSetupIntent(id);
```

### Parameters

| Name   | Type         | Description | Notes                 |
| ------ | ------------ | ----------- | --------------------- |
| **id** | [**string**] |             | defaults to undefined |

### Return type

**{ [key: string]: string; }**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: _/_

### HTTP response details

| Status code | Description           | Response headers |
| ----------- | --------------------- | ---------------- |
| **200**     | OK                    | -                |
| **400**     | Bad Request           | -                |
| **500**     | Internal Server Error | -                |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **getSessionStatus**

> { [key: string]: string; } getSessionStatus()

### Example

```typescript
import { PaymentResourceApi, Configuration } from "./api";

const configuration = new Configuration();
const apiInstance = new PaymentResourceApi(configuration);

let id: string; // (default to undefined)

const { status, data } = await apiInstance.getSessionStatus(id);
```

### Parameters

| Name   | Type         | Description | Notes                 |
| ------ | ------------ | ----------- | --------------------- |
| **id** | [**string**] |             | defaults to undefined |

### Return type

**{ [key: string]: string; }**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: _/_

### HTTP response details

| Status code | Description           | Response headers |
| ----------- | --------------------- | ---------------- |
| **200**     | OK                    | -                |
| **400**     | Bad Request           | -                |
| **500**     | Internal Server Error | -                |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)
