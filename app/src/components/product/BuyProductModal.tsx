import { Modal } from "antd";
import PaymentApi from "../../api/PaymentApi";
import { useMemo, useState } from "react";
import { CheckoutProvider } from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";
import CheckoutForm from "./CheckoutForm";

const stripePromise = loadStripe(
  import.meta.env.PUBLIC_STRIPE_PUBLISHABLE_KEY ?? "",
);

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
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [clientEmail, setClientEmail] = useState<string>("");

  const promise = useMemo(() => {
    if (open) {
      setIsLoading(true);
      return PaymentApi.createCheckoutSession(productId)
        .then((resp) => {
          console.log("resp", resp);
          setClientEmail(resp.data.clientEmail);
          return resp.data.clientSecret;
        })
        .finally(() => setIsLoading(false));
    }
  }, [open]);

  return (
    <Modal
      title="Checkout"
      open={open}
      onCancel={onClose}
      loading={isLoading}
      centered
      footer={null}
    >
      <CheckoutProvider
        stripe={stripePromise}
        options={{
          fetchClientSecret: async () => promise,
        }}
      >
        <CheckoutForm email={clientEmail} />
      </CheckoutProvider>
    </Modal>
  );
}
