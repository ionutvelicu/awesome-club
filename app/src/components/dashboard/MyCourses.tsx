import { useEffect, useState } from "react";
import type {
  MemberProductDto,
  MemberProductLightDto,
} from "../../generated/api-client";
import ProductApi from "../../api/ProductApi";
import { handleAxiosError } from "../../services/ErrorService";
import { Skeleton } from "antd";
import { useNavigate } from "react-router-dom";

export default function MyCourses() {
  const navigate = useNavigate();

  const [loading, setLoading] = useState(false);
  const [courses, setCourses] = useState([] as MemberProductLightDto[]);

  useEffect(() => {
    setLoading(true);
    ProductApi.getPurchased()
      .then((resp) => {
        setCourses(resp.data);
      })
      .catch((err) => {
        handleAxiosError(err);
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  function goToPurchase(purchaseId: string) {
    navigate(`/courses/${purchaseId}`);
  }

  return (
    <div>
      <h1>My courses</h1>

      {loading && <Skeleton active />}
      {!loading && (
        <>
          <ul>
            {courses.map((it) => (
              <li key={it.purchaseId}>
                <h3 onClick={() => goToPurchase(it.purchaseId)}>{it.name}</h3>
              </li>
            ))}
          </ul>
        </>
      )}
    </div>
  );
}
