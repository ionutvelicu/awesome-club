# ContentResourceApi

All URIs are relative to _http://localhost:8080/api_

| Method                                    | HTTP request      | Description |
| ----------------------------------------- | ----------------- | ----------- |
| [**getMemberContent**](#getmembercontent) | **GET** /contents |             |

# **getMemberContent**

> Array<ContentDto> getMemberContent()

### Example

```typescript
import { ContentResourceApi, Configuration } from "./api";

const configuration = new Configuration();
const apiInstance = new ContentResourceApi(configuration);

const { status, data } = await apiInstance.getMemberContent();
```

### Parameters

This endpoint does not have any parameters.

### Return type

**Array<ContentDto>**

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
