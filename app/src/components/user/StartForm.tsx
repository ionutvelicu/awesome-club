import { Button, Input, message } from "antd";
import { useState } from "react";
import AuthApi from "../../api/AuthApi";
import Storage from "../../util/storage";
import { handleAxiosError } from "../../services/ErrorService";
import { getRoute, Routes } from "../../util/routes";

interface StartFormProps {
  author: boolean;
}

export default function StartForm({ author }: StartFormProps) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [saving, setSaving] = useState(false);

  async function register() {
    if (!email || !password) return;

    setSaving(true);
    AuthApi.register(email, password, author)
      .then((resp) => {
        Storage.setToken(resp.data.token);
        window.location.href = author ? getRoute(Routes.ProductsNew) : getRoute(Routes.Dashboard);
      })
      .catch((err) => {
        handleAxiosError(err);
      })
      .finally(() => {
        setSaving(false);
      });
  }

  return (
    <div className="auth-form">
      <Input
        type="email"
        size="large"
        value={email}
        onChange={(ev) => setEmail(ev.target.value)}
      />
      <Input
        type="password"
        size="large"
        value={password}
        onChange={(ev) => setPassword(ev.target.value)}
      />
      <Button
        block
        size="large"
        loading={saving}
        onClick={register}
      >
        Register
      </Button>
    </div>
  );
}
