# AuthResourceApi

All URIs are relative to *http://localhost:8080/api*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**getStatus**](#getstatus) | **GET** /public/auth/status | |
|[**login**](#login) | **POST** /public/auth/login | |
|[**register**](#register) | **POST** /public/auth/register | |

# **getStatus**
> AuthStatus getStatus()


### Example

```typescript
import {
    AuthResourceApi,
    Configuration
} from './api';

const configuration = new Configuration();
const apiInstance = new AuthResourceApi(configuration);

const { status, data } = await apiInstance.getStatus();
```

### Parameters
This endpoint does not have any parameters.


### Return type

**AuthStatus**

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

# **login**
> AuthResponse login(authRequest)


### Example

```typescript
import {
    AuthResourceApi,
    Configuration,
    AuthRequest
} from './api';

const configuration = new Configuration();
const apiInstance = new AuthResourceApi(configuration);

let authRequest: AuthRequest; //

const { status, data } = await apiInstance.login(
    authRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **authRequest** | **AuthRequest**|  | |


### Return type

**AuthResponse**

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

# **register**
> AuthResponse register(authRequest)


### Example

```typescript
import {
    AuthResourceApi,
    Configuration,
    AuthRequest
} from './api';

const configuration = new Configuration();
const apiInstance = new AuthResourceApi(configuration);

let authRequest: AuthRequest; //

const { status, data } = await apiInstance.register(
    authRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **authRequest** | **AuthRequest**|  | |


### Return type

**AuthResponse**

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

