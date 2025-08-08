# MemberProduct

## Properties

| Name             | Type                      | Description | Notes                  |
| ---------------- | ------------------------- | ----------- | ---------------------- |
| **id**           | **string**                |             | [default to undefined] |
| **ownerId**      | **string**                |             | [default to undefined] |
| **price**        | **number**                |             | [default to undefined] |
| **paymentData**  | **string**                |             | [default to undefined] |
| **progress**     | **number**                |             | [default to undefined] |
| **complete**     | **boolean**               |             | [default to undefined] |
| **product**      | [**Product**](Product.md) |             | [default to undefined] |
| **purchaseDate** | **string**                |             | [default to undefined] |
| **createdDate**  | **string**                |             | [default to undefined] |
| **updatedDate**  | **string**                |             | [default to undefined] |
| **removedDate**  | **string**                |             | [default to undefined] |
| **removed**      | **boolean**               |             | [default to undefined] |

## Example

```typescript
import { MemberProduct } from "./api";

const instance: MemberProduct = {
  id,
  ownerId,
  price,
  paymentData,
  progress,
  complete,
  product,
  purchaseDate,
  createdDate,
  updatedDate,
  removedDate,
  removed,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
