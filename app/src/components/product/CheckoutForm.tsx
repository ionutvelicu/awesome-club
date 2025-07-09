import { useEffect, useState } from "react";
import { PaymentElement, useCheckout } from "@stripe/react-stripe-js";
import { Button } from "antd";
import { MessageFilled } from "@ant-design/icons";

interface CheckoutFormProps {
  email: string;
}

const CheckoutForm = ({ email }: CheckoutFormProps) => {
  const checkout = useCheckout();
  const [message, setMessage] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => console.log("email", email), [email]);
  const handleSubmit = async () => {
    console.log("in submit");
    setIsLoading(true);
    const isValidEmail = await checkout.updateEmail(email);

    if (!isValidEmail) setMessage(isValidEmail.error.message);
    const confirmResult = await checkout.confirm();

    console.log("confirmResult ", confirmResult);
    if (confirmResult.type === "error") {
      setMessage(confirmResult.error.message);
    }

    setIsLoading(false);
  };

  return (
    <>
      <PaymentElement id="payment-element" />
      <Button disabled={isLoading} id="submit" onClick={handleSubmit}>
        {isLoading ? <div className="spinner"></div> : "Pay now"}
      </Button>
      {message && <MessageFilled id="payment-message">{message}</MessageFilled>}
    </>
  );
};

export default CheckoutForm;
