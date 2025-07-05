import { Button, Modal } from "antd";
import ProductApi from "../../api/ProductApi";
import { handleAxiosError } from "../../services/ErrorService";
import { useState } from "react";

interface BuyProductModalProps {
  open: boolean;
  productId: string;
  onClose: () => void;
}

export default function BuyProductModal({
  open,
  productId,
  onClose,
}: BuyProductModalProps) {
  const [buying, setBuying] = useState(false);

  function buy() {
    setBuying(true);
    ProductApi.buy(productId)
      .then((resp) => {
        console.log(resp);
      })
      .catch((err) => {
        handleAxiosError(err);
      })
      .finally(() => {
        setBuying(false);
      });
  }

  return (
    <>
      <Modal open={open} onCancel={onClose}>
        <Button loading={buying} onClick={buy}>
          Buy
        </Button>
      </Modal>
    </>
  );
}
