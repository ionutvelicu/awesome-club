import React, { useEffect, useState } from "react";
import { Button, Flex, Result, Spin } from "antd";
import PaymentApi from "../../api/PaymentApi";
import ProductApi from "../../api/ProductApi";
import { handleAxiosError } from "src/services/ErrorService";

interface ResultPageProps {
  sessionId: string | null;
  productId: string | null;
}
export default function ResultPage({ sessionId, productId }: ResultPageProps) {
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [sessionDetails, setSessionDetails] = useState<Record<
    string,
    string
  > | null>(null);

  useEffect(() => {
    if (!sessionId || !productId) return;

    const fetchSessionStatus = async () => {
      setIsLoading(true);

      try {
        const { data } = await PaymentApi.getSessionStatus(sessionId);
        setSessionDetails(data);

        if (data.payment_status === "paid") {
          await ProductApi.buy(productId);
        }
      } catch (err) {
        handleAxiosError(err);
      } finally {
        setIsLoading(false);
      }
    };

    fetchSessionStatus();
  }, [sessionId, productId]);

  if (isLoading)
    return (
      <Flex gap="middle" vertical justify="center" align="center">
        <Spin spinning size="large" tip="Do not close the page" fullscreen>
          Do not close the page
        </Spin>
      </Flex>
    );

  return (
    <Result
      status={sessionDetails?.payment_status === "paid" ? "success" : "error"}
      title="Thank you for your purchase!"
      subTitle="Your payment was successfully processed."
      extra={[
        <Button
          type="primary"
          onClick={() => (window.location.href = "/app/dashboard")}
        >
          Go To Dashboard
        </Button>,
      ]}
    />
  );
}
