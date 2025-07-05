import { useEffect, useState } from "react";
import ProductApi from "../../api/ProductApi";
import { Button } from "antd";

interface ProductActionProps {
  id: string;
  price: number;
}

export default function ProductAction({ id, price }: ProductActionProps) {
  const [loading, setLoading] = useState(true);
  const [purchased, setPurchased] = useState(false);

  useEffect(() => {
    ProductApi.checkProductStatus(id).then((resp) => {
      setPurchased(resp.data.purchased);
    }).finally(() => {
      setLoading(false);
    });
  }, [id]);

  function goToBuy() {
    //TODO Teo
  }

  return <>
    {!loading && <>
      {purchased && <Button>
        <strong>Already owned</strong>
        Go to course
      </Button>}

      {!purchased && <Button onClick={goToBuy}>
        ${price} Buy now
      </Button>}
    </>}
  </>
}
