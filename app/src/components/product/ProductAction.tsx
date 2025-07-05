import { useEffect, useState } from "react";
import ProductApi from "../../api/ProductApi";
import { Button, Modal } from "antd";

interface ProductActionProps {
  id: string;
  price: number;
}

//todo add stripe widget first time add cart details, option to save payment for later, subsequent buys can use existing payment
// save on the server - create new endpoint /product/{courseId}/buy in ProductResource - entry is saved in MemberProduct
// after you create the resource and dtos on the backedn you can go into /app folder and run "npm run gen api" to generate frontend dtos and axios requests 
// you can then call them in ProductAPI

export default function ProductAction({ id, price }: ProductActionProps) {
  const [loading, setLoading] = useState(true);
  const [isBuyModal, setIsBuyModal] = useState(false);
  const [purchased, setPurchased] = useState(false);

  useEffect(() => {
    ProductApi.checkProductStatus(id).then((resp) => {
      setPurchased(resp.data.purchased);
    }).finally(() => {
      setLoading(false);
    });
  }, [id]);

  return <>
    {!loading && <>
      {purchased && <Button>
        <strong>Already owned</strong>
        Go to course
      </Button>}

      {!purchased && <Button onClick={() => setIsBuyModal(true)}>
        ${price} Buy now
      </Button>}

      {isBuyModal && <Modal>
        
        </Modal>}
    </>}
  </>
}
