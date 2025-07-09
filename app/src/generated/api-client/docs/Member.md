# Member

## Properties

| Name            | Type                                   | Description | Notes                             |
| --------------- | -------------------------------------- | ----------- | --------------------------------- |
| **id**          | **string**                             |             | [default to undefined]            |
| **email**       | **string**                             |             | [default to undefined]            |
| **password**    | **string**                             |             | [default to undefined]            |
| **name**        | **string**                             |             | [default to undefined]            |
| **isAuthor**    | **boolean**                            |             | [default to undefined]            |
| **lastLogin**   | **string**                             |             | [default to undefined]            |
| **state**       | **string**                             |             | [default to undefined]            |
| **products**    | [**Array&lt;Product&gt;**](Product.md) |             | [default to undefined]            |
| **createdDate** | **string**                             |             | [default to undefined]            |
| **updatedDate** | **string**                             |             | [default to undefined]            |
| **removedDate** | **string**                             |             | [default to undefined]            |
| **removed**     | **boolean**                            |             | [default to undefined]            |
| **author**      | **boolean**                            |             | [optional] [default to undefined] |

## Example

```typescript
import { Member } from "./api";

const instance: Member = {
  id,
  email,
  password,
  name,
  isAuthor,
  lastLogin,
  state,
  products,
  createdDate,
  updatedDate,
  removedDate,
  removed,
  author,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
