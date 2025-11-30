import { Button } from "antd";
import { useStore } from "@nanostores/react";
import { authStore } from "../../store/auth";
import { Routes } from "../../util/routes";

export default function StartCtaBtn() {
  const $authStore = useStore(authStore);
  return (
    <Button
      type="primary"
      className="cta"
      href={
        $authStore.loggedIn ? `${Routes.ProductsNew}` : "/start?author=y"
      }
    >
      Start Selling
    </Button>
  );
}
