import { Skeleton } from "antd";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import ProductApi from "../../api/ProductApi";
import { handleAxiosError } from "../../services/ErrorService";
import type { MemberProductDto } from "../../generated/api-client";

export default function CourseView() {
  const [loading, setLoading] = useState(false);
  const [course, setCourse] = useState(null as null | MemberProductDto);
  const { purchaseId } = useParams();

  useEffect(() => {
    if (!purchaseId) return;

    setLoading(false);
    ProductApi.getPurchasedProductDetails(purchaseId)
      .then((resp) => {
        setCourse(resp.data);
      })
      .catch((err) => {
        handleAxiosError(err);
      })
      .finally(() => {
        setLoading(false);
      });
  }, [purchaseId]);

  return (
    <div>
      <h1>{course?.product.name}</h1>
    </div>
  );
}
