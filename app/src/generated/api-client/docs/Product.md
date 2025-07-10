# Product

## Properties

| Name            | Type                    | Description | Notes                             |
| --------------- | ----------------------- | ----------- | --------------------------------- |
| **id**          | **string**              |             | [default to undefined]            |
| **name**        | **string**              |             | [default to undefined]            |
| **url**         | **string**              |             | [default to undefined]            |
| **description** | **string**              |             | [default to undefined]            |
| **hero**        | **string**              |             | [default to undefined]            |
| **price**       | **number**              |             | [default to undefined]            |
| **data**        | **string**              |             | [default to undefined]            |
| **status**      | **string**              |             | [default to undefined]            |
| **author**      | [**Member**](Member.md) |             | [optional] [default to undefined] |
| **createdDate** | **string**              |             | [default to undefined]            |
| **updatedDate** | **string**              |             | [default to undefined]            |
| **removedDate** | **string**              |             | [default to undefined]            |
| **removed**     | **boolean**             |             | [default to undefined]            |

## Example

```typescript
import { Product } from "./api";

const instance: Product = {
  id,
  name,
  url,
  description,
  hero,
  price,
  data,
  status,
  author,
  createdDate,
  updatedDate,
  removedDate,
  removed,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
