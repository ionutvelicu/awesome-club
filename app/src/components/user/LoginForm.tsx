import { Button, Input, message } from "antd";
import { useState } from "react";
import AuthApi from "../../api/AuthApi";
import Storage from "../../util/storage";
import { handleAxiosError } from "../../services/ErrorService";
import { getRoute, Routes } from "../../util/routes";

export default function LoginForm() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  async function login() {
    if (!email || !password) return;

    setLoading(true);
    AuthApi.login(email, password)
      .then((resp) => {
        Storage.setToken(resp.data.token);
        window.location.href = getRoute(Routes.Dashboard);
      })
      .catch((err) => {
        handleAxiosError(err);
      })
      .finally(() => setLoading(false));
  }

  return (
    <div className="auth-form">
      <Input
        name="email"
        type="email"
        size="large"
        value={email}
        onChange={(ev) => setEmail(ev.target.value)}
      />
      <Input
        name="password"
        type="password"
        size="large"
        value={password}
        onChange={(ev) => setPassword(ev.target.value)}
      />
      <Button
        size="large"
        block
        loading={loading}
        onClick={login}
      >
        Login
      </Button>
    </div>
  );
}
